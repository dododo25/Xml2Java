package com.bready.xml2java.model;

import com.bready.xml2java.annotation.Multiple;
import com.bready.xml2java.annotation.Single;
import com.bready.xml2java.annotation.Tag;

import java.util.Collection;

@Tag
public class InitMappingsCommandItem {

    @Single
    private int pValue1;

    @Single(reference = true)
    private InitMappingsCommandItem rValue1;

    @Multiple
    private Collection<InitMappingsCommandItem> cValue1;

    private Object transientObj;

}