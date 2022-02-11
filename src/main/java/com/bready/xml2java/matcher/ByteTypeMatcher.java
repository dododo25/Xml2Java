package com.bready.xml2java.matcher;

import com.bready.xml2java.annotation.Match;

@Match(value = Byte.class)
public final class ByteTypeMatcher extends SimpleTypeMatcher<Byte> {

    ByteTypeMatcher() {
        super(Byte::valueOf);
    }
}
