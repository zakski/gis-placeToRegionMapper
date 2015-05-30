package com.szadowsz.io.read.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Utility class to read/validate XML files
 *
 * @author zakski
 */
public final class XMLReader {

    /**
     * Method to load and build the schema of an xml document
     *
     * @param xsdPath - the schema path
     * @return the loaded Schema object
     * @throws SAXException - throws this if unable to load the schema
     */
    private static Schema buildSchema(String xsdPath) throws SAXException {
        if (xsdPath == null) // null safety, if we don't want a schema we use null
            return null;

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new File(xsdPath));
        return schema;
    }

    /**
     * Method loads the DOM of the specified xml document
     *
     * @param xmlPath - the xml document path
     * @return the loaded DOM object
     * @throws SAXException                 - throws this if unable to load the xml document
     * @throws IOException                  TODO
     * @throws ParserConfigurationException TODO
     */
    public static Document getDocument(String xmlPath) throws SAXException, IOException, ParserConfigurationException {
        return getDocument(null, xmlPath);
    }

    /**
     * Method loads the DOM of the specified xml document
     *
     * @param xsdPath - the schema path
     * @param xmlPath - the xml document path
     * @return the loaded DOM object
     * @throws SAXException                 TODO
     * @throws IOException                  TODO
     * @throws ParserConfigurationException TODO
     */
    public static Document getDocument(String xsdPath, String xmlPath) throws SAXException, IOException, ParserConfigurationException {

        Schema schema = buildSchema(xsdPath);

        // initialise a factory with it
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setSchema(schema);

        // turn off namespace checking, this being set can cause all manner of defects
        dbf.setNamespaceAware(false);

        // validate and parse the file
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(new File(xmlPath));
    }
}
