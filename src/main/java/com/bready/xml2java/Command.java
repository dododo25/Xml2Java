package com.bready.xml2java;

import java.util.*;

abstract class Command {

    protected Collection<Mapping> mappings;

    public abstract void execute();

    void setMappings(Collection<Mapping> mappings) {
        this.mappings = mappings;
    }
}
