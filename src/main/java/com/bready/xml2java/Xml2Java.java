package com.bready.xml2java;

import com.bready.xml2java.dataset.Dataset;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public final class Xml2Java {

    private Xml2Java() {}

    public static Dataset parse(File file) throws IOException, SAXException {
        DatasetImpl dataset = new DatasetImpl();
        Node node = createFor(file);

        new DatasetCommandsOrganizer()
                .addCommand(new ValidateNodesCommand(node))
                .addCommand(new InitMappingsCommand(node))
                .addCommand(new ValidateMappingFieldNullabilitiesCommand())
                .addCommand(new ValidateMappingFieldReferencesCommand())
                .addCommand(new ValidateMappingFieldRepresentationsCommand())
                .addCommand(new PrepareSingleFieldsCommand())
                .addCommand(new FillSingleObjectsCommand())
                .addCommand(new FillSingleReferencesCommand())
                .addCommand(new FillMultipleReferencesCommand())
                .addCommand(new FillDatasetCommand(dataset))
                .execute();

        return dataset;
    }

    private static Node createFor(File file) throws SAXException, IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            return new Node(document.getDocumentElement());
        } catch (ParserConfigurationException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
