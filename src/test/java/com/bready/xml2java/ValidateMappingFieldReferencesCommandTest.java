package com.bready.xml2java;

import com.bready.xml2java.model.ValidateMappingFieldReferencesCommandItem;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

class ValidateMappingFieldReferencesCommandTest {

    private static Command command;

    @BeforeAll
    static void setUp() {
        command = new ValidateMappingFieldReferencesCommand();
    }

    @AfterAll
    static void tearDown() {
        command = null;
    }

    @Test
    void testExecute_shouldReturnVoid() throws NoSuchFieldException {
        test(Arrays.asList(
                createMapping(1L, 2L, Collections.singleton(2L)),
                createMapping(2L, 1L, Collections.emptySet())));
    }

    @Test
    void testExecute_whenInputIsInvalid_shouldThrowException() throws NoSuchFieldException {
        testExecute_whenInputIsInvalid_shouldThrowException(createMapping(1L, 2L, null));
        testExecute_whenInputIsInvalid_shouldThrowException(createMapping(1L, null, Collections.singleton(2L)));
    }

    private void testExecute_whenInputIsInvalid_shouldThrowException(Mapping mapping) {
        Collection<Mapping> mappings = Collections.singleton(mapping);
        Assertions.assertThrows(IllegalArgumentException.class, () -> test(mappings));
    }

    private Mapping createMapping(Long id, Long singleReference, Set<Long> multipleReferences)
            throws NoSuchFieldException {
        Mapping mapping = new Mapping();

        mapping.id = id;
        mapping.obj = new ValidateMappingFieldReferencesCommandItem();
        mapping.singleReferences
                .put(ValidateMappingFieldReferencesCommandItem.class.getDeclaredField("rValue1"), singleReference);
        mapping.multipleReferences
                .put(ValidateMappingFieldReferencesCommandItem.class.getDeclaredField("cValue1"), multipleReferences);

        return mapping;
    }

    private void test(Collection<Mapping> mappings) {
        command.setMappings(mappings);
        command.execute();
    }
}
