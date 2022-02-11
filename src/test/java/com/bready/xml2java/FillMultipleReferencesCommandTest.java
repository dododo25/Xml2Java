package com.bready.xml2java;

import com.bready.xml2java.model.common.MultipleReferencesItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

class FillMultipleReferencesCommandTest {

    private static Command command;

    @BeforeEach
    void setUp() {
        command = new FillMultipleReferencesCommand();
    }

    @AfterEach
    void tearDown() {
        command = null;
    }

    @Test
    void testExecute_shouldReturnVoid() throws NoSuchFieldException {
        Mapping mapping1 = new Mapping();
        mapping1.obj = new MultipleReferencesItem();
        mapping1.multipleReferences
                .put(MultipleReferencesItem.class.getDeclaredField("cValue"), new HashSet<>(Arrays.asList(1L, 2L)));

        Mapping mapping2 = new Mapping();
        mapping2.id = 1L;
        mapping2.obj = new MultipleReferencesItem.Wrapper();

        Mapping mapping3 = new Mapping();
        mapping3.id = 2L;
        mapping3.obj = new MultipleReferencesItem.Wrapper();

        command.setMappings(Arrays.asList(mapping1, mapping2, mapping3));
        command.execute();

        Assertions.assertTrue(((MultipleReferencesItem) mapping1.obj).cValue
                .contains((MultipleReferencesItem.Wrapper) mapping2.obj));
        Assertions.assertTrue(((MultipleReferencesItem) mapping1.obj).cValue
                .contains((MultipleReferencesItem.Wrapper) mapping3.obj));
    }
}
