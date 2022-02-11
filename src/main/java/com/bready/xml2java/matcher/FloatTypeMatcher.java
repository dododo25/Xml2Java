package com.bready.xml2java.matcher;

import com.bready.xml2java.annotation.Match;

@Match(value = Float.class)
public final class FloatTypeMatcher extends SimpleTypeMatcher<Float> {

    FloatTypeMatcher() {
        super(Float::valueOf);
    }
}
