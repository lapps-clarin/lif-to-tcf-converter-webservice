/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.lapps.core.layer.api.AnnotationLayerFinder;
import de.tuebingen.uni.sfs.lapps.utils.AnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.core.layer.impl.LifAnnotationLayerFinderStored;
import de.tuebingen.uni.sfs.lapps.exceptions.JsonValidityException;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.ConverterTool;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.VocabularyMappingException;
import de.tuebingen.uni.sfs.lapps.profile.api.LifProfile;
import de.tuebingen.uni.sfs.lapps.profile.impl.LifProfilerImpl;
import de.tuebingen.uni.sfs.lapps.utils.LifFileProcess;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusStored;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.apache.commons.io.FileUtils;
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
public class ConvertToTCFEachLayerTest {

    private String CORFERENCE_EXAMPLE = "lif-corferenceLayer.json";
    private String CONTSTITUENT_EXAMPLE = "lif-constituentLayer.json";
    private String DEPENDENCY_EXAMPLE = "lif-dependencyLayer.json";
    private String MULTILAYER_EXAMPLE = "lif-multipleLayer.json";
    private String NAMEENTITY_EXAMPLE = "lif-nameEntittyLayer.json";
    private String SENTENCE_EXAMPLE = "lif-sentenceLayer.json";
    private String POS_EXAMPLE = "lif-posLayer.json";
    private String TEXT_EXAMPLE = "lif-textLayer.json";
    private String TOKEN_EXAMPLE = "lif-tokenLayer.json";
    private String FILE_LIF = "json";
    private ClassLoader classLoader = getClass().getClassLoader();
    private File inputFile;
    private InputStream targetStream;
    private LifProfile givenLifFormat;
    private ConvertToTCFAnnotations instance;

    /**
     * Test of toText method, of class ConvertToTCFAnnotations.
     */
    @Test
    public void testTextLayer() throws Exception {
        inputFile = new File(classLoader.getResource(TEXT_EXAMPLE).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfilerImpl(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertTrue("Text Layer exists in TCF file", instance.getTextCorpusStored().getTextLayer() != null);
        assertEquals("text : Karen flew to New York.", instance.getTextCorpusStored().getTextLayer().toString());
    }

    /**
     * Test of toToken method, of class ConvertToTCFAnnotations.
     */
    @Test
    public void testTokenLayer() throws Exception {

        inputFile = new File(classLoader.getResource(TOKEN_EXAMPLE).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfilerImpl(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertTrue("Token Layer exists in TCF file", instance.getTextCorpusStored().getTextLayer() != null);
        assertEquals("0: t_0 -> Karen", instance.getTextCorpusStored().getTokensLayer().getToken(0).toString());
    }

    /**
     * Test of toPos method, of class ConvertToTCFAnnotations.
     */
    @Test
    public void testPosLayer() throws Exception {

        inputFile = new File(classLoader.getResource(POS_EXAMPLE).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfilerImpl(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertTrue("PosTag Layer exists in TCF file", instance.getTextCorpusStored().getPosTagsLayer() != null);
        assertEquals("NNP [t_0]", instance.getTextCorpusStored().getPosTagsLayer().getTag(0).toString());
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
        inputFile = new File(classLoader.getResource(SENTENCE_EXAMPLE).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfilerImpl(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertTrue("Sentence Layer exists in TCF file", instance.getTextCorpusStored().getSentencesLayer() != null);
        assertEquals("[t_0]", instance.getTextCorpusStored().getSentencesLayer().getSentence(0).toString());
    }

    /**
     * Test of toNameEntity method, of class ConvertToTCFAnnotations.
     */
    @Test
    public void testNamedEntirtyLayer() throws Exception {
        inputFile = new File(classLoader.getResource(NAMEENTITY_EXAMPLE).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfilerImpl(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertTrue("NameEntitty Layer exists in TCF file", instance.getTextCorpusStored().getNamedEntitiesLayer() != null);
        assertEquals("LOCATION [t_3]", instance.getTextCorpusStored().getNamedEntitiesLayer().getEntity(0).toString());
    }

    /**
     * Test of toConstituentParser method, of class ConvertToTCFAnnotations.
     */
    @Test
    public void testConstituentLayer() throws Exception {
        inputFile = new File(classLoader.getResource(CONTSTITUENT_EXAMPLE).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfilerImpl(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertTrue("Constituent Parser Layer exists in TCF file", instance.getTextCorpusStored().getConstituentParsingLayer() != null);
        assertEquals("c_17 -> ROOT ( c_16 -> S ( c_15 -> NP ( c_14 -> NNP ( c_13 -> NNP [t_0] ) ) c_12 -> VP ( c_11 -> VBD ( c_10 -> VBD [t_1] ) c_9 -> PP ( c_8 -> TO ( c_7 -> TO [t_2] ) c_6 -> NP ( c_5 -> NNP ( c_4 -> NNP [t_3] ) c_3 -> NNP ( c_2 -> NNP [t_4] ) ) ) ) c_1 -> . ( c_0 -> . [t_5] ) ) )", instance.getTextCorpusStored().getConstituentParsingLayer().getParse(0).getRoot().toString());

    }

    /**
     * Test of toDependencyParser method, of class ConvertToTCFAnnotations.
     */
    @Test
    public void testDependencyLayer() throws Exception {
        inputFile = new File(classLoader.getResource(DEPENDENCY_EXAMPLE).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfilerImpl(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertTrue("Dependency Parser Layer exists in TCF file", instance.getTextCorpusStored().getDependencyParsingLayer() != null);
        assertEquals("[nn [t_3] <- [t_4], pobj [t_4] <- [t_2], nsubj [t_0] <- [t_1], prep [t_2] <- [t_1]]", instance.getTextCorpusStored().getDependencyParsingLayer().getParse(0).toString());
    }

    /**
     * Test of toCoreferenceResolver method, of class ConvertToTCFAnnotations.
     */
    @Test
    public void testCorferenceLayer() throws Exception {
        inputFile = new File(classLoader.getResource(CORFERENCE_EXAMPLE).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfilerImpl(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertTrue("References Layer exists in TCF file", instance.getTextCorpusStored().getReferencesLayer() != null);
        assertEquals("[rc_0  [t_6], rc_1 anaphoric ->[rc_0] [t_0]]", instance.getTextCorpusStored().getReferencesLayer().getReferencedEntity(0).toString());
    }
}
