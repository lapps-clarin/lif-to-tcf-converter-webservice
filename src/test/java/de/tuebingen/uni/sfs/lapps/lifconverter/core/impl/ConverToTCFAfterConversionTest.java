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
    private String DEPENDENCY_PARSER_CON = "other/afterConverter/lif-depLayer-after-converter.json";
    private String NAMEDENTITY_OLD_ANNOTATION = "other/lif-named-entities-old-annotation.json";
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
        assertEquals("c_30 -> ROOT ( c_29 -> S ( c_28 -> NP ( c_27 -> NNP ( c_26 -> NNP [t_0] ) ) c_25 -> VP ( c_24 -> VBD ( c_23 -> VBD [t_1] ) c_22 -> PP ( c_21 -> TO ( c_20 -> TO [t_2] ) c_19 -> NP ( c_18 -> NNP ( c_17 -> NNP [t_3] ) c_16 -> NNP ( c_15 -> NNP [t_4] ) ) ) c_14 -> SBAR ( c_13 -> IN ( c_12 -> IN [t_5] ) c_11 -> S ( c_10 -> NP ( c_9 -> PRP ( c_8 -> PRP [t_6] ) ) c_7 -> VP ( c_6 -> VBZ ( c_5 -> VBZ [t_7] ) c_4 -> NP ( c_3 -> PRP ( c_2 -> PRP [t_8] ) ) ) ) ) ) c_1 -> . ( c_0 -> . [t_9] ) ) )", instance.getTextCorpusStored().getConstituentParsingLayer().getParse(0).getRoot().toString());
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

}
