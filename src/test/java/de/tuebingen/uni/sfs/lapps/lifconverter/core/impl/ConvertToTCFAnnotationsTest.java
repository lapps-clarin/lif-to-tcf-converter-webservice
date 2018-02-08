/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.lapps.core.layer.api.AnnotationLayerFinder;
import de.tuebingen.uni.sfs.lapps.utils.AnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.core.layer.impl.LifAnnotationLayerFinderStored;
import de.tuebingen.uni.sfs.lapps.core.layer.impl.LifFileProcess;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusStored;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author felahi
 */
public class ConvertToTCFAnnotationsTest {
    
    private String CORFERENCE_EXAMPLE = "lif-corferenceLayer.json";
    private String CONTSTITUENT_EXAMPLE = "lif-constituentLayer.json";
    private String DEPENDENCY_EXAMPLE = "lif-dependencyLayer.json";
    private String MULTILAYER_EXAMPLE = "lif-multipleLayer.json";
    private String NAMEENTITY_EXAMPLE = "lif-nameEntittyLayer.json";
    private String SENTENCE_EXAMPLE = "lif-sentenceLayer.json";
    private String POS_EXAMPLE = "lif-posLayer.json";
    private String TEXT_EXAMPLE = "lif-textLayer.json";
    private String TOKEN_EXAMPLE = "lif-tokenLayer.json";
    private String ALL_EXAMPLE = "karen-all.json";
    
    private String FILE_LIF = "json";
    ConvertToTCFAnnotations instance;
    private ClassLoader classLoader = getClass().getClassLoader();
    
    public ConvertToTCFAnnotationsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
     * Test of toLayers method, of class ConvertToTCFAnnotations.
     */
    @Ignore
    public void testToLayers() throws Exception {
        System.out.println("toLayers");
        AnnotationLayerFinder layer = null;
        List<AnnotationInterpreter> annotationlist = null;
        ConvertToTCFAnnotations instance = null;
        instance.toLayers(layer, annotationlist);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toLayer method, of class ConvertToTCFAnnotations.
     */
    @Ignore
    public void testToLayer() throws Exception {
        System.out.println("toLayer");
        String givenLayer = "";
        ConvertToTCFAnnotations instance = null;
        instance.toLayer(givenLayer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toLanguage method, of class ConvertToTCFAnnotations.
     */
    @Ignore
    public void testToLanguage() throws Exception {
        System.out.println("toLanguage");
        String language = "";
        ConvertToTCFAnnotations instance = null;
        instance.toLanguage(language);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toText method, of class ConvertToTCFAnnotations.
     */
    @Ignore
    public void testTextLayer() throws Exception {
        File inputFile = new File(classLoader.getResource(TEXT_EXAMPLE).getFile());
        if (inputFile.getName().contains(FILE_LIF)) {
            LifAnnotationLayerFinderStored tool = LifFileProcess.fileProcessing(inputFile);
            if (tool.isTextLayer()) {
                Assert.assertEquals(tool.isTextLayer(), true);
                instance = new ConvertToTCFAnnotations(null);
                instance.toText("Karen flew to New York.");
                System.out.println("TextLayer exists:" + instance.getTextCorpusStored().getTextLayer().toString());
                assertEquals("text : Karen flew to New York.", instance.getTextCorpusStored().getTextLayer().toString());
            }
        }
    }
    
    /**
     * Test of toText method, of class ConvertToTCFAnnotations.
     */
    @Test
    public void testAllLayer() throws Exception {
        File inputFile = new File(classLoader.getResource(ALL_EXAMPLE).getFile());
        if (inputFile.getName().contains(FILE_LIF)) {
            LifAnnotationLayerFinderStored tool = LifFileProcess.fileProcessing(inputFile);
            if (tool.isTextLayer()) {
                Assert.assertEquals(tool.isTextLayer(), true);
                instance = new ConvertToTCFAnnotations(null);
                instance.toText("Karen flew to New York.");
                System.out.println("TextLayer exists:" + instance.getTextCorpusStored().getTextLayer().toString());
                assertEquals("text : Karen flew to New York.", instance.getTextCorpusStored().getTextLayer().toString());
            }
        }
    }

    /**
     * Test of toToken method, of class ConvertToTCFAnnotations.
     */
     @Ignore
    public void testTokenLayer() throws Exception {
        File inputFile = new File(classLoader.getResource(TOKEN_EXAMPLE).getFile());
        if (inputFile.getName().contains(FILE_LIF)) {
            LifAnnotationLayerFinderStored tool = LifFileProcess.fileProcessing(inputFile);
            if (tool.isTokenLayer()) {
                Assert.assertEquals(tool.isTokenLayer(), true);
                instance = new ConvertToTCFAnnotations(null);
                List<AnnotationInterpreter> tokenAnnotations = tool.getGivenDataModel().getAnnotationLayerData(0);
                instance.setGivenAnnotations(tokenAnnotations);
                instance.toToken();
                System.out.println("TokenLayer exists:" + instance.getTextCorpusStored().getTokensLayer().getToken(0).toString());
                assertEquals("0: t_0 -> Karen", instance.getTextCorpusStored().getTokensLayer().getToken(0).toString());
            }
        }

    }

    /**
     * Test of toPos method, of class ConvertToTCFAnnotations.
     */
     @Ignore
    public void testPosLayer() throws Exception {
        File inputFile = new File(classLoader.getResource(POS_EXAMPLE).getFile());
        if (inputFile.getName().contains(FILE_LIF)) {
            LifAnnotationLayerFinderStored tool = LifFileProcess.fileProcessing(inputFile);
            if (tool.isPosLayer()) {
                Assert.assertEquals(tool.isPosLayer(), true);
                instance = new ConvertToTCFAnnotations(null);
                List<AnnotationInterpreter> tokenAnnotations = tool.getGivenDataModel().getAnnotationLayerData(0);
                instance.setGivenAnnotations(tokenAnnotations);
                instance.toPos();
                System.out.println("PosLayer exists:" + instance.getTextCorpusStored().getPosTagsLayer().getTag(0).toString());
                assertEquals("NNP [t_0]", instance.getTextCorpusStored().getPosTagsLayer().getTag(0).toString());
            }
        }
    }

    /**
     * Test of toLemma method, of class ConvertToTCFAnnotations.
     */
    @Ignore
    public void testToLemma() throws Exception {
        System.out.println("toLemma");
        ConvertToTCFAnnotations instance = null;
        instance.toLemma();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toSentences method, of class ConvertToTCFAnnotations.
     */
    @Ignore
    public void testSentenceLayer() throws Exception {
        File inputFile = new File(classLoader.getResource(SENTENCE_EXAMPLE).getFile());
        if (inputFile.getName().contains(FILE_LIF)) {
            LifAnnotationLayerFinderStored tool = LifFileProcess.fileProcessing(inputFile);
            if (tool.isSenetenceLayer()) {
                Assert.assertEquals(tool.isSenetenceLayer(), true);
                instance = new ConvertToTCFAnnotations(null);
                List<AnnotationInterpreter> tokenAnnotations = tool.getGivenDataModel().getAnnotationLayerData(0);
                instance.setGivenAnnotations(tokenAnnotations);
                instance.toToken();
                instance.toSentences();
                System.out.println("SentenceLayer exists:" + instance.getTextCorpusStored().getSentencesLayer().getSentence(0));
                assertEquals("[t_0]", instance.getTextCorpusStored().getSentencesLayer().getSentence(0).toString());
            }
        }
    }

    /**
     * Test of toNameEntity method, of class ConvertToTCFAnnotations.
     */
    @Ignore
    public void testNamedEntirtyLayer() throws Exception {
        File inputFile = new File(classLoader.getResource(NAMEENTITY_EXAMPLE).getFile());
        if (inputFile.getName().contains(FILE_LIF)) {
            LifAnnotationLayerFinderStored tool = LifFileProcess.fileProcessing(inputFile);
            if (tool.isNamedEntityLayer()) {
                Assert.assertEquals(tool.isNamedEntityLayer(), true);
                instance = new ConvertToTCFAnnotations(null);
                
                List<AnnotationInterpreter> tokenAnnotations = tool.getGivenDataModel().getAnnotationLayerData(0);
                //instance.toLayers(Discriminators.Uri.NE.toString(), tokenAnnotations);
                //instance.setGivenAnnotations(tokenAnnotations);
                instance.toToken();
                instance.toNameEntity();
                System.out.println("NamedEntirtyLayer exists:" + instance.getTextCorpusStored().getNamedEntitiesLayer().getEntity(0));
                //assertEquals("[t_0]", instance.getTextCorpusStored().getSentencesLayer().getSentence(0).toString());
            }
        }
    }

    /**
     * Test of toConstituentParser method, of class ConvertToTCFAnnotations.
     */
    @Ignore
    public void testConstituentLayer() throws Exception {
        File inputFile = new File(classLoader.getResource(CONTSTITUENT_EXAMPLE).getFile());
        if (inputFile.getName().contains(FILE_LIF)) {
            LifAnnotationLayerFinderStored tool = LifFileProcess.fileProcessing(inputFile);
            if (tool.isConstituentLayer()) {
                Assert.assertEquals(tool.isConstituentLayer(), true);
                instance = new ConvertToTCFAnnotations(null);
                List<AnnotationInterpreter> tokenAnnotations = tool.getGivenDataModel().getAnnotationLayerData(0);
                instance.setGivenAnnotations(tokenAnnotations);
                instance.toConstituentParser();
                System.out.println("ConstituentLayer exists:" + instance.getTextCorpusStored().getConstituentParsingLayer().getParse(0).getRoot());
                assertEquals("c_17 -> ROOT ( c_16 -> S ( c_15 -> NP ( c_14 -> NNP ( c_13 -> NNP [t_0] ) ) c_12 -> VP ( c_11 -> VBD ( c_10 -> VBD [t_1] ) c_9 -> PP ( c_8 -> TO ( c_7 -> TO [t_2] ) c_6 -> NP ( c_5 -> NNP ( c_4 -> NNP [t_3] ) c_3 -> NNP ( c_2 -> NNP [t_4] ) ) ) ) c_1 -> . ( c_0 -> . [t_5] ) ) )", instance.getTextCorpusStored().getConstituentParsingLayer().getParse(0).getRoot().toString());

            }
        }

    }

    /**
     * Test of toDependencyParser method, of class ConvertToTCFAnnotations.
     */
   @Ignore
    public void testDependencyLayer() throws Exception {
        File inputFile = new File(classLoader.getResource(DEPENDENCY_EXAMPLE).getFile());
        if (inputFile.getName().contains(FILE_LIF)) {
            LifAnnotationLayerFinderStored tool = LifFileProcess.fileProcessing(inputFile);
            if (tool.isDependencyLayer()) {
                Assert.assertEquals(tool.isDependencyLayer(), true);
                instance = new ConvertToTCFAnnotations(null);
                List<AnnotationInterpreter> tokenAnnotations = tool.getGivenDataModel().getAnnotationLayerData(0);
                instance.setGivenAnnotations(tokenAnnotations);
                instance.toDependencyParser();
                System.out.println("DependencyLayer exists:" + instance.getTextCorpusStored().getDependencyParsingLayer().getParse(0));
                assertEquals("[nn [t_3] <- [t_4], pobj [t_4] <- [t_2], nsubj [t_0] <- [t_1], prep [t_2] <- [t_1]]", instance.getTextCorpusStored().getDependencyParsingLayer().getParse(0).toString());
            }
        }

    }

    /**
     * Test of toCoreferenceResolver method, of class ConvertToTCFAnnotations.
     */
    @Ignore
    public void testCorferenceLayer() throws Exception {
        File inputFile = new File(classLoader.getResource(CORFERENCE_EXAMPLE).getFile());
        instance = new ConvertToTCFAnnotations(null);
        if (inputFile.getName().contains(FILE_LIF)) {
            LifAnnotationLayerFinderStored tool = LifFileProcess.fileProcessing(inputFile);
            if (tool.isCorferenceLayer()) {
                Assert.assertEquals(tool.isCorferenceLayer(), true);
                List<AnnotationInterpreter> coreferenceResolverAnnotations = tool.getGivenDataModel().getAnnotationLayerData(0);
                instance.setGivenAnnotations(coreferenceResolverAnnotations);
                instance.toCoreferenceResolver();
                System.out.println("CorferenceLayer exists:" + instance.getTextCorpusStored().getReferencesLayer().getReferencedEntity(0));
                assertEquals("[rc_0  [t_6], rc_1 anaphoric ->[rc_0] [t_0]]", instance.getTextCorpusStored().getReferencesLayer().getReferencedEntity(0).toString());
            }

        }

    }

    /**
     * Test of toTextSource method, of class ConvertToTCFAnnotations.
     */
    @Ignore
    public void testToTextSource() throws Exception {
        System.out.println("toTextSource");
        String fileString = "";
        ConvertToTCFAnnotations instance = null;
        instance.toTextSource(fileString);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inputDataProcessing method, of class ConvertToTCFAnnotations.
     */
    @Ignore
    public void testInputDataProcessing() {
        System.out.println("inputDataProcessing");
        InputStream is = null;
        ConvertToTCFAnnotations instance = null;
        instance.inputDataProcessing(is);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of process method, of class ConvertToTCFAnnotations.
     */
    @Ignore
    public void testProcess() {
        System.out.println("process");
        OutputStream os = null;
        ConvertToTCFAnnotations instance = null;
        instance.process(os);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValid method, of class ConvertToTCFAnnotations.
     */
    @Ignore
    public void testIsValid() {
        System.out.println("isValid");
        ConvertToTCFAnnotations instance = null;
        boolean expResult = false;
        boolean result = instance.isValid();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTextCorpusStored method, of class ConvertToTCFAnnotations.
     */
    @Ignore
    public void testGetTextCorpusStored() {
        System.out.println("getTextCorpusStored");
        ConvertToTCFAnnotations instance = null;
        TextCorpusStored expResult = null;
        TextCorpusStored result = instance.getTextCorpusStored();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGivenAnnotations method, of class ConvertToTCFAnnotations.
     */
    @Ignore
    public void testSetGivenAnnotations() {
        System.out.println("setGivenAnnotations");
        List<AnnotationInterpreter> givenAnnotations = null;
        ConvertToTCFAnnotations instance = null;
        instance.setGivenAnnotations(givenAnnotations);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
