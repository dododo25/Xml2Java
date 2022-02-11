package com.bready.xml2java;

import com.bready.xml2java.model.common.SingleFieldsItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;

class ValidateMappingFieldRepresentationsCommandTest {

    private static Command command;

    private static Mapping mapping;

    @BeforeEach
    void setUp() {
        command = new ValidateMappingFieldRepresentationsCommand();

        mapping = new Mapping();
        mapping.obj = new SingleFieldsItem();
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
        test("pValue2", "a");
        test("pValue2", "");

        test("value2", "a");
    }

    @Test
    void testExecute_whenNumberIsPassed_shouldReturnVoid() throws NoSuchFieldException {
        testExecute_whenNumberIsPassed("pValue3", Byte.MIN_VALUE, Byte.MAX_VALUE);
        testExecute_whenNumberIsPassed("pValue4", Short.MIN_VALUE, Short.MIN_VALUE);
        testExecute_whenNumberIsPassed("pValue5", Integer.MIN_VALUE, Integer.MIN_VALUE);
        testExecute_whenNumberIsPassed("pValue6", Long.MIN_VALUE, Long.MIN_VALUE);

        testExecute_whenNumberIsPassed("value3", Byte.MIN_VALUE, Byte.MAX_VALUE);
        testExecute_whenNumberIsPassed("value4", Short.MIN_VALUE, Short.MIN_VALUE);
        testExecute_whenNumberIsPassed("value5", Integer.MIN_VALUE, Integer.MIN_VALUE);
        testExecute_whenNumberIsPassed("value6", Long.MIN_VALUE, Long.MIN_VALUE);
    }

    @Test
    void testExecute_whenFloatIsPassed_shouldReturnVoid() throws NoSuchFieldException {
        testExecute_whenFloatNumberIsPassed("pValue7", Float.MIN_VALUE, Float.MAX_VALUE);
        testExecute_whenFloatNumberIsPassed("pValue8", Double.MIN_VALUE, Double.MAX_VALUE);

        testExecute_whenFloatNumberIsPassed("value7", Float.MIN_VALUE, Float.MAX_VALUE);
        testExecute_whenFloatNumberIsPassed("value8", Double.MIN_VALUE, Double.MAX_VALUE);
    }

    @Test
    void testExecute_whenStringIsPassed_shouldReturnVoid() throws NoSuchFieldException {
        test("value9", "0");
        test("value9", "0.1");
        test("value9", "aaa");
    }

    @Test
    void testExecute_whenCharValueIsInvalid_shouldThrowException() throws NoSuchFieldException {
        test_shouldThrowException("pValue2", "aaa");

        test_shouldThrowException("value2", "aaa");
        test_shouldThrowException("value2", "");
    }

    @Test
    void testExecute_whenNumberValueIsInvalid_shouldThrowException() throws NoSuchFieldException {
        testExecute_whenNumberIsPassed_shouldThrowException("pValue3");
        testExecute_whenNumberIsPassed_shouldThrowException("pValue4");
        testExecute_whenNumberIsPassed_shouldThrowException("pValue5");
        testExecute_whenNumberIsPassed_shouldThrowException("pValue6");

        test_shouldThrowException("pValue7", "aaa");
        test_shouldThrowException("pValue8", "aaa");

        testExecute_whenNumberIsPassed_shouldThrowException("value3");
        testExecute_whenNumberIsPassed_shouldThrowException("value4");
        testExecute_whenNumberIsPassed_shouldThrowException("value5");
        testExecute_whenNumberIsPassed_shouldThrowException("value6");

        test_shouldThrowException("value7", "aaa");
        test_shouldThrowException("value8", "aaa");
    }

    @Test
    void testExecute_whenNumberIsExceeded_shouldThrowException() throws NoSuchFieldException {
        test_shouldThrowException("pValue3", String.valueOf(Byte.MIN_VALUE - 1));
        test_shouldThrowException("pValue3", String.valueOf(Byte.MAX_VALUE + 1));

        test_shouldThrowException("pValue4", String.valueOf(Short.MIN_VALUE - 1));
        test_shouldThrowException("pValue4", String.valueOf(Short.MAX_VALUE + 1));

        test_shouldThrowException("pValue5", String.valueOf((long) Integer.MIN_VALUE - 1));
        test_shouldThrowException("pValue5", String.valueOf((long) Integer.MAX_VALUE + 1));

        test_shouldThrowException("pValue6", String.valueOf((float) Long.MIN_VALUE - 1));
        test_shouldThrowException("pValue6", String.valueOf((float) Long.MAX_VALUE + 1));

        test_shouldThrowException("pValue7", String.valueOf((double) Float.MIN_VALUE - 1));
        test_shouldThrowException("pValue7", String.valueOf((double) Float.MAX_VALUE + 1));

        test_shouldThrowException("value3", String.valueOf(Byte.MIN_VALUE - 1));
        test_shouldThrowException("value3", String.valueOf(Byte.MAX_VALUE + 1));

        test_shouldThrowException("value4", String.valueOf(Short.MIN_VALUE - 1));
        test_shouldThrowException("value4", String.valueOf(Short.MAX_VALUE + 1));

        test_shouldThrowException("value5", String.valueOf((long) Integer.MIN_VALUE - 1));
        test_shouldThrowException("value5", String.valueOf((long) Integer.MAX_VALUE + 1));

        test_shouldThrowException("value6", String.valueOf((float) Long.MIN_VALUE - 1));
        test_shouldThrowException("value6", String.valueOf((float) Long.MAX_VALUE + 1));

        test_shouldThrowException("value7", String.valueOf((double) Float.MIN_VALUE - 1));
        test_shouldThrowException("value7", String.valueOf((double) Float.MAX_VALUE + 1));
    }

    private void testExecute_whenBooleanIsPassed(String fieldName) throws NoSuchFieldException {
        test(fieldName, "true");
        test(fieldName, "false");
        test(fieldName, "null");
        test(fieldName, "a");
    }

    private <T extends Number> void testExecute_whenNumberIsPassed(String fieldName, T minValue, T maxValue)
            throws NoSuchFieldException {
        test(fieldName, "0");
        test(fieldName, "16");
        test(fieldName, String.valueOf(minValue));
        test(fieldName, String.valueOf(maxValue));
    }

    private void testExecute_whenNumberIsPassed_shouldThrowException(String fieldName) throws NoSuchFieldException {
        test_shouldThrowException(fieldName, "0.0");
        test_shouldThrowException(fieldName, "aaa");
    }

    private <T extends Number> void testExecute_whenFloatNumberIsPassed(String fieldName, T minValue, T maxValue)
            throws NoSuchFieldException {
        test(fieldName, "0");
        test(fieldName, "10.1");
        test(fieldName, String.valueOf(minValue));
        test(fieldName, String.valueOf(maxValue));
    }

    private void test(String fieldName, String value) throws NoSuchFieldException {
        Field field = SingleFieldsItem.class.getDeclaredField(fieldName);

        mapping.singleObjects.put(field, value);

        command.setMappings(Collections.singleton(mapping));
        command.execute();
    }

    private void test_shouldThrowException(String fieldName, String value) throws NoSuchFieldException {
        Field field = SingleFieldsItem.class.getDeclaredField(fieldName);
        Collection<Mapping> mappings = Collections.singleton(mapping);

        mapping.singleObjects.put(field, value);

        command.setMappings(mappings);
        Assertions.assertThrows(IllegalArgumentException.class, command::execute);
    }
}
