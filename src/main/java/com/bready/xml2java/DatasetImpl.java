package com.bready.xml2java;

import com.bready.xml2java.dataset.Dataset;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

final class DatasetImpl implements Dataset {

    private final Map<Class<?>, Collection<Object>> objects;

    DatasetImpl() {
        this.objects = new HashMap<>();
    }

    @Override
    public Set<Class<?>> getTypes() {
        return objects.keySet();
    }

    @Override
    public <T> Collection<T> getObjectsOfType(Class<T> type) {
        return objects.get(Objects.requireNonNull(type))
                .stream()
                .map(type::cast)
                .collect(Collectors.toList());
    }

    @Override
    public <T> Collection<T> findObjects(Class<T> type, Predicate<T> predicate) {
        return objects.get(Objects.requireNonNull(type))
                .stream()
                .map(type::cast)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public <T> Optional<T> findObject(Class<T> type, Predicate<T> predicate) {
        return objects.get(Objects.requireNonNull(type))
                .stream()
                .map(type::cast)
                .filter(predicate)
                .findFirst();
    }

    @Override
    public String toString() {
        return objects.entrySet()
                .stream()
                .map(entry -> prepareEntry(entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(", ", "Dataset{", "}"));
    }

    void addObject(Object obj) {
        objects.putIfAbsent(obj.getClass(), new ArrayList<>());
        objects.get(obj.getClass()).add(obj);
    }

    private String prepareEntry(Class<?> type, Collection<?> c) {
        return String.format("%s: %s", type.getSimpleName(), prepareObjects(c));
    }

    private String prepareObjects(Collection<?> c) {
        return c.stream().map(Object::toString).collect(Collectors.joining(", ", "(", ")"));
    }
}