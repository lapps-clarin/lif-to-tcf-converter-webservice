/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.lapps.core.layer.impl.LifAnnotationLayerFinderStored;
import de.tuebingen.uni.sfs.lapps.exceptions.JsonValidityException;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.ConverterTool;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.VocabularyMappingException;
import de.tuebingen.uni.sfs.lapps.profile.api.LifProfile;
import de.tuebingen.uni.sfs.lapps.profile.impl.LifProfilerImpl;
import de.tuebingen.uni.sfs.lapps.utils.LifFileProcess;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author felahi
 */
public class ConvertToTCFAllLayersTest {

    private String ALL_EXAMPLE = "karen-all.json";
    private String FILE_LIF = "json";
    private ClassLoader classLoader = getClass().getClassLoader();
    private File inputFile;
    private InputStream targetStream;
    private LifProfile givenLifFormat;
    private ConvertToTCFAnnotations resultingTcfFormat;

    @Before
    public void setUp() throws IOException, LifException, JsonValidityException, ConversionException, VocabularyMappingException, Exception {
        inputFile = new File(classLoader.getResource(ALL_EXAMPLE).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfilerImpl(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        resultingTcfFormat = Convertertool.getConvertedDataModel();
    }

    /**
     * Test of all layers conversion.
     */
    @Test
    public void testAllLayer() {

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());

        assertTrue("Text Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getTextLayer() != null);
        assertEquals("text : Karen flies to New York and she is happy about that.", resultingTcfFormat.getTextCorpusStored().getTextLayer().toString());

        assertTrue("Token Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getTextLayer() != null);
        assertEquals("0: t_0 -> Karen", resultingTcfFormat.getTextCorpusStored().getTokensLayer().getToken(0).toString());

        assertTrue("PosTag Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getPosTagsLayer() != null);
        assertEquals("NNP [t_0]", resultingTcfFormat.getTextCorpusStored().getPosTagsLayer().getTag(0).toString());

        //lemma layer does not exists in this file
        assertFalse("Lemma Layer does not exists in TCF file", resultingTcfFormat.getTextCorpusStored().getLemmasLayer() != null);

        assertTrue("Sentence Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getSentencesLayer() != null);
        assertEquals("[t_0, t_1, t_2, t_3, t_4, t_5, t_6, t_7, t_8, t_9, t_10, t_11]", resultingTcfFormat.getTextCorpusStored().getSentencesLayer().getSentence(0).toString());

        assertTrue("NameEntitty Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getNamedEntitiesLayer() != null);
        assertEquals("LOCATION [t_3]", resultingTcfFormat.getTextCorpusStored().getNamedEntitiesLayer().getEntity(0).toString());

        assertTrue("Constituent Parser Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getConstituentParsingLayer() != null);
        assertEquals("c_36 -> ROOT ( c_35 -> S ( c_34 -> S ( c_33 -> NP ( c_32 -> NNP ( c_31 -> NNP [t_0] ) ) c_30 -> VP ( c_29 -> VBZ ( c_28 -> VBZ [t_1] ) c_27 -> PP ( c_26 -> TO ( c_25 -> TO [t_2] ) c_24 -> NP ( c_23 -> NNP ( c_22 -> NNP [t_3] ) c_21 -> NNP ( c_20 -> NNP [t_4] ) ) ) ) ) c_19 -> CC ( c_18 -> CC [t_5] ) c_17 -> S ( c_16 -> NP ( c_15 -> PRP ( c_14 -> PRP [t_6] ) ) c_13 -> VP ( c_12 -> VBZ ( c_11 -> VBZ [t_7] ) c_10 -> ADJP ( c_9 -> JJ ( c_8 -> JJ [t_8] ) c_7 -> PP ( c_6 -> IN ( c_5 -> IN [t_9] ) c_4 -> NP ( c_3 -> DT ( c_2 -> DT [t_10] ) ) ) ) ) ) c_1 -> . ( c_0 -> . [t_11] ) ) )", resultingTcfFormat.getTextCorpusStored().getConstituentParsingLayer().getParse(0).getRoot().toString());

        assertTrue("Dependency Parser Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getDependencyParsingLayer() != null);
        assertEquals("[ROOT [t_1] <- [t_0], nn [t_3] <- [t_4], conj [t_8] <- [t_1], pobj [t_4] <- [t_2], cop [t_7] <- [t_8], pobj [t_10] <- [t_9], nsubj [t_0] <- [t_1], nsubj [t_6] <- [t_8], prep [t_9] <- [t_8], cc [t_5] <- [t_1], prep [t_2] <- [t_1]]", resultingTcfFormat.getTextCorpusStored().getDependencyParsingLayer().getParse(0).toString());

        assertTrue("References Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getReferencesLayer() != null);
        assertEquals("[rc_0  [t_6], rc_1 anaphoric ->[rc_0] [t_0]]", resultingTcfFormat.getTextCorpusStored().getReferencesLayer().getReferencedEntity(0).toString());

        assertTrue("Text source Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getTextSourceLayer() != null);

    }

}
