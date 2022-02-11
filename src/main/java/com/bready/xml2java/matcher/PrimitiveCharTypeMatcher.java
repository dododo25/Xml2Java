package com.bready.xml2java.matcher;

import com.bready.xml2java.annotation.Match;

@Match(value = char.class)
public final class PrimitiveCharTypeMatcher implements TypeMatcher<Character> {

    PrimitiveCharTypeMatcher() {}

    @Override
    public Character match(String value) {
        if (value.isEmpty()) {
            return Character.MIN_VALUE;
        } else if (value.length() > 1) {
            throw new IllegalArgumentException();
        } else {
            return value.charAt(0);
        }
    }
}
