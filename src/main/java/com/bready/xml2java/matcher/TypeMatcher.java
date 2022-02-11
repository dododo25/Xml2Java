package com.bready.xml2java.matcher;

public interface TypeMatcher<T> {

    T match(String value);
}
