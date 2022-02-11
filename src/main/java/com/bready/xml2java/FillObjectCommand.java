package com.bready.xml2java;

import java.lang.reflect.Field;

abstract class FillObjectCommand extends ForEachMappingCommand {

    @SuppressWarnings("java:S3011")
    protected void setObject(Object obj, Field field, Object value) {
        try {
            boolean isAccessible = field.isAccessible();

            if (!isAccessible) {
                field.setAccessible(true);
            }

            field.set(obj, value);
            field.setAccessible(isAccessible);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected Object findObject(Class<?> type, long ref) {
        return mappings.stream()
                .filter(mapping -> mapping.obj.getClass().equals(type))
                .filter(mapping -> mapping.id == ref)
                .map(mapping -> mapping.obj)
                .findAny()
                .orElse(null);
    }
}
