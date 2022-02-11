package com.bready.xml2java;

import com.bready.xml2java.model.common.CommonItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collections;

class FillSingleFieldsCommandTest {

    private static Command command;

    @BeforeEach
    void setUp() {
        command = new FillSingleObjectsCommand();
    }

    @AfterEach
    void tearDown() {
        command = null;
    }

    @Test
    void testExecute_shouldReturnVoid() throws NoSuchFieldException {
        String expected = "test value";
        Field field = CommonItem.class.getDeclaredField("value");

        Mapping mapping = new Mapping();
        mapping.obj = new CommonItem();
        mapping.singleObjects.put(field, expected);

        command.setMappings(Collections.singleton(mapping));
        command.execute();

        Assertions.assertEquals(expected, ((CommonItem) mapping.obj).getValue());
    }
}
