package com.bready.xml2java.matcher;

import com.bready.xml2java.annotation.Match;

@Match(value = boolean.class)
public final class PrimitiveBooleanTypeMatcher extends SimpleTypeMatcher<Boolean> {

    PrimitiveBooleanTypeMatcher() {
        super(Boolean::parseBoolean);
    }
}
