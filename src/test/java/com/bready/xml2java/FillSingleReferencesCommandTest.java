package com.bready.xml2java;

import com.bready.xml2java.model.common.SingleReferencesItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class FillSingleReferencesCommandTest {

    private static Command command;

    @BeforeEach
    void setUp() {
        command = new FillSingleReferencesCommand();
    }

    @AfterEach
    void tearDown() {
        command = null;
    }

    @Test
    void testExecute_shouldReturnVoid() throws NoSuchFieldException {
        Mapping mapping1 = new Mapping();
        mapping1.obj = new SingleReferencesItem();
        mapping1.singleReferences.put(SingleReferencesItem.class.getDeclaredField("value"), 1L);

        Mapping mapping2 = new Mapping();
        mapping2.id = 1L;
        mapping2.obj = new SingleReferencesItem.Wrapper();

        command.setMappings(Arrays.asList(mapping1, mapping2));
        command.execute();
        Assertions.assertEquals(mapping2.obj, ((SingleReferencesItem) mapping1.obj).value);
    }
}
