/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel;

import de.tuebingen.uni.sfs.lapps.library.layer.api.AnnotationLayerFinder;
import de.tuebingen.uni.sfs.lapps.library.layer.xb.AnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.createannotation.ConstParseInputCreation;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.createannotation.DependencyParseInputCreation;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.createannotation.NameEntityInputCreation;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.createannotation.SenetenceInputCreation;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.createannotation.TokenInputCreation;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.conversion.ToTCFAnnotationLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusStored;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
/**
 *
 * @author felahi
 */
public class LifInputTcfTest {

    private static List<AnnotationInterpreter> tokenAnnotations = new ArrayList<AnnotationInterpreter>();
    private static List<AnnotationInterpreter> senetenceAnnotations = new ArrayList<AnnotationInterpreter>();

    @BeforeClass
    public static void setUpClass() {
        AnnotationInterpreter.elementIdMapper = new HashMap<String, AnnotationInterpreter>();
        tokenAnnotations = new TokenInputCreation().getTokenAnnotations();
        senetenceAnnotations = new SenetenceInputCreation().getSentenceAnnotations();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of toLayers method, of class ToTCFAnnotationLayer.
     *
     */
    @Ignore
    public void testToLayers() throws Exception {
        System.out.println("toLayers");
        String givenLayer = "";
         AnnotationLayerFinder layer = null;
        List<AnnotationInterpreter> annotationlist = null;
        ToTCFAnnotationLayer instance = null;
        instance.toLayers(layer,annotationlist);
        fail("The test case is a prototype.");
    }

    /**
     * Test of toLayer method, of class ToTCFAnnotationLayer.
     *
     */
    @Ignore
    public void testToLayer() throws Exception {
        System.out.println("toLayer");
        String givenLayer = "";
        ToTCFAnnotationLayer instance = null;
        instance.toLayer(givenLayer);
        fail("The test case is a prototype.");
    }

    /**
     * Test of toLanguage method, of class ToTCFAnnotationLayer.
     */
    @Test
    public void testToLanguage() throws Exception {
        System.out.println("toLanguage");
        String language = "en";
        ToTCFAnnotationLayer instance = new ToTCFAnnotationLayer(null);
        instance.toLanguage(language);
        instance.getTextCorpusStored().getLanguage();
        System.out.println(instance.getTextCorpusStored().getLanguage());
        assertEquals("en", instance.getTextCorpusStored().getLanguage());
    }

    /**
     * Test of toText method, of class ToTCFAnnotationLayer.
     */
    @Test
    public void testToText() throws Exception {
        System.out.println("toText");
        ToTCFAnnotationLayer instance = new ToTCFAnnotationLayer(null);
        instance.toText("Karen flew to New York.");
        assertEquals("text : Karen flew to New York.", instance.getTextCorpusStored().getTextLayer().toString());
    }

    /**
     * Test of toToken method, of class ToTCFAnnotationLayer.
     */
    @Test
    public void testToToken() throws Exception {
        System.out.println("toToken");
        ToTCFAnnotationLayer instance = new ToTCFAnnotationLayer(null);
        instance.setGivenAnnotations(tokenAnnotations);
        instance.toToken();
        assertEquals("0: t_0 -> Karen", instance.getTextCorpusStored().getTokensLayer().getToken(0).toString());
    }

    /**
     * Test of toPos method, of class ToTCFAnnotationLayer.
     */
    @Test
    public void testToPos() throws Exception {
        System.out.println("toPos");
        ToTCFAnnotationLayer instance = new ToTCFAnnotationLayer(null);
        instance.setGivenAnnotations(tokenAnnotations);
        instance.toPos();
        assertEquals("NNP [t_0]", instance.getTextCorpusStored().getPosTagsLayer().getTag(0).toString());
    }

    /**
     * Test of toLemma method, of class ToTCFAnnotationLayer.
     */
    @Test
    public void testToLemma() throws Exception {
        System.out.println("toLemma");
        ToTCFAnnotationLayer instance = new ToTCFAnnotationLayer(null);
        instance.setGivenAnnotations(tokenAnnotations);
        instance.toLemma();
        assertEquals("l_0 -> Karen [t_0]", instance.getTextCorpusStored().getLemmasLayer().getLemma(0).toString());
    }

    /**
     * Test of toSentences method, of class ToTCFAnnotationLayer.
     *
     */
    @Test
    public void testToSentences() throws Exception {
        System.out.println("toSentences");
        ToTCFAnnotationLayer instance = new ToTCFAnnotationLayer(null);
        instance.setGivenAnnotations(tokenAnnotations);
        instance.toToken();
        instance.setGivenAnnotations(senetenceAnnotations);
        instance.toSentences();
        assertEquals("[t_0, t_1, t_2, t_3, t_4, t_5]", instance.getTextCorpusStored().getSentencesLayer().getSentence(0).toString());
    }

    /**
     * Test of toNameEntity method, of class ToTCFAnnotationLayer.
     *
     */
    @Ignore
    public void testToNameEntity() throws Exception {
        System.out.println("toNameEntity");
        ToTCFAnnotationLayer instance = new ToTCFAnnotationLayer(null);
        List<AnnotationInterpreter> nameEntityAnnotations = new NameEntityInputCreation().getNameEntityAnnotations();
        instance.setGivenAnnotations(nameEntityAnnotations);
        instance.toNameEntity();
        assertEquals("null [t_2, t_1]", instance.getTextCorpusStored().getNamedEntitiesLayer().getEntity(0).toString());
    }

    /**
     * Test of toConstituentParser method, of class ToTCFAnnotationLayer.
     *
     */
    @Test
    public void testToConstituentParser() throws Exception {
        System.out.println("toConstituentParser");
        ToTCFAnnotationLayer instance = new ToTCFAnnotationLayer(null);
        List<AnnotationInterpreter> constituentParseAnnotations = new ConstParseInputCreation().getConstitunetParseAnnotations();
        instance.setGivenAnnotations(constituentParseAnnotations);
        instance.toConstituentParser();
        String expectedParseResult = "c_17 -> ROOT ( c_16 -> S ( c_15 -> NP ( c_14 -> NNP ( c_13 -> NNP [t_0] ) ) c_12 -> VP ( c_11 -> VBD ( c_10 -> VBD [t_1] ) c_9 -> PP ( c_8 -> TO ( c_7 -> TO [t_2] ) c_6 -> NP ( c_5 -> NNP ( c_4 -> NNP [t_3] ) c_3 -> NNP ( c_2 -> NNP [t_4] ) ) ) ) c_1 -> . ( c_0 -> . [t_5] ) ) )";
        assertEquals(expectedParseResult, instance.getTextCorpusStored().getConstituentParsingLayer().getParseRoot(0).toString());
    }

    /**
     * Test of toDependencyParser method, of class ToTCFAnnotationLayer.
     *
     */
    @Test
    public void testToDependencyParser() throws Exception {
        System.out.println("toDependencyParser");
        ToTCFAnnotationLayer instance = new ToTCFAnnotationLayer(null);
        List<AnnotationInterpreter> dependencyParseAnnotations = new DependencyParseInputCreation().getDependencyParseAnnotations();
        instance.setGivenAnnotations(dependencyParseAnnotations);
        instance.toDependencyParser();
        String expectedParseResult = "[nn [t_3] <- [t_4], pobj [t_4] <- [t_2], nsubj [t_0] <- [t_1], prep [t_2] <- [t_1]]";
        assertEquals(expectedParseResult, instance.getTextCorpusStored().getDependencyParsingLayer().getParse(0).toString());
    }
    
    /**
     * Test of toTextSource method, of class ToTCFAnnotationLayer.
     *
     *
     */
    @Test
    public void testToTextSource() throws Exception {
        System.out.println("toTextSource");
        String fileString = "";
        ToTCFAnnotationLayer instance = new ToTCFAnnotationLayer(null);
        instance.toTextSource(fileString);
    }

    /**
     * Test of inputformat method, of class ToTCFAnnotationLayer.
     *
     */
    @Ignore
    public void testInputformat() {
        System.out.println("inputformat");
        InputStream is = null;
        ToTCFAnnotationLayer instance = null;
        instance.inputDataProcessing(is);
        fail("The test case is a prototype.");
    }

    /**
     * Test of process method, of class ToTCFAnnotationLayer.
     *
     */
    @Ignore
    public void testProcess() {
        System.out.println("process");
        OutputStream os = null;
        ToTCFAnnotationLayer instance = null;
        instance.process(os);
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValid method, of class ToTCFAnnotationLayer.
     *
     */
    @Ignore
    public void testIsValid() {
        System.out.println("isValid");
        ToTCFAnnotationLayer instance = null;
        boolean expResult = false;
        boolean result
                = instance.isValid();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTextCorpusStored method, of class ToTCFAnnotationLayer.
     *
     */
    @Ignore
    public void testGetTextCorpusStored() {
        System.out.println("getTextCorpusStored");
        ToTCFAnnotationLayer instance = null;
        TextCorpusStored expResult = null;
        TextCorpusStored result
                = instance.getTextCorpusStored();
        assertEquals(expResult, result); // TODO
        fail("The test case is a prototype.");
    }

}
