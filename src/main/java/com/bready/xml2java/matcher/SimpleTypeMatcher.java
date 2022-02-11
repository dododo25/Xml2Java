package com.bready.xml2java.matcher;

import java.util.function.Function;

public abstract class SimpleTypeMatcher<T> implements TypeMatcher<T> {

    private final Function<String, T> function;

    protected SimpleTypeMatcher(Function<String, T> function) {
        this.function = function;
    }

    @Override
    public final T match(String value) {
        return function.apply(value);
    }
}
