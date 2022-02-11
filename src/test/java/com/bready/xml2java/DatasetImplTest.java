package com.bready.xml2java;

import com.bready.xml2java.dataset.Dataset;
import com.bready.xml2java.model.common.CommonItem;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

class DatasetImplTest {

    private static Dataset dataset;

    @BeforeAll
    static void setUp() throws IOException, SAXException {
        ClassLoader classLoader = InitMappingsCommandTest.class.getClassLoader();

        URL url = classLoader.getResource("datasetImplTest.xml");

        if (url == null) {
            throw new NullPointerException();
        }

        dataset = Xml2Java.parse(new File(url.getFile()));
    }

    @AfterAll
    static void tearDown() {
        dataset = null;
    }

    @Test
    void testGetTypes_shouldReturnSet() {
        Assertions.assertIterableEquals(Collections.singleton(CommonItem.class), dataset.getTypes());
    }

    @Test
    void testGetObjectsOfType_shouldReturnCollection() {
        Collection<CommonItem> expected = Arrays.asList(
                new CommonItem("test #1"),
                new CommonItem("test #2"),
                new CommonItem("alternative #1")
        );

        Assertions.assertIterableEquals(expected, dataset.getObjectsOfType(CommonItem.class));
    }

    @Test
    void testFindObjects_shouldReturnCollection() {
        Collection<CommonItem> expected = Arrays.asList(new CommonItem("test #1"), new CommonItem("test #2"));
        Collection<CommonItem> values = dataset.findObjects(CommonItem.class,
                item -> item.getValue().startsWith("test"));

        Assertions.assertIterableEquals(expected, values);
    }

    @Test
    void testFindObject_shouldReturnObject() {
        String testValue = "test #1";

        CommonItem expected = new CommonItem(testValue);
        Optional<CommonItem> value = dataset.findObject(CommonItem.class,
                item -> item.getValue().equalsIgnoreCase(testValue));

        if (!value.isPresent()) {
            Assertions.fail();
        }

        Assertions.assertEquals(expected, value.get());
    }

    @Test
    void testFindObject_whenPredicateIsWrong_shouldReturnEmptyObject() {
        String testValue = "test-value #1";

        Optional<CommonItem> value = dataset.findObject(CommonItem.class,
                item -> item.getValue().equalsIgnoreCase(testValue));

        Assertions.assertNull(value.orElse(null));
    }
}
