package com.bready.xml2java.model;

import com.bready.xml2java.annotation.Multiple;
import com.bready.xml2java.annotation.Single;
import com.bready.xml2java.annotation.Tag;

import java.util.Collection;

@Tag
public class ValidateMappingFieldReferencesCommandItem {

    @Single(reference=true)
    private ValidateMappingFieldReferencesCommandItem rValue1;

    @Multiple(nullable = false)
    private Collection<ValidateMappingFieldReferencesCommandItem> cValue1;
}
