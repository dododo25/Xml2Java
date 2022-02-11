package com.bready.xml2java.matcher;

import com.bready.xml2java.annotation.Match;

@Match(value = long.class)
public final class PrimitiveLongTypeMatcher extends SimpleTypeMatcher<Long> {

    PrimitiveLongTypeMatcher() {
        super(Long::parseLong);
    }
}
