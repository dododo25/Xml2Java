package com.bready.xml2java;

import java.util.*;

public class DatasetCommandsOrganizer {

    private final List<Command> commands;

    private final Collection<Mapping> mappings;

    public DatasetCommandsOrganizer() {
        this.commands = new ArrayList<>();
        this.mappings = new ArrayList<>();
    }

    public DatasetCommandsOrganizer addCommand(Command command) {
        commands.add(command);
        command.setMappings(mappings);
        return this;
    }

    public void execute() {
        commands.forEach(Command::execute);
    }
}
