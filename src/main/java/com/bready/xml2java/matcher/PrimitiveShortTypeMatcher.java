package com.bready.xml2java.matcher;

import com.bready.xml2java.annotation.Match;

@Match(value = short.class)
public final class PrimitiveShortTypeMatcher extends SimpleTypeMatcher<Short> {

    PrimitiveShortTypeMatcher() {
        super(Short::parseShort);
    }
}
