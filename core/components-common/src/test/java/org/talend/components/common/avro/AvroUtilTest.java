package org.talend.components.common.avro;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AvroUtilTest {

    private final static Map<String, String> checkGetAcceptableName = new HashMap<>();

    @Before
    public void init(){
        checkGetAcceptableName.put("good_name", "good_name");
        checkGetAcceptableName.put("_good_name", "_good_name");
        checkGetAcceptableName.put("_good_123_name", "_good_123_name");
        checkGetAcceptableName.put("Name With Space", "Name_With_Space");
        checkGetAcceptableName.put("   Name With Space_   ", "Name_With_Space_");
        checkGetAcceptableName.put("", AvroUtil.EMPTY_NAME);
        checkGetAcceptableName.put("   ", AvroUtil.EMPTY_NAME);
        checkGetAcceptableName.put("123_name", "_123_name");
        checkGetAcceptableName.put("  ^ `|[{name#~&_(  ", "______name_____");
    }

    @Test
    public void getAcceptableName(){
        assertEquals(AvroUtil.NULL_NAME, AvroUtil.getAcceptableName(null));
        for (String input : checkGetAcceptableName.keySet()) {
            assertEquals(checkGetAcceptableName.get(input), AvroUtil.getAcceptableName(input));
        }
    }

}