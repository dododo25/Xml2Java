package com.bready.xml2java;

import java.lang.reflect.Field;
import java.util.*;

class Mapping {

    Object obj;
    Long id;

    final Map<Field, Object> singleObjects;
    final Map<Field, Long> singleReferences;
    final Map<Field, Set<Long>> multipleReferences;

    Mapping() {
        this.singleObjects = new HashMap<>();
        this.singleReferences = new HashMap<>();
        this.multipleReferences = new HashMap<>();
    }
}