package com.bready.xml2java;

import com.bready.xml2java.model.common.SingleFieldsItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.function.Function;

class PrepareSingleFieldsCommandTest {

    private static Command command;

    private static Mapping mapping;

    @BeforeEach
    void setUp() {
        command = new PrepareSingleFieldsCommand();

        mapping = new Mapping();
        mapping.obj = new SingleFieldsItem();

        command.setMappings(Collections.singleton(mapping));
    }

    @AfterEach
    void tearDown() {
        command = null;
        mapping = null;
    }

    @Test
    void testExecute_whenBooleanIsPassed_shouldReturnVoid() throws NoSuchFieldException {
        testExecute_whenBooleanIsPassed("pValue1");
        testExecute_whenBooleanIsPassed("value1");
    }

    @Test
    void testExecute_whenCharIsPassed_shouldReturnVoid() throws NoSuchFieldException {
        test("pValue2", "a", 'a');
        test("pValue2", "", Character.MIN_VALUE);

        test("value2", "a", 'a');
    }

    @Test
    void testExecute_whenNumberIsPassed_shouldReturnVoid() throws NoSuchFieldException {
        testExecute_whenNumberIsPassed("pValue3", Number::byteValue, Byte.MIN_VALUE, Byte.MAX_VALUE);
        testExecute_whenNumberIsPassed("pValue4", Number::shortValue, Short.MIN_VALUE, Short.MIN_VALUE);
        testExecute_whenNumberIsPassed("pValue5", Number::intValue, Integer.MIN_VALUE, Integer.MIN_VALUE);
        testExecute_whenNumberIsPassed("pValue6", Number::longValue, Long.MIN_VALUE, Long.MIN_VALUE);

        testExecute_whenNumberIsPassed("value3", Number::byteValue, Byte.MIN_VALUE, Byte.MAX_VALUE);
        testExecute_whenNumberIsPassed("value4", Number::shortValue, Short.MIN_VALUE, Short.MIN_VALUE);
        testExecute_whenNumberIsPassed("value5", Number::intValue, Integer.MIN_VALUE, Integer.MIN_VALUE);
        testExecute_whenNumberIsPassed("value6", Number::longValue, Long.MIN_VALUE, Long.MIN_VALUE);
    }

    @Test
    void testExecute_whenFloatIsPassed_shouldReturnVoid() throws NoSuchFieldException {
        testExecute_whenFloatNumberIsPassed("pValue7", Number::floatValue, Float.MIN_VALUE, Float.MAX_VALUE);
        testExecute_whenFloatNumberIsPassed("pValue8", Number::doubleValue, Double.MIN_VALUE, Double.MAX_VALUE);

        testExecute_whenFloatNumberIsPassed("value7", Number::floatValue, Float.MIN_VALUE, Float.MAX_VALUE);
        testExecute_whenFloatNumberIsPassed("value8", Number::doubleValue, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    @Test
    void testExecute_whenStringIsPassed_shouldReturnVoid() throws NoSuchFieldException {
        test("value9", "0", "0");
        test("value9", "0.1", "0.1");
        test("value9", "aaa", "aaa");
    }

    private void testExecute_whenBooleanIsPassed(String fieldName) throws NoSuchFieldException {
        test(fieldName, "true", true);
        test(fieldName, "false", false);
        test(fieldName, "null", false);
        test(fieldName, "a", false);
    }

    private <T extends Number> void testExecute_whenNumberIsPassed(String fieldName, Function<Number, T> function,
                                                                   T minValue, T maxValue)
            throws NoSuchFieldException {
        test(fieldName, "0", function.apply(0));
        test(fieldName, "16", function.apply(16));
        test(fieldName, String.valueOf(minValue), minValue);
        test(fieldName, String.valueOf(maxValue), maxValue);
    }

    private <T extends Number> void testExecute_whenFloatNumberIsPassed(String fieldName, Function<Number, T> function,
                                                                        T minValue, T maxValue)
            throws NoSuchFieldException {
        test(fieldName, "0", function.apply(0));
        test(fieldName, "10.1", function.apply(10.1));
        test(fieldName, String.valueOf(minValue), minValue);
        test(fieldName, String.valueOf(maxValue), maxValue);
    }

    private void test(String fieldName, String value, Object expected) throws NoSuchFieldException {
        Field field = SingleFieldsItem.class.getDeclaredField(fieldName);

        mapping.singleObjects.put(field, value);
        command.execute();
        Assertions.assertEquals(expected, mapping.singleObjects.get(field));
    }
}
