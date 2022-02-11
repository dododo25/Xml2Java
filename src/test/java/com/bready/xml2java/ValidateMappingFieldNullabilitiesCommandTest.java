package com.bready.xml2java;

import com.bready.xml2java.model.ValidateMappingFieldNullabilitiesCommandItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class ValidateMappingFieldNullabilitiesCommandTest {

    @Test
    void testExecute_shouldReturnVoid() throws NoSuchFieldException {
        test(createMapping("0"));
    }

    @Test
    void testExecute_whenInputIsInvalid_shouldThrowException() throws NoSuchFieldException {
        Mapping mapping = createMapping(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> test(mapping));
    }

    private void test(Mapping mapping) {
        Command command = new ValidateMappingFieldNullabilitiesCommand();
        command.setMappings(Collections.singleton(mapping));
        command.execute();
    }

    private Mapping createMapping(String singleObject) throws NoSuchFieldException {
        Mapping mapping = new Mapping();

        mapping.obj = new ValidateMappingFieldNullabilitiesCommandItem();
        mapping.singleObjects
                .put(ValidateMappingFieldNullabilitiesCommandItem.class.getDeclaredField("pValue1"), singleObject);

        return mapping;
    }
}
