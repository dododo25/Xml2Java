package com.bready.xml2java.dataset;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public interface Dataset {

    Set<Class<?>> getTypes();

    <T> Collection<T> getObjectsOfType(Class<T> type);

    <T> Collection<T> findObjects(Class<T> type, Predicate<T> predicate);

    <T> Optional<T> findObject(Class<T> type, Predicate<T> predicate);
}
