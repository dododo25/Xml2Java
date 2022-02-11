package com.bready.xml2java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

final class InitMappingsCommand extends Command {

    private static final Logger LOG = LoggerFactory.getLogger(InitMappingsCommand.class);

    private static final AnnotationsHandler HANDLER = AnnotationsHandler.getInstance();

    private final Node root;

    public InitMappingsCommand(Node root) {
        this.root = root;
    }

    @Override
    public void execute() {
        root.getChildNodes().forEach(this::loadMappings);
    }

    private void loadMappings(Node node) {
        try {
            final Mapping mapping = new Mapping();

            Class<?> type = HANDLER.getType(node.getName());

            if (type == null) {
                throw new IllegalArgumentException();
            }

            mapping.obj = type.newInstance();
            node.findAttribute("id").map(Long::parseLong).ifPresent(id -> mapping.id = id);

            HANDLER.getSingleObjectFields(type).forEach(field -> addSimpleObject(node, mapping, field));
            HANDLER.getSingleReferenceFields(type).forEach(field -> addSimpleReference(node, mapping, field));
            HANDLER.getMultipleFields(type).forEach(field -> addMultipleReferences(node, mapping, field));

            mappings.add(mapping);
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private static void addSimpleObject(Node node, Mapping mapping, Field field) {
        String fieldName = AnnotationsHandler.getFieldName(field);
        node.findAttribute(fieldName)
                .ifPresent(value -> mapping.singleObjects.put(field, value));
    }

    private static void addSimpleReference(Node node, Mapping mapping, Field field) {
        String fieldName = AnnotationsHandler.getFieldName(field);
        node.findAttribute(fieldName)
                .map(Long::parseLong)
                .ifPresent(value -> mapping.singleReferences.put(field, value));
    }

    private static void addMultipleReferences(Node node, Mapping mapping, Field field) {
        String fieldName = AnnotationsHandler.getFieldName(field);

        Optional<Node> child = node.findChild(fieldName);

        if (child.isPresent()) {
            mapping.multipleReferences.putIfAbsent(field, new HashSet<>());

            child.get()
                    .getChildNodes()
                    .stream()
                    .map(refChild -> refChild.findAttribute("id"))
                    .map(refChild -> refChild.orElse(null))
                    .filter(Objects::nonNull)
                    .map(Long::parseLong)
                    .forEach(ref -> mapping.multipleReferences.get(field).add(ref));
        }
    }
}
