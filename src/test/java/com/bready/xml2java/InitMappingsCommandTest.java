package com.bready.xml2java;

import com.bready.xml2java.model.InitMappingsCommandItem;
import com.bready.xml2java.util.NodesUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

class InitMappingsCommandTest {

    @Test
    void testExecute_shouldFillMappings() throws IOException, SAXException, NoSuchFieldException {
        Command command = new InitMappingsCommand(NodesUtil.collectNode("initMappingsCommandTest.test1.xml"));

        List<Mapping> mappings = new ArrayList<>();

        command.setMappings(mappings);
        command.execute();

        Mapping mapping = mappings.get(0);

        Assertions.assertEquals(1, mapping.id);
        Assertions.assertTrue(mapping.singleObjects
                .containsKey(InitMappingsCommandItem.class.getDeclaredField("pValue1")));
        Assertions.assertTrue(mapping.singleReferences
                .containsKey(InitMappingsCommandItem.class.getDeclaredField("rValue1")));
        Assertions.assertTrue(mapping.multipleReferences
                .containsKey(InitMappingsCommandItem.class.getDeclaredField("cValue1")));

        Assertions.assertEquals("0", mapping.singleObjects
                .get(InitMappingsCommandItem.class.getDeclaredField("pValue1")));
        Assertions.assertEquals(0L, mapping.singleReferences
                .get(InitMappingsCommandItem.class.getDeclaredField("rValue1")));
        Assertions.assertIterableEquals(new HashSet<>(Arrays.asList(2L, 3L)), mapping.multipleReferences
                .get(InitMappingsCommandItem.class.getDeclaredField("cValue1")));
    }

    @Test
    void testExecute_whenTypeInputIsInvalid_shouldThrowIllegalArgumentException() throws IOException, SAXException {
        Command command = new InitMappingsCommand(NodesUtil.collectNode("initMappingsCommandTest.test2.xml"));
        command.setMappings(new ArrayList<>());
        Assertions.assertThrows(IllegalArgumentException.class, command::execute);
    }
}
