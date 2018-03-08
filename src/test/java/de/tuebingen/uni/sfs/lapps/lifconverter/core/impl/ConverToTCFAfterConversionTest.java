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

    private String POS_EXAMPLE2 = "other/lif-posLayer-tcfToLifCon.json";
    private String TOKEN_EXAMPLE2 = "other/lif-tokSen-tcfToLifCon.json";
    private String DEPENDENCY_PARSER = "other/dependencyCon.json";
    private String NAMEDENTITY_RECOGNIZER = "other/lif-namedEntities1.json";
    private String CONSTITUENT_PARSER_CON = "other/lif-ConstLayer-tcfToLifCon.json";
    private String CONSTITUENT_PARSER = "other/lif-constituentLayerNew1.json";
    private String FILE_LIF = Discriminators.Alias.JSON;
    private ClassLoader classLoader = getClass().getClassLoader();
    private File inputFile;
    private InputStream targetStream;
    private LifProfile givenLifFormat;
    private ConvertToTCFAnnotations instance;

    /**
     * Test of toToken method, of class ConvertToTCFAnnotations.
     */
    @Test
    public void testTokenLayerTCFtoLifConverter() throws Exception {

        inputFile = new File(classLoader.getResource(TOKEN_EXAMPLE2).getFile());
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
        System.out.println(instance.getTextCorpusStored().getSentencesLayer().getSentence(0).toString());
    }

    /**
     * Test of toPos method, of class ConvertToTCFAnnotations.
     */
    @Test
    public void testPosLayerTCFtoLifConverter() throws Exception {

        inputFile = new File(classLoader.getResource(POS_EXAMPLE2).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfiler(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertTrue("PosTag Layer exists in TCF file", instance.getTextCorpusStored().getPosTagsLayer() != null);
        assertEquals("NNP [t_0]", instance.getTextCorpusStored().getPosTagsLayer().getTag(0).toString());
        //assertEquals("[t_0, t_1, t_2, t_3, t_4, t_5]", instance.getTextCorpusStored().getSentencesLayer().getSentence(0).toString());
    }

    /**
     * Test of toPos method, of class ConvertToTCFAnnotations.
     */
    @Ignore
    public void testDependencyParser() throws Exception {

        inputFile = new File(classLoader.getResource(DEPENDENCY_PARSER).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfiler(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertTrue("PosTag Layer exists in TCF file", instance.getTextCorpusStored().getPosTagsLayer() != null);
        assertEquals("NNP [t_0]", instance.getTextCorpusStored().getPosTagsLayer().getTag(0).toString());
        //assertEquals("[t_0, t_1, t_2, t_3, t_4, t_5]", instance.getTextCorpusStored().getSentencesLayer().getSentence(0).toString());
    }

    /**
     * Test of toToken method, of class ConvertToTCFAnnotations.
     */
    @Ignore
    public void testConstituentLayerAfterTCFtoLIFConversion() throws Exception {

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
        //assertEquals("c_17 -> ROOT ( c_16 -> S ( c_15 -> NP ( c_14 -> NNP ( c_13 -> NNP [t_0] ) ) c_12 -> VP ( c_11 -> VBD ( c_10 -> VBD [t_1] ) c_9 -> PP ( c_8 -> TO ( c_7 -> TO [t_2] ) c_6 -> NP ( c_5 -> NNP ( c_4 -> NNP [t_3] ) c_3 -> NNP ( c_2 -> NNP [t_4] ) ) ) ) c_1 -> . ( c_0 -> . [t_5] ) ) )", instance.getTextCorpusStored().getConstituentParsingLayer().getParse(0).getRoot().toString());
    }

    /**
     * Test of toToken method, of class ConvertToTCFAnnotations.
     */
    @Test
    public void testNamedEntityOldAnnotation() throws Exception {

        inputFile = new File(classLoader.getResource(NAMEDENTITY_RECOGNIZER).getFile());
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

}
