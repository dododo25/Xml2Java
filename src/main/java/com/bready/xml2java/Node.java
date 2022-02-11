package com.bready.xml2java;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Node {

    private final org.w3c.dom.Node wrapped;

    public Node(org.w3c.dom.Node wrapped) {
        this.wrapped = wrapped;
    }

    public String getName() {
        return wrapped.getNodeName();
    }

    public Map<String, String> getAttributes() {
        NamedNodeMap map = wrapped.getAttributes();
        return IntStream.range(0, map.getLength())
                .mapToObj(map::item)
                .collect(Collectors.toMap(org.w3c.dom.Node::getNodeName, org.w3c.dom.Node::getNodeValue));
    }

    public Optional<String> findAttribute(String key) {
        return Optional.ofNullable(wrapped.getAttributes().getNamedItem(key))
                .map(org.w3c.dom.Node::getNodeValue);
    }

    public List<Node> getChildNodes() {
        NodeList list = wrapped.getChildNodes();
        return IntStream.range(0, list.getLength())
                .mapToObj(list::item)
                .filter(child -> child.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE)
                .map(Node::new)
                .collect(Collectors.toList());
    }

    public Optional<Node> findChild(String name) {
        return getChildNodes().stream().filter(child -> child.getName().equalsIgnoreCase(name)).findAny();
    }

    @Override
    public int hashCode() {
        return wrapped.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
                || obj instanceof Node && ((Node) obj).wrapped.equals(wrapped)
                || obj instanceof org.w3c.dom.Node && obj.equals(wrapped);
    }

    @Override
    public String toString() {
        return String.format("%s:{%s}", wrapped.getNodeName(), prepareAttributesString());
    }

    private String prepareAttributesString() {
        NamedNodeMap map = wrapped.getAttributes();
        return IntStream.range(0, map.getLength())
                .mapToObj(map::item)
                .map(attr -> String.format("%s=%s", attr.getNodeName(), attr.getNodeValue()))
                .collect(Collectors.joining(", "));
    }
}
