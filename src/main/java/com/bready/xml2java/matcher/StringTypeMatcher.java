package com.bready.xml2java.matcher;

import com.bready.xml2java.annotation.Match;

import java.util.function.Function;

@Match(value = String.class)
public final class StringTypeMatcher extends SimpleTypeMatcher<String> {

    StringTypeMatcher() {
        super(Function.identity());
    }
}
