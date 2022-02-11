package com.bready.xml2java;

import com.bready.xml2java.util.NodesUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

class ValidateNodesCommandTest {

    @Test
    void testExecute_shouldReturnVoid() throws IOException, SAXException {
        Command command = new ValidateNodesCommand(NodesUtil.collectNode("validateNodesCommandTest.test1.xml"));
        Assertions.assertDoesNotThrow(command::execute);
    }

    @Test
    void testExecute_whenTypeInputIsInvalid_shouldThrowIllegalArgumentException() throws IOException, SAXException {
        testExecute_shouldThrowIllegalArgumentException("validateNodesCommandTest.test2.xml");
    }

    @Test
    void testExecute_whenTypeInputIsInvalid_shouldThrowIllegalArgumentException1() throws IOException, SAXException {
        testExecute_shouldThrowIllegalArgumentException("validateNodesCommandTest.test3.xml");
    }

    @Test
    void testExecute_whenTypeInputIsInvalid_shouldThrowIllegalArgumentException2() throws IOException, SAXException {
        testExecute_shouldThrowIllegalArgumentException("validateNodesCommandTest.test4.xml");
    }

    private void testExecute_shouldThrowIllegalArgumentException(String fileName) throws IOException, SAXException {
        Command command = new ValidateNodesCommand(NodesUtil.collectNode(fileName));
        Assertions.assertThrows(IllegalArgumentException.class, command::execute);
    }
}
