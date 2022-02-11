package com.bready.xml2java.matcher;

import com.bready.xml2java.annotation.Match;

@Match(value = Integer.class)
public final class IntTypeMatcher extends SimpleTypeMatcher<Integer> {

    IntTypeMatcher() {
        super(Integer::valueOf);
    }
}
