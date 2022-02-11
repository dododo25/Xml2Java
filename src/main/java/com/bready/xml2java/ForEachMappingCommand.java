package com.bready.xml2java;

import java.lang.reflect.Field;

abstract class ForEachMappingCommand extends Command {

    @Override
    public final void execute() {
        mappings.forEach(this::forMapping);
    }

    protected abstract void forMapping(Mapping mapping);
}
