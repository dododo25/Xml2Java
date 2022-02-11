package com.bready.xml2java.matcher;

import com.bready.xml2java.annotation.Match;

@Match(value = Short.class)
public final class ShortTypeMatcher extends SimpleTypeMatcher<Short> {

    ShortTypeMatcher() {
        super(Short::valueOf);
    }
}
