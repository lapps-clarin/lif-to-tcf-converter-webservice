/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.tests.utils;

import de.tuebingen.uni.sfs.lapps.lifconverter.resources.ConverterResource;
import de.tuebingen.uni.sfs.lapps.lifconverter.utils.StreamingOutputExtended;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceEngine;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class LifToTcfConversionAssertUtils {
    
    public static void lifToTcf(InputStream input) throws IOException, ParserConfigurationException, FileNotFoundException, SAXException {
        ConverterResource instance = new ConverterResource();
        StreamingOutputExtended streamingOutput = instance.processWithStreaming(input);
        File outputFile = streamingOutput.getFile();
        InputStream result = new FileInputStream(outputFile);
    }

    public static void lifToTcfAssertEqual(InputStream input, InputStream expectedOutput) throws IOException, ParserConfigurationException, FileNotFoundException, SAXException {
        ConverterResource instance = new ConverterResource();
        StreamingOutputExtended streamingOutput = instance.processWithStreaming(input);
        File outputFile = streamingOutput.getFile();
        InputStream result = new FileInputStream(outputFile);
        assertEqualXml(result, expectedOutput);
    }

    public static void assertEqualXml(InputStream expectedFile, InputStream realFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        //dbf.setCoalescing(true);
        //dbf.setIgnoringElementContentWhitespace(true);
        //dbf.setIgnoringComments(true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc1 = db.parse(expectedFile);
        doc1.normalizeDocument();
        Document doc2 = db.parse(realFile);
        doc2.normalizeDocument();

        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreAttributeOrder(true);
        XMLUnit.setNormalize(true);

        Diff diff = new Diff(doc1, doc2);
        DetailedDiff ddiff = new DetailedDiff(diff);
        List list = ddiff.getAllDifferences();
        boolean equal = true;
        for (Object le : list) {
            Difference d = (Difference) le;
            // ignore certain differences
            if (d.equals(DifferenceEngine.NAMESPACE_PREFIX)) {
                // ignore differences in namespace prefix names
            } else if (d.equals(DifferenceEngine.SCHEMA_LOCATION)) {
                // ignore at which place the schemaLocation is inserted
            } else {
                equal = false;
                break;
            }
        }
        Assert.assertTrue("xml not identical " + diff, equal);
        //Assert.assertTrue("xml not similar " + myDiff, myDiff.similar());
    }

}
