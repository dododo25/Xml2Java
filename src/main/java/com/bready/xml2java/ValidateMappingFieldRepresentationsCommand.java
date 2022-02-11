package com.bready.xml2java;

import com.bready.xml2java.matcher.TypeMatcher;

import java.lang.reflect.Field;

final class ValidateMappingFieldRepresentationsCommand extends ForEachMappingCommand {

    private static final AnnotationsHandler HANDLER = AnnotationsHandler.getInstance();

    @Override
    protected void forMapping(Mapping mapping) {
        mapping.singleObjects.forEach(this::validateRepresentation);
    }

    private void validateRepresentation(Field field, Object obj) {
        TypeMatcher<?> matcher = HANDLER.getMatcher(field.getType());

        if (matcher == null) {
            throw new IllegalArgumentException(String.format("Unknown type %s", field.getType()));
        }

        matcher.match(String.valueOf(obj));
    }
}
