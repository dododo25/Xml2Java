package com.bready.xml2java.matcher;

import com.bready.xml2java.annotation.Match;

@Match(value = float.class)
public final class PrimitiveFloatTypeMatcher extends SimpleTypeMatcher<Float> {

    PrimitiveFloatTypeMatcher() {
        super(Float::parseFloat);
    }
}
