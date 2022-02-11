package com.bready.xml2java;

import com.bready.xml2java.matcher.TypeMatcher;

final class PrepareSingleFieldsCommand extends ForEachMappingCommand {

    private static final AnnotationsHandler HANDLER = AnnotationsHandler.getInstance();

    @Override
    protected void forMapping(Mapping mapping) {
        mapping.singleObjects.forEach((key, value) ->
                mapping.singleObjects.replace(key, prepareObject(key.getType(), value)));
    }

    private Object prepareObject(Class<?> type, Object obj) {
        TypeMatcher<?> matcher = HANDLER.getMatcher(type);

        if (matcher == null) {
            throw new IllegalArgumentException();
        }

        return matcher.match(String.valueOf(obj));
    }
}
