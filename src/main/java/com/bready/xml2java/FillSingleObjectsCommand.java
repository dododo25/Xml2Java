package com.bready.xml2java;

final class FillSingleObjectsCommand extends FillObjectCommand {

    @Override
    protected void forMapping(Mapping mapping) {
        mapping.singleObjects.forEach((key, value) -> setObject(mapping.obj, key, value));
    }
}
