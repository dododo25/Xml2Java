package com.bready.xml2java.model.common;

import com.bready.xml2java.annotation.Single;
import com.bready.xml2java.annotation.Tag;

@Tag
public class CommonItem {

    @Single
    private String value;

    public CommonItem() {}

    public CommonItem(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof CommonItem && ((CommonItem) obj).value.equalsIgnoreCase(value);
    }
}
