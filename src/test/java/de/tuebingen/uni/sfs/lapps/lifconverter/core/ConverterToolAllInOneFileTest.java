/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import de.tuebingen.uni.sfs.lapps.exceptions.JsonValidityException;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.VocabularyMappingException;
import de.tuebingen.uni.sfs.lapps.core.impl.profiler.LifFormatImpl;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.api.LayerConverter;
import eu.clarin.weblicht.wlfxb.tc.api.ConstituentParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.api.DependencyParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.api.LemmasLayer;
import eu.clarin.weblicht.wlfxb.tc.api.NamedEntitiesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.PosTagsLayer;
import eu.clarin.weblicht.wlfxb.tc.api.ReferencesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.SentencesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.TextLayer;
import eu.clarin.weblicht.wlfxb.tc.api.TokensLayer;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class ConverterToolAllInOneFileTest {

    private String ALL_LAYER_EXAMPLE = "/data/all/tool/karen-all.json";
    private LayerConverter weblichtTcfProfile;

    @Before
    public void setUp() throws VocabularyMappingException, LifException, ConversionException, IOException, JsonValidityException {
        InputStream is = this.getClass().getResourceAsStream(ALL_LAYER_EXAMPLE);
        LifFormatImpl lappsLifProfile = new LifFormatImpl(is);
        ConverterTool instance = new ConverterTool();
        weblichtTcfProfile = instance.convertFormat(lappsLifProfile);
    }

    /**
     * Test of convertFormat method, of class ConverterTool.
     */
    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_TextLayerExist() throws Exception {
        System.out.println("testConvertFormatAllLayersInOneFile_TextLayer");
        TextLayer result = weblichtTcfProfile.getTextCorpusStored().getTextLayer();
        assertTrue("Text Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getTextLayer() != null);
        assertEquals("Karen flies to New York and she is happy about that.", result.getText());
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_toTextSourceLayerExist() {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_toTextSourceLayerExist");
        assertTrue("Text source Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getTextSourceLayer() != null);
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_TokenLayerExist() throws Exception {
        System.out.println("testConvertFormatAllLayersInOneFile_TokenLayer");
        TokensLayer result = weblichtTcfProfile.getTextCorpusStored().getTokensLayer();
        assertTrue("Token Layer exists in TCF file", result != null);
        assertEquals("Karen", result.getToken(0).getString());
        assertEquals(".", result.getToken(11).getString());
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_PosLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_PosLayerExist");
        assertTrue("PosTag Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getPosTagsLayer() != null);
        PosTagsLayer result = weblichtTcfProfile.getTextCorpusStored().getPosTagsLayer();
        assertEquals("NNP", result.getTag(0).getString());
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_LemmaLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_LemmaLayerExist");
        LemmasLayer result = weblichtTcfProfile.getTextCorpusStored().getLemmasLayer();
        assertFalse("Lemma Layer exists in TCF file", result != null);
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_SentenceLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_LemmaLayerExist");
        SentencesLayer result = weblichtTcfProfile.getTextCorpusStored().getSentencesLayer();
        assertTrue("Sentence Layer exists in TCF file", result != null);
        assertEquals("[t_0, t_1, t_2, t_3, t_4, t_5, t_6, t_7, t_8, t_9, t_10, t_11]", result.getSentence(0).toString());
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_NamedEntityLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_NamedEntityLayerExist");
        NamedEntitiesLayer result = weblichtTcfProfile.getTextCorpusStored().getNamedEntitiesLayer();
        assertTrue("NameEntitty Layer exists in TCF file", result != null);
        assertEquals("LOCATION [t_3]", result.getEntity(0).toString());
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_ConstituentParseLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_ConstituentParseLayerExist");
        ConstituentParsingLayer result = weblichtTcfProfile.getTextCorpusStored().getConstituentParsingLayer();
        assertTrue("Constituent Parser Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getConstituentParsingLayer() != null);
        assertEquals("c_24 -> ROOT ( c_23 -> S ( c_22 -> S ( c_21 -> NP ( c_20 -> NNP [t_0] ) c_19 -> VP ( c_18 -> VBZ [t_1] c_17 -> PP ( c_16 -> TO [t_2] c_15 -> NP ( c_14 -> NNP [t_3] c_13 -> NNP [t_4] ) ) ) ) c_12 -> CC [t_5] c_11 -> S ( c_10 -> NP ( c_9 -> PRP [t_6] ) c_8 -> VP ( c_7 -> VBZ [t_7] c_6 -> ADJP ( c_5 -> JJ [t_8] c_4 -> PP ( c_3 -> IN [t_9] c_2 -> NP ( c_1 -> DT [t_10] ) ) ) ) ) c_0 -> . [t_11] ) )", result.getParse(0).toString());
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_DependencyParseLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_DependencyParseLayerExist");
        DependencyParsingLayer result = weblichtTcfProfile.getTextCorpusStored().getDependencyParsingLayer();
        assertTrue("Dependency Parser Layer exists in TCF file", result != null);
        assertEquals("[ROOT [t_1] <- [ ], nn [t_3] <- [t_4], conj [t_8] <- [t_1], pobj [t_4] <- [t_2], cop [t_7] <- [t_8], pobj [t_10] <- [t_9], nsubj [t_0] <- [t_1], nsubj [t_6] <- [t_8], prep [t_9] <- [t_8], cc [t_5] <- [t_1], prep [t_2] <- [t_1]]", result.getParse(0).toString());
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_toCoreferenceResolverLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_toCoreferenceResolverLayerExist");
        ReferencesLayer result = weblichtTcfProfile.getTextCorpusStored().getReferencesLayer();
        assertTrue("References Layer exists in TCF file", result != null);
        assertEquals("[rc_0  [t_6], rc_1 anaphoric ->[rc_0] [t_0]]", result.getReferencedEntity(0).toString());
    }

}
