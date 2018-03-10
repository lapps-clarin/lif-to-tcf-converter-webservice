/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.lapps.lifconverter.core.ConverterTool;
import de.tuebingen.uni.sfs.lapps.profile.api.LifProfile;
import de.tuebingen.uni.sfs.lapps.profile.impl.LifProfiler;
import java.io.File;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.lappsgrid.discriminator.Discriminators;

/**
 *
 * @author felahi
 */
public class ConverToTCFAfterConversionTest {

    private String POS_AFTER_TCF_TO_LIF = "other/afterConverter/lif-posLayer-after-converter.json";
    private String TOKEN_AFTER_TCF_TO_LIF = "other/afterConverter/lif-tokSenLayer-after-converter.json";
    private String CONSTITUENT_PARSER_CON = "other/afterConverter/lif-contsLayer-after-converter.json";
    private String CONSTITUENT_PARSER_WITHOUT_SENTENCE = "other/variations/lif-constituentLayer-without-sentence.json";
    private String DEPENDENCY_PARSER_WITHOUT_SENTENCE = "other/variations/lif-dependencyLayer-without-sentence.json";
    private String DEPENDENCY_PARSER_CON = "other/afterConverter/lif-depLayer-after-converter.json";
    private String NAMEDENTITY_OLD_ANNOTATION = "other/lif-named-entities-old-annotation.json";
    private String CORFERENCE_WITHOUT_REFERENCE= "other/refLayer-without-ref-wrong.json";
    private String FILE_LIF = Discriminators.Alias.JSON;
    private ClassLoader classLoader = getClass().getClassLoader();
    private File inputFile;
    private InputStream targetStream;
    private LifProfile givenLifFormat;
    private ConvertToTCFAnnotations instance;

