// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.components.common.tableaction;

import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.talend.daikon.avro.SchemaConstants;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class DefaultSQLCreateTableAction extends TableAction {

    private final Logger log = LoggerFactory.getLogger(DefaultSQLCreateTableAction.class);

    private String table;

    private Schema schema;

    private boolean drop;

    private boolean createIfNotExists;
    private boolean dropIfExists;

    public DefaultSQLCreateTableAction(final String table, final Schema schema, boolean createIfNotExists, boolean drop,
            boolean dropIfExists) {
        if (table == null || table.isEmpty()) {
            throw new InvalidParameterException("Table name can't null or empty");
        }

        this.table = table;
        this.schema = schema;
        this.createIfNotExists = createIfNotExists;

        this.drop = drop;
        this.dropIfExists = dropIfExists;
        if(dropIfExists){
            this.drop = true;
        }

    }

    @Override
    public List<String> getQueries() throws Exception {
        List<String> queries = new ArrayList<>();

        if (drop) {
            queries.add(getDropTableQuery());
        }

        queries.add(getCreateTableQuery());

        if (log.isDebugEnabled()) {
            log.debug("Generated SQL queries for create table:");
            for (String q : queries) {
                log.debug(q);
            }
        }

        return queries;
    }

    private String getDropTableQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getConfig().SQL_DROP_TABLE_PREFIX);
        sb.append(this.getConfig().SQL_DROP_TABLE);
        sb.append(" ");
        if(dropIfExists){
            sb.append(this.getConfig().SQL_DROP_TABLE_IF_EXISITS);
            sb.append(" ");
        }
        sb.append(escape(table));
        sb.append(this.getConfig().SQL_DROP_TABLE_SUFFIX);

        return sb.toString();
    }

    private String getCreateTableQuery() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.getConfig().SQL_CREATE_TABLE_PREFIX);
        sb.append(this.getConfig().SQL_CREATE_TABLE);
        sb.append(" ");

        if(createIfNotExists){
            sb.append(this.getConfig().SQL_CREATE_TABLE_IF_NOT_EXISTS);
            sb.append(" ");
        }

        sb.append(escape(table));
        sb.append(" ");
        sb.append(this.getConfig().SQL_CREATE_TABLE_FIELD_ENCLOSURE_START);
        sb.append(buildColumns());
        sb.append(this.getConfig().SQL_CREATE_TABLE_FIELD_ENCLOSURE_END);
        sb.append(this.getConfig().SQL_CREATE_TABLE_SUFFIX);

        return sb.toString();
    }

    private StringBuilder buildColumns() {
        StringBuilder sb = new StringBuilder();

        boolean first = true;
        List<Schema.Field> fields = schema.getFields();
        List<String> keys = new ArrayList<>();
        for (Schema.Field f : fields) {
            if (!first) {
                sb.append(this.getConfig().SQL_CREATE_TABLE_FIELD_SEP);
            }

            String sDBLength = f.getProp(SchemaConstants.TALEND_COLUMN_DB_LENGTH);
            String sDBName = f.getProp(SchemaConstants.TALEND_COLUMN_DB_COLUMN_NAME);
            String sDBType = f.getProp(SchemaConstants.TALEND_COLUMN_DB_TYPE);
            String sDBDefault = f.getProp(SchemaConstants.TALEND_COLUMN_DEFAULT);
            String sDBPrecision = f.getProp(SchemaConstants.TALEND_COLUMN_PRECISION);
            String sDBScale = f.getProp(SchemaConstants.TALEND_COLUMN_SCALE);
            boolean sDBIsKey = Boolean.valueOf(f.getProp(SchemaConstants.TALEND_COLUMN_IS_KEY)).booleanValue();

            String name = sDBName == null ? f.name() : sDBName;
            if (sDBIsKey) {
                keys.add(name);
            }
            sb.append(escape(name));
            sb.append(" ");

            if (isNullOrEmpty(sDBType)) {
                // If DB type not set, try to guess it
                sDBType = ConvertAvroTypeToSQL.convertToSQLTypeString(f.schema());
            }
            sb.append(sDBType);

            // Length
            if (this.getConfig().SQL_CREATE_TABLE_LENGTH_ENABLED && !isNullOrEmpty(sDBLength)) {
                sb.append(this.getConfig().SQL_CREATE_TABLE_LENGTH_START);
                sb.append(sDBLength);
                sb.append(this.getConfig().SQL_CREATE_TABLE_LENGTH_END);
            } else if (this.getConfig().SQL_CREATE_TABLE_PRECISION_ENABLED && !isNullOrEmpty(sDBPrecision)) { // or precision/scale
                sb.append(this.getConfig().SQL_CREATE_TABLE_PRECISION_START);
                sb.append(sDBPrecision);
                if (this.getConfig().SQL_CREATE_TABLE_SCALE_ENABLED && !isNullOrEmpty(sDBScale)) {
                    sb.append(this.getConfig().SQL_CREATE_TABLE_SCALE_SEP);
                    sb.append(sDBScale);
                }
                sb.append(this.getConfig().SQL_CREATE_TABLE_PRECISION_END);
            }

            if (this.getConfig().SQL_CREATE_TABLE_DEFAULT_ENABLED && !isNullOrEmpty(sDBDefault)) {
                sb.append(" ");
                sb.append(this.getConfig().SQL_CREATE_TABLE_DEFAULT);
                sb.append(" ");
                sb.append(sDBDefault);
            }

            first = false;
        }

        if (this.getConfig().SQL_CREATE_TABLE_CONSTRAINT_ENABLED && keys.size() > 0) {
            sb.append(this.getConfig().SQL_CREATE_TABLE_FIELD_SEP);
            sb.append(this.getConfig().SQL_CREATE_TABLE_CONSTRAINT);
            sb.append(" ");
            sb.append(escape(this.getConfig().SQL_CREATE_TABLE_PRIMARY_KEY_PREFIX+table));
            sb.append(" ");
            sb.append(this.getConfig().SQL_CREATE_TABLE_PRIMARY_KEY);
            sb.append(" ");
            sb.append(this.getConfig().SQL_CREATE_TABLE_PRIMARY_KEY_ENCLOSURE_START);

            first = true;
            for (String k : keys) {
                if (!first) {
                    sb.append(this.getConfig().SQL_CREATE_TABLE_FIELD_SEP);
                }

                sb.append(escape(k));

                first = false;
            }
            sb.append(this.getConfig().SQL_CREATE_TABLE_PRIMARY_KEY_ENCLOSURE_END);
        }

        return sb;
    }

    private static boolean isNullOrEmpty(String s) {
        if (s == null) {
            return true;
        }

        return s.trim().isEmpty();
    }
}
