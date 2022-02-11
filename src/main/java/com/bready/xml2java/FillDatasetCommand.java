package com.bready.xml2java;

final class FillDatasetCommand extends ForEachMappingCommand {

    private final DatasetImpl dataset;

    public FillDatasetCommand(DatasetImpl dataset) {
        this.dataset = dataset;
    }

    @Override
    protected void forMapping(Mapping mapping) {
        dataset.addObject(mapping.obj);
    }
}
