package com.bready.xml2java.matcher;

import com.bready.xml2java.annotation.Match;

@Match(value = int.class)
public final class PrimitiveIntTypeMatcher extends SimpleTypeMatcher<Integer> {

    PrimitiveIntTypeMatcher() {
        super(Integer::parseInt);
    }
}
