package com.bready.xml2java.util;

import com.bready.xml2java.Node;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class NodesUtil {

    public static Node collectNode(String fileName) throws IOException, SAXException {
        ClassLoader classLoader = NodesUtil.class.getClassLoader();

        URL url = classLoader.getResource(fileName);

        if (url == null) {
            throw new NullPointerException();
        }

        return collectNode(new File(url.getFile()));
    }

    private static Node collectNode(File file) throws SAXException, IOException {
        org.w3c.dom.Node root = null;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            root = document.getDocumentElement();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        if (root == null) {
            return null;
        }

        return new Node(root);
    }
}
