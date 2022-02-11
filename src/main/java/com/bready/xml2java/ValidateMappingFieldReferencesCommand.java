package com.bready.xml2java;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Set;

final class ValidateMappingFieldReferencesCommand extends ForEachMappingCommand {

    @Override
    protected void forMapping(Mapping mapping) {
        mapping.singleReferences.forEach(this::validateReference);
        mapping.multipleReferences.forEach(this::validateReferences);
    }

    private void validateReference(Field field, Long ref) {
        validateReference(field.getType(), ref);
    }

    private void validateReferences(Field field, Set<Long> refs) {
        Class<?> genericType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

        if (refs != null) {
            refs.forEach(ref -> validateReference(genericType, ref));
        }
    }

    private void validateReference(Class<?> type, Long ref) {
        if (ref != null) {
            boolean res = mappings.stream()
                    .filter(mapping -> mapping.obj.getClass().equals(type))
                    .anyMatch(mapping -> ref.equals(mapping.id));

            if (!res) {
                throw new IllegalArgumentException(String
                        .format("Could not find %s entity with id=%d", type.getSimpleName(), ref));
            }
        }
    }
}
