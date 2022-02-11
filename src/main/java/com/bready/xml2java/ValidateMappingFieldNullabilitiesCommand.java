package com.bready.xml2java;

import java.lang.reflect.Field;

final class ValidateMappingFieldNullabilitiesCommand extends ForEachMappingCommand {

    private static final AnnotationsHandler HANDLER = AnnotationsHandler.getInstance();

    @Override
    protected void forMapping(Mapping mapping) {
        Class<?> type = mapping.obj.getClass();

        HANDLER.getSingleObjectFields(type)
                .forEach(field -> validateNullability(field, mapping.singleObjects.get(field)));
        HANDLER.getSingleReferenceFields(type)
                .forEach(field -> validateNullability(field, mapping.singleReferences.get(field)));
        HANDLER.getMultipleFields(type)
                .forEach(field -> validateNullability(field, mapping.multipleReferences.get(field)));
    }

    private void validateNullability(Field field, Object obj) {
        if (!AnnotationsHandler.isFieldNullable(field) && obj == null) {
            throw new IllegalArgumentException(String.format("Invalid null value for non-null %s field", field));
        }
    }
}
