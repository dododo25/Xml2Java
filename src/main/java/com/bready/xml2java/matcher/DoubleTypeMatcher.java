package com.bready.xml2java.matcher;

import com.bready.xml2java.annotation.Match;

@Match(value = Double.class)
public final class DoubleTypeMatcher extends SimpleTypeMatcher<Double> {

    DoubleTypeMatcher() {
        super(Double::valueOf);
    }
}
