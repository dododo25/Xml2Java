package com.bready.xml2java.matcher;

import com.bready.xml2java.annotation.Match;

@Match(value = Character.class)
public final class CharTypeMatcher implements TypeMatcher<Character> {

    CharTypeMatcher() {}

    @Override
    public Character match(String value) {
        if (value.isEmpty()) {
            return null;
        } else if (value.length() > 1) {
            throw new IllegalArgumentException();
        } else {
            return value.charAt(0);
        }
    }
}
