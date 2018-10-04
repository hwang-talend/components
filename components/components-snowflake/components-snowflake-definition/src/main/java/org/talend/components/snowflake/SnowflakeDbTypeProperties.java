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
package org.talend.components.snowflake;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.TypeLiteral;
import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.talend.daikon.properties.property.PropertyFactory.newProperty;

public class SnowflakeDbTypeProperties extends ComponentPropertiesImpl  {

    private static final long serialVersionUID = 1L;

    public enum SNOWFLAKE_DBTYPE {
        ARRAY,
        BIGINT,
        BINARY,
        BOOLEAN,
        CHARACTER,
        DATE,
        DATETIME,
        DECIMAL,
        DOUBLE,
        DOUBLE_PRECISION,
        FLOAT,
        FLOAT4,
        FLOAT8,
        INTEGER,
        NUMBER,
        NUMERIC,
        OBJECT,
        REAL,
        SMALLINT,
        STRING,
        TEXT,
        TIME,
        TIMESTAMP,
        TIMESTAMP_LTZ,
        TIMESTAMP_NTZ,
        TIMESTAMP_TZ,
        VARBINARY,
        VARCHAR,
        VARIANT
    }

    public static final TypeLiteral<List<String>> LIST_STRING_TYPE = new TypeLiteral<List<String>>() {};

    public Property<List<String>> column = newProperty(LIST_STRING_TYPE, "column");
    public Property<List<String>> dbtype = newProperty(LIST_STRING_TYPE, "dbtype");

    public SnowflakeDbTypeProperties(String name) {
        super(name);
    }

    @Override
    public void setupProperties() {
        super.setupProperties();
        column.setValue(Collections.<String>emptyList());

        List<String> optionPossibleValues = new ArrayList<String>();
        for (SNOWFLAKE_DBTYPE possibleValue : SNOWFLAKE_DBTYPE.values()) {
            optionPossibleValues.add(possibleValue.name());
        }
        dbtype.setPossibleValues(optionPossibleValues);
        column.setPossibleValues(Collections.EMPTY_LIST);
        column.setValue(Collections.EMPTY_LIST);
        dbtype.setValue(Collections.EMPTY_LIST);
    }

    @Override
    public void setupLayout() {
        super.setupLayout();
        Form mainForm = Form.create(this, Form.MAIN);
        mainForm.addColumn(Widget.widget(column).setWidgetType(Widget.ENUMERATION_WIDGET_TYPE));
        mainForm.addColumn(Widget.widget(dbtype).setWidgetType(Widget.ENUMERATION_WIDGET_TYPE));
    }

    public void setFieldNames(List<String> names){
        this.column.setPossibleValues(names);
    }



    /*private boolean isValideDbType() {
        if (column.getValue() == null || column.getValue().isEmpty() || dbtype.getValue() == null || dbtype.getValue()
                .isEmpty()) {
            return false;
        }

        int tableSize = column.getValue().size();
        for (int i = 0; i < tableSize; i++) {
            if (StringUtils.isEmpty(column.getValue().get(i)) || StringUtils.isEmpty(dbtype.getValue().get(i))) {
                return false;
            }
        }

        return true;
    }*/

}
