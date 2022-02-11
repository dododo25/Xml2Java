package com.bready.xml2java;

import java.util.*;
import java.util.stream.Collectors;

final class ValidateNodesCommand extends Command {

    private final Node root;

    public ValidateNodesCommand(Node root) {
        this.root = root;
    }

    @Override
    public void execute() {
        root.getChildNodes().forEach(this::validateNodes);
    }

    private void validateNodes(Node node) {
        node.getChildNodes().forEach(child -> validateNodes(node, child));
    }

    private void validateNodes(Node root, Node node) {
        Collection<Node> invalidNodes = node.getChildNodes()
                .stream()
                .filter(this::isInvalid)
                .collect(Collectors.toSet());

        if (!invalidNodes.isEmpty()) {
            throw new IllegalArgumentException(String.format("Invalid child node %s for %s",
                    prepareInvalidNodesString(invalidNodes), root.getName()));
        }
    }

    private boolean isInvalid(Node node) {
        Map<String, String> attrs = node.getAttributes();

        return !(node.getChildNodes().isEmpty()
                && node.getName().equalsIgnoreCase("ref")
                && attrs.size() == 1
                && attrs.containsKey("id"));
    }

    private static String prepareInvalidNodesString(Collection<Node> list) {
        return list.stream().map(Node::getName).collect(Collectors.joining(", "));
    }
}