    /**
     * Test of toToken layer, after TCF to LIF converter
     */
    @Test
    public void testTokenLayerTCFtoLifConverter() throws Exception {

        inputFile = new File(classLoader.getResource(TOKEN_AFTER_TCF_TO_LIF).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfiler(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertTrue("Token Layer exists in TCF file", instance.getTextCorpusStored().getTextLayer() != null);
        assertEquals("0: t_0 -> Karen", instance.getTextCorpusStored().getTokensLayer().getToken(0).toString());
        assertTrue("Sentence Layer exists in TCF file", instance.getTextCorpusStored().getSentencesLayer() != null);
    }

    /**
     * Test of toPos layer, after TCF to LIF converter
     */
    @Test
    public void testPosLayerTCFtoLifConverter() throws Exception {

        inputFile = new File(classLoader.getResource(POS_AFTER_TCF_TO_LIF).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfiler(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertTrue("PosTag Layer exists in TCF file", instance.getTextCorpusStored().getPosTagsLayer() != null);
        assertEquals("NNP [t_0]", instance.getTextCorpusStored().getPosTagsLayer().getTag(0).toString());
    }

    /**
     * Test of dependency layer, after TCF to LIF converter
     */
    @Test
    public void testDependencyLayerTCFtoLifConverter() throws Exception {

        inputFile = new File(classLoader.getResource(DEPENDENCY_PARSER_CON).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfiler(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertTrue("Dependency Parser Layer exists in TCF file", instance.getTextCorpusStored().getDependencyParsingLayer() != null);
        assertEquals("nn [t_3] <- [t_4]", instance.getTextCorpusStored().getDependencyParsingLayer().getParse(0).getDependencies()[2].toString());
    }

    /**
     * Test of constituent layer, after TCF to LIF converter
     */
    @Test
    public void testConstituentLayerTCFtoLifConverter() throws Exception {

        inputFile = new File(classLoader.getResource(CONSTITUENT_PARSER_CON).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfiler(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertTrue("Token Layer exists in TCF file", instance.getTextCorpusStored().getTextLayer() != null);
        assertEquals("0: t_0 -> Karen", instance.getTextCorpusStored().getTokensLayer().getToken(0).toString());
        assertTrue("Constituent Parser Layer exists in TCF file", instance.getTextCorpusStored().getConstituentParsingLayer() != null);
        assertEquals("c_20 -> ROOT ( c_19 -> S ( c_18 -> NP ( c_17 -> NNP [t_0] ) c_16 -> VP ( c_15 -> VBD [t_1] c_14 -> PP ( c_13 -> TO [t_2] c_12 -> NP ( c_11 -> NNP [t_3] c_10 -> NNP [t_4] ) ) c_9 -> SBAR ( c_8 -> IN [t_5] c_7 -> S ( c_6 -> NP ( c_5 -> PRP [t_6] ) c_4 -> VP ( c_3 -> VBZ [t_7] c_2 -> NP ( c_1 -> PRP [t_8] ) ) ) ) ) c_0 -> . [t_9] ) )", instance.getTextCorpusStored().getConstituentParsingLayer().getParse(0).getRoot().toString());
    }
    
    @Test
    public void testConstituentLayerWithOutSentence() throws Exception {
        inputFile = new File(classLoader.getResource(CONSTITUENT_PARSER_WITHOUT_SENTENCE).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfiler(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertFalse("Sentence Layer does not exist in TCF file", instance.getTextCorpusStored().getSentencesLayer() != null);
        assertTrue("Constituent Parser Layer exists in TCF file", instance.getTextCorpusStored().getConstituentParsingLayer() != null);
        System.out.println(instance.getTextCorpusStored().getConstituentParsingLayer().getParse(0).getRoot().toString());
        assertEquals("c_11 -> ROOT ( c_10 -> S ( c_9 -> NP ( c_8 -> NNP [t_0] ) c_7 -> VP ( c_6 -> VBD [t_1] c_5 -> PP ( c_4 -> TO [t_2] c_3 -> NP ( c_2 -> NNP [t_3] c_1 -> NNP [t_4] ) ) ) c_0 -> . [t_5] ) )", instance.getTextCorpusStored().getConstituentParsingLayer().getParse(0).getRoot().toString());
    }
    
    /**
     * Test of toDependencyParser method, of class ConvertToTCFAnnotations.
     */
    @Test
    public void testDependencyLayerWithOutSentence() throws Exception {
        inputFile = new File(classLoader.getResource(DEPENDENCY_PARSER_WITHOUT_SENTENCE).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfiler(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertFalse("Sentence Layer does not exist in TCF file", instance.getTextCorpusStored().getSentencesLayer() != null);
        assertTrue("Dependency Parser Layer exists in TCF file", instance.getTextCorpusStored().getDependencyParsingLayer() != null);
        assertEquals("[nn [t_3] <- [t_4], pobj [t_4] <- [t_2], nsubj [t_0] <- [t_1], prep [t_2] <- [t_1]]", instance.getTextCorpusStored().getDependencyParsingLayer().getParse(0).toString());
    }


    /**
     * Test of named entity layer, old annotation
     */
    @Test
    public void testNamedEntityOldAnnotation() throws Exception {

        inputFile = new File(classLoader.getResource(NAMEDENTITY_OLD_ANNOTATION).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfiler(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertTrue("Token Layer exists in TCF file", instance.getTextCorpusStored().getTextLayer() != null);
        assertEquals("0: t_0 -> Karen", instance.getTextCorpusStored().getTokensLayer().getToken(0).toString());
        assertTrue("NameEntitty Layer exists in TCF file", instance.getTextCorpusStored().getNamedEntitiesLayer() != null);
        assertEquals("Person [t_0]", instance.getTextCorpusStored().getNamedEntitiesLayer().getEntity(0).toString());
    }
    
    @Test
    public void testCorferenceLayerWithOutReference() throws Exception {
        inputFile = new File(classLoader.getResource(CORFERENCE_WITHOUT_REFERENCE).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfiler(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertFalse("References Layer not exists in TCF file", instance.getTextCorpusStored().getReferencesLayer() != null);
    }

}
