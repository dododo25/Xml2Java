package com.bready.xml2java;

final class FillSingleReferencesCommand extends FillObjectCommand {

    @Override
    protected void forMapping(Mapping mapping) {
        mapping.singleReferences.forEach((key, value) ->
                setObject(mapping.obj, key, findObject(key.getType(), value)));
    }
}
