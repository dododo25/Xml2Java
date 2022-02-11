package com.bready.xml2java.matcher;

import com.bready.xml2java.annotation.Match;

@Match(value = double.class)
public final class PrimitiveDoubleTypeMatcher extends SimpleTypeMatcher<Double> {

    PrimitiveDoubleTypeMatcher() {
        super(Double::parseDouble);
    }
}
