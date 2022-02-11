package com.bready.xml2java;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

final class FillMultipleReferencesCommand extends FillObjectCommand {

    private final Map<Class<?>, UnaryOperator<Collection<Object>>> functions;

    public FillMultipleReferencesCommand() {
        this.functions = new HashMap<>();
        this.functions.put(Collection.class, ArrayList::new);
        this.functions.put(List.class, ArrayList::new);
        this.functions.put(Set.class, HashSet::new);
    }

    @Override
    protected void forMapping(Mapping mapping) {
        mapping.multipleReferences.forEach((key, value) -> setObject(mapping.obj, key, prepareObject(key, value)));
    }

    private Object prepareObject(Field field, Collection<Long> refs) {
        UnaryOperator<Collection<Object>> operator = functions.get(field.getType());
        Class<?> genericType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

        if (operator == null) {
            throw new IllegalArgumentException();
        }

        return operator.apply(findObjects(genericType, refs));
    }

    private Collection<Object> findObjects(Class<?> genericType, Collection<Long> refs) {
        return refs.stream()
                .map(ref -> findObject(genericType, ref))
                .collect(Collectors.toList());
    }
}
