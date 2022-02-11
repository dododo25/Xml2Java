package com.bready.xml2java.model;

import com.bready.xml2java.annotation.Multiple;
import com.bready.xml2java.annotation.Single;
import com.bready.xml2java.annotation.Tag;

@Tag
public class AnnotationsHandlerItem {
        
    @Single
    private Object sValue1;

    @Single(name = "testName")
    private Object sValue2;

    @Single(nullable = false)
    private Object sValue3;

    @Single(reference = true)
    private Object sValue4;

    @Multiple
    private Object mValue1;

    @Multiple(name = "testName")
    private Object mValue2;

    @Multiple(nullable = false)
    private Object mValue3;

    @Tag("testName")
    public static class Wrapped {

    }
}