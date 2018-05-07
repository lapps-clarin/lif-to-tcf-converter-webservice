/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.lapps.exceptions.JsonValidityException;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.ConverterTool;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.VocabularyMappingException;
import de.tuebingen.uni.sfs.lapps.profile.api.LifProfile;
import de.tuebingen.uni.sfs.lapps.profile.impl.LifProfiler;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.lappsgrid.discriminator.Discriminators;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class ConvertToTCFAllLayersTest {

    private String INPUT = "karen-all.json";
    private ConvertToTCFAnnotations resultingTcfFormat;

    @Before
    public void setUp() throws IOException, LifException, JsonValidityException, ConversionException, VocabularyMappingException, Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(classLoader.getResource(INPUT).getFile());
        InputStream targetStream = FileUtils.openInputStream(inputFile);
        LifProfile givenLifFormat = new LifProfiler(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        resultingTcfFormat = Convertertool.getWeblichtTcfProfile();
    }

    @Test
    public void testToText() throws Exception {
        System.out.println("testToText");
        assertTrue("Text Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getTextLayer() != null);
        assertEquals("text : Karen flies to New York and she is happy about that.", resultingTcfFormat.getTextCorpusStored().getTextLayer().toString());

    }

    @Test
    public void testToToken() throws Exception {
        System.out.println("testToToken");
        assertTrue("Token Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getTextLayer() != null);
        assertEquals("0: t_0 -> Karen", resultingTcfFormat.getTextCorpusStored().getTokensLayer().getToken(0).toString());
    }

    @Test
    public void testToPos() throws Exception {
        System.out.println("toPos");
        assertTrue("PosTag Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getPosTagsLayer() != null);
        assertEquals("NNP [t_0]", resultingTcfFormat.getTextCorpusStored().getPosTagsLayer().getTag(0).toString());

    }

    //This file does not contain a lemma layer. 
    //TO do create another file that takes lemma layer
    @Test
    public void testToLemma() throws Exception {
        System.out.println("toLemma");
        System.out.println("toPos");
        assertFalse("Lemma Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getLemmasLayer() != null);
    }

    @Test
    public void testToSentences() throws Exception {
        System.out.println("toSentences");
        assertTrue("Sentence Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getSentencesLayer() != null);
        assertEquals("[t_0, t_1, t_2, t_3, t_4, t_5, t_6, t_7, t_8, t_9, t_10, t_11]", resultingTcfFormat.getTextCorpusStored().getSentencesLayer().getSentence(0).toString());
    }

    @Test
    public void testToNameEntity() throws Exception {
        System.out.println("toNameEntity");
        assertTrue("NameEntitty Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getNamedEntitiesLayer() != null);
        assertEquals("LOCATION [t_3]", resultingTcfFormat.getTextCorpusStored().getNamedEntitiesLayer().getEntity(0).toString());
    }

    @Test
    public void testToConstituentParser() throws Exception {
        System.out.println("toConstituentParser");
        assertTrue("Constituent Parser Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getConstituentParsingLayer() != null);
        assertEquals("c_24 -> ROOT ( c_23 -> S ( c_22 -> S ( c_21 -> NP ( c_20 -> NNP [t_0] ) c_19 -> VP ( c_18 -> VBZ [t_1] c_17 -> PP ( c_16 -> TO [t_2] c_15 -> NP ( c_14 -> NNP [t_3] c_13 -> NNP [t_4] ) ) ) ) c_12 -> CC [t_5] c_11 -> S ( c_10 -> NP ( c_9 -> PRP [t_6] ) c_8 -> VP ( c_7 -> VBZ [t_7] c_6 -> ADJP ( c_5 -> JJ [t_8] c_4 -> PP ( c_3 -> IN [t_9] c_2 -> NP ( c_1 -> DT [t_10] ) ) ) ) ) c_0 -> . [t_11] ) )", resultingTcfFormat.getTextCorpusStored().getConstituentParsingLayer().getParse(0).getRoot().toString());
    }

    @Test
    public void testToDependencyParser() throws Exception {
        System.out.println("toDependencyParser");
        assertTrue("Dependency Parser Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getDependencyParsingLayer() != null);
        assertEquals("[ROOT [t_1] <- [ ], nn [t_3] <- [t_4], conj [t_8] <- [t_1], pobj [t_4] <- [t_2], cop [t_7] <- [t_8], pobj [t_10] <- [t_9], nsubj [t_0] <- [t_1], nsubj [t_6] <- [t_8], prep [t_9] <- [t_8], cc [t_5] <- [t_1], prep [t_2] <- [t_1]]", resultingTcfFormat.getTextCorpusStored().getDependencyParsingLayer().getParse(0).toString());
    }

    @Test
    public void testToCoreferenceResolver() throws Exception {
        System.out.println("toCoreferenceResolver");
        assertTrue("References Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getReferencesLayer() != null);
        assertEquals("[rc_0  [t_6], rc_1 anaphoric ->[rc_0] [t_0]]", resultingTcfFormat.getTextCorpusStored().getReferencesLayer().getReferencedEntity(0).toString());
    }

    @Test
    public void testToTextSource() {
        System.out.println("toTextSource");
        assertTrue("Text source Layer exists in TCF file", resultingTcfFormat.getTextCorpusStored().getTextSourceLayer() != null);
    }

}
