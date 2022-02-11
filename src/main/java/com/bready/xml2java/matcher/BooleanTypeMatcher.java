package com.bready.xml2java.matcher;

import com.bready.xml2java.annotation.Match;

@Match(value = Boolean.class)
public final class BooleanTypeMatcher extends SimpleTypeMatcher<Boolean> {

    BooleanTypeMatcher() {
        super(Boolean::valueOf);
    }
}
