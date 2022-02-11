package com.bready.xml2java.matcher;

import com.bready.xml2java.annotation.Match;

@Match(value = byte.class)
public final class PrimitiveByteTypeMatcher extends SimpleTypeMatcher<Byte> {

    PrimitiveByteTypeMatcher() {
        super(Byte::parseByte);
    }
}
