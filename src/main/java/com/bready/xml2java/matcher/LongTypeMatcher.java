package com.bready.xml2java.matcher;

import com.bready.xml2java.annotation.Match;

@Match(value = Long.class)
public final class LongTypeMatcher extends SimpleTypeMatcher<Long> {

    LongTypeMatcher() {
        super(Long::valueOf);
    }
}
