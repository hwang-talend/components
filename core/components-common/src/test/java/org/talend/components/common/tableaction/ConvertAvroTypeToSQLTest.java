package org.talend.components.common.tableaction;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.junit.Before;
import org.junit.Test;
import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.SchemaConstants;

import java.sql.Types;

import static org.junit.Assert.*;

public class ConvertAvroTypeToSQLTest {

    private static Schema schema;

    @Before
    public void createSchema(){
        schema = SchemaBuilder.builder()
                .record("main")
                .fields()
                .name("integer_fld")
                .type(AvroUtils._int())
                .withDefault(1)
                .name("string_fld")
                .type(AvroUtils._string())
                .noDefault()
                .name("date_fld")
                .type(AvroUtils._logicalDate())
                .noDefault()
                .name("float_fld")
                .type(AvroUtils._double())
                .withDefault("123.1234")
                .name("timestamp_fld")
                .type(AvroUtils._logicalTimestamp())
                .noDefault()
                .name("time_fld")
                .type(AvroUtils._logicalTime())
                .noDefault()
                .name("boolean_fld")
                .type(AvroUtils._boolean())
                .noDefault()
                .name("byte_fld")
                .type(AvroUtils._byte())
                .noDefault()
                .name("bytes_fld")
                .type(AvroUtils._bytes())
                .noDefault()
                .name("character_fld")
                .type(AvroUtils._character())
                .noDefault()
                .name("decimal_fld")
                .type(AvroUtils._decimal())
                .noDefault()
                .name("timemicro_fld")
                .type(AvroUtils._logicalTimeMicros())
                .noDefault()
                .name("timestampmicro_fld")
                .type(AvroUtils._logicalTimestampMicros())
                .noDefault()
                .name("long_fld")
                .type(AvroUtils._long())
                .noDefault()
                .name("short_fld")
                .type(AvroUtils._short())
                .noDefault()
                .endRecord();
    }

    @Test
    public void convertToSQLType(){
        TableActionConfig conf = new TableActionConfig();
        ConvertAvroTypeToSQL conv = new ConvertAvroTypeToSQL(conf);

        int sql_int = conv.convertToSQLType(schema.getField("integer_fld").schema());
        assertEquals(Types.NUMERIC, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("string_fld").schema());
        assertEquals(Types.VARCHAR, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("date_fld").schema());
        assertEquals(Types.DATE, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("float_fld").schema());
        assertEquals(Types.DOUBLE, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("timestamp_fld").schema());
        assertEquals(Types.TIMESTAMP, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("boolean_fld").schema());
        assertEquals(Types.BOOLEAN, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("byte_fld").schema());
        assertEquals(Types.NUMERIC, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("time_fld").schema());
        assertEquals(Types.TIME, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("bytes_fld").schema());
        assertEquals(Types.BLOB, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("character_fld").schema());
        assertEquals(Types.VARCHAR, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("decimal_fld").schema());
        assertEquals(Types.NUMERIC, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("timemicro_fld").schema());
        assertEquals(Types.TIME, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("timestampmicro_fld").schema());
        assertEquals(Types.TIMESTAMP, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("long_fld").schema());
        assertEquals(Types.NUMERIC, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("short_fld").schema());
        assertEquals(Types.NUMERIC, sql_int);

    }

}