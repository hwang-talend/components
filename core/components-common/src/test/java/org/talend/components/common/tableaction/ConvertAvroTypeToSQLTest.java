package org.talend.components.common.tableaction;

import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.talend.daikon.avro.AvroUtils;

import java.sql.Types;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;
import static org.talend.daikon.avro.SchemaConstants.JAVA_CLASS_FLAG;

public class ConvertAvroTypeToSQLTest {

    private static Schema schema;

    @Before
    public void createSchema() {

        ConvertAvroTypeToSQLTest.schema = SchemaBuilder.builder()
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
                .noDefault()
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
                .name("raw_boolean_fld")
                .type(Schema.create(Schema.Type.BOOLEAN))
                .noDefault()
                .name("raw_bytes_fld")
                .type(Schema.create(Schema.Type.BYTES))
                .noDefault()
                .name("raw_double_fld")
                .type(Schema.create(Schema.Type.DOUBLE))
                .noDefault()
                .name("raw_float_fld")
                .type(Schema.create(Schema.Type.FLOAT))
                .noDefault()
                .name("raw_long_fld")
                .type(Schema.create(Schema.Type.LONG))
                .noDefault()
                .name("raw_string_fld")
                .type(Schema.create(Schema.Type.STRING))
                .noDefault()
                .name("raw_enum_fld")
                .type(Schema.createEnum("MYENUM", null, null, Lists.newArrayList("VAL1", "VAL2")))
                .noDefault()
                .name("raw_fixed_fld")
                .type(Schema.createFixed("MYFIXED", null, null, 2))
                .noDefault()
                .name("raw_map_fld")
                .type(Schema.createMap(Schema.createArray(SchemaBuilder.builder()
                        .record("main")
                        .fields()
                        .name("string_fld")
                        .type(Schema.create(Schema.Type.STRING))
                        .noDefault()
                        .endRecord())))
                .noDefault()
                .name("raw_record_fld")
                .type(Schema.createRecord("MYRECORD", null, null, false))
                .noDefault()
                .name("raw_null_fld")
                .type(Schema.create(Schema.Type.NULL))
                .noDefault()
                .name("raw_array_fld")
                .type(Schema.createArray(SchemaBuilder.builder()
                        .record("main")
                        .fields()
                        .name("string_fld")
                        .type(Schema.create(Schema.Type.STRING))
                        .noDefault()
                        .endRecord()))
                .noDefault()
                .endRecord();
    }

    @Test
    public void convertToSQLType() {
        TableActionConfig conf = new TableActionConfig();
        ConvertAvroTypeToSQL conv = new ConvertAvroTypeToSQL(conf);

        int sql_int = conv.convertToSQLType(schema.getField("integer_fld").schema());
        assertEquals(Types.INTEGER, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("string_fld").schema());
        assertEquals(Types.VARCHAR, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("raw_string_fld").schema());
        assertEquals(Types.VARCHAR, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("date_fld").schema());
        assertEquals(Types.DATE, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("float_fld").schema());
        assertEquals(Types.NUMERIC, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("raw_float_fld").schema());
        assertEquals(Types.NUMERIC, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("timestamp_fld").schema());
        assertEquals(Types.TIMESTAMP, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("boolean_fld").schema());
        assertEquals(Types.BOOLEAN, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("raw_boolean_fld").schema());
        assertEquals(Types.BOOLEAN, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("byte_fld").schema());
        assertEquals(Types.SMALLINT, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("time_fld").schema());
        assertEquals(Types.TIME, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("bytes_fld").schema());
        assertEquals(Types.BLOB, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("raw_bytes_fld").schema());
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
        assertEquals(Types.INTEGER, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("raw_long_fld").schema());
        assertEquals(Types.INTEGER, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("short_fld").schema());
        assertEquals(Types.SMALLINT, sql_int);

        sql_int = conv.convertToSQLType(schema.getField("raw_double_fld").schema());
        assertEquals(Types.NUMERIC, sql_int);

    }

    @Test
    public void unsupportedTypes() {
        TableActionConfig conf = new TableActionConfig();
        ConvertAvroTypeToSQL conv = new ConvertAvroTypeToSQL(conf);

        try {
            int sql_int = conv.convertToSQLType(schema.getField("raw_enum_fld").schema());
            fail("UnsupportedOperationException should be raised for ENUM type");
        }
        catch(UnsupportedOperationException e){
        }

        try {
            int sql_int = conv.convertToSQLType(schema.getField("raw_fixed_fld").schema());
            fail("UnsupportedOperationException should be raised for FIXED type");
        }
        catch(UnsupportedOperationException e){
        }

        try {
            int sql_int = conv.convertToSQLType(schema.getField("raw_map_fld").schema());
            fail("UnsupportedOperationException should be raised for MAP type");
        }
        catch(UnsupportedOperationException e){
        }

        try {
            int sql_int = conv.convertToSQLType(schema.getField("raw_record_fld").schema());
            fail("UnsupportedOperationException should be raised for RECORD type");
        }
        catch(UnsupportedOperationException e){
        }

        try {
            int sql_int = conv.convertToSQLType(schema.getField("raw_null_fld").schema());
            fail("UnsupportedOperationException should be raised for NULL type");
        }
        catch(UnsupportedOperationException e){
        }

        try {
            int sql_int = conv.convertToSQLType(schema.getField("raw_array_fld").schema());
            fail("UnsupportedOperationException should be raised for ARRAY type");
        }
        catch(UnsupportedOperationException e){
        }
    }

}