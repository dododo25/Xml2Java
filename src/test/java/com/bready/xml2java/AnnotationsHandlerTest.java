package com.bready.xml2java;

import com.bready.xml2java.model.AnnotationsHandlerItem;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;

class AnnotationsHandlerTest {

    private static final Class<?> ITEM_TYPE_1 = AnnotationsHandlerItem.class;

    private static final Class<?> ITEM_TYPE_2 = AnnotationsHandlerItem.Wrapped.class;

    private static final Class<?> INVALID_TYPE = Class.class;

    private static AnnotationsHandler handler;

    @BeforeAll
    static void setUp() {
        handler = AnnotationsHandler.getInstance();
    }

    @AfterAll
    static void tearDown() {
        handler = null;
    }

    @Test
    void testGetType_shouldReturnClass() {
        Assertions.assertEquals(ITEM_TYPE_1, handler.getType("annotationsHandlerItem"));
        Assertions.assertEquals(ITEM_TYPE_2, handler.getType("testName"));
    }

    @Test
    void testGetType_whenArgumentIsWrong_shouldReturnNull() {
        Assertions.assertNull(handler.getType("__invalid"));
    }

    @Test
    void testGetSingleObjectFields_shouldReturnSet() throws NoSuchFieldException {
        Collection<Field> singlePrimaryObjectFields = new HashSet<>(Arrays.asList(
                ITEM_TYPE_1.getDeclaredField("sValue1"),
                ITEM_TYPE_1.getDeclaredField("sValue2"),
                ITEM_TYPE_1.getDeclaredField("sValue3")
        ));

        Assertions.assertIterableEquals(singlePrimaryObjectFields, handler.getSingleObjectFields(ITEM_TYPE_1));
    }

    @Test
    void testGetSingleReferenceFields_shouldReturnSet() throws NoSuchFieldException {
        Collection<Field> singlePrimaryObjectFields = Collections.singleton(
                ITEM_TYPE_1.getDeclaredField("sValue4"));

        Assertions.assertIterableEquals(singlePrimaryObjectFields, handler.getSingleReferenceFields(ITEM_TYPE_1));
    }

    @Test
    void testGetMultipleFields_shouldReturnSet() throws NoSuchFieldException {
        Collection<Field> singlePrimaryObjectFields = new HashSet<>(Arrays.asList(
                ITEM_TYPE_1.getDeclaredField("mValue1"),
                ITEM_TYPE_1.getDeclaredField("mValue2"),
                ITEM_TYPE_1.getDeclaredField("mValue3")
        ));

        Assertions.assertIterableEquals(singlePrimaryObjectFields, handler.getMultipleFields(ITEM_TYPE_1));
    }

    @Test
    void testIsFieldNullable_shouldReturnBoolean() throws NoSuchFieldException {
        Assertions.assertTrue(AnnotationsHandler.isFieldNullable(ITEM_TYPE_1.getDeclaredField("sValue1")));
        Assertions.assertTrue(AnnotationsHandler.isFieldNullable(ITEM_TYPE_1.getDeclaredField("sValue2")));
        Assertions.assertTrue(AnnotationsHandler.isFieldNullable(ITEM_TYPE_1.getDeclaredField("sValue4")));

        Assertions.assertTrue(AnnotationsHandler.isFieldNullable(ITEM_TYPE_1.getDeclaredField("mValue1")));
        Assertions.assertTrue(AnnotationsHandler.isFieldNullable(ITEM_TYPE_1.getDeclaredField("mValue2")));

        Assertions.assertFalse(AnnotationsHandler.isFieldNullable(ITEM_TYPE_1.getDeclaredField("sValue3")));
        Assertions.assertFalse(AnnotationsHandler.isFieldNullable(ITEM_TYPE_1.getDeclaredField("mValue3")));
    }

    @Test
    void testGetFieldName_shouldReturnString() throws NoSuchFieldException {
        Assertions.assertEquals("sValue1", AnnotationsHandler.getFieldName(ITEM_TYPE_1.getDeclaredField("sValue1")));
        Assertions.assertEquals("testName", AnnotationsHandler.getFieldName(ITEM_TYPE_1.getDeclaredField("sValue2")));

        Assertions.assertEquals("mValue1", AnnotationsHandler.getFieldName(ITEM_TYPE_1.getDeclaredField("mValue1")));
        Assertions.assertEquals("testName", AnnotationsHandler.getFieldName(ITEM_TYPE_1.getDeclaredField("mValue2")));
    }

    @Test
    void testGetSingleObjectFields_whenTypeIsWrong_shouldReturnNull() {
        Assertions.assertTrue(handler.getSingleObjectFields(INVALID_TYPE).isEmpty());
    }

    @Test
    void testGetSingleReferenceFields_whenTypeIsWrong_shouldReturnNull() {
        Assertions.assertTrue(handler.getSingleReferenceFields(INVALID_TYPE).isEmpty());
    }

    @Test
    void testGetMultipleFields_whenTypeIsWrong_shouldReturnNull() {
        Assertions.assertTrue(handler.getMultipleFields(INVALID_TYPE).isEmpty());
    }
}
