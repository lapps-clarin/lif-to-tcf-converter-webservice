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
public class ConverterToolAllInOneViewTest {

    private String ALL_LAYER_ONE_VIEW_EXAMPLE = "/data/all/gold/lif-all-1_1_0.json";
    private LayerConverter weblichtTcfProfile;

    @Before
    public void setUp() throws VocabularyMappingException, LifException, ConversionException, IOException, JsonValidityException {
        InputStream is = this.getClass().getResourceAsStream(ALL_LAYER_ONE_VIEW_EXAMPLE);
        LifFormatImpl lappsLifProfile = new LifFormatImpl(is);
        ConverterTool instance = new ConverterTool();
        weblichtTcfProfile = instance.convertFormat(lappsLifProfile);
    }

    /**
     * Test of convertFormat method, of class ConverterTool.
     */
    @Test
    public void testConvertFormat_WhenAllLayersInOneView_TextLayerExist() throws Exception {
        System.out.println("testConvertFormatAllLayersInOneView_TextLayer");
        TextLayer result = weblichtTcfProfile.getTextCorpusStored().getTextLayer();
        assertTrue("Text Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getTextLayer() != null);
        assertEquals("Karen flew to New York. She went to see her cousin. ", result.getText());
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneView_toTextSourceLayerExist() {
        System.out.println("testConvertFormat_WhenAllLayersInOneView_toTextSourceLayerExist");
        assertTrue("Text source Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getTextSourceLayer() != null);
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneView_TokenLayerExist() throws Exception {
        System.out.println("testConvertFormatAllLayersInOneView_TokenLayer");
        TokensLayer result = weblichtTcfProfile.getTextCorpusStored().getTokensLayer();
        assertTrue("Token Layer exists in TCF file", result != null);
        assertEquals("Karen", result.getToken(0).getString());
        assertEquals("cousin", result.getToken(11).getString());
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneView_PosLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneView_PosLayerExist");
        assertTrue("PosTag Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getPosTagsLayer() != null);
        PosTagsLayer result = weblichtTcfProfile.getTextCorpusStored().getPosTagsLayer();
        assertEquals("NNP", result.getTag(0).getString());
        assertEquals("NN", result.getTag(11).getString());
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneView_LemmaLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneView_LemmaLayerExist");
        LemmasLayer result = weblichtTcfProfile.getTextCorpusStored().getLemmasLayer();
        assertTrue("Lemma Layer exists in TCF file", result != null);
        assertEquals("Karen", result.getLemma(0).getString());
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneView_SentenceLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneView_LemmaLayerExist");
        SentencesLayer result = weblichtTcfProfile.getTextCorpusStored().getSentencesLayer();
        assertTrue("Sentence Layer exists in TCF file", result != null);
        assertEquals("[t_0, t_1, t_2, t_3, t_4, t_5]", result.getSentence(0).toString());
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneView_NamedEntityLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneView_NamedEntityLayerExist");
        NamedEntitiesLayer result = weblichtTcfProfile.getTextCorpusStored().getNamedEntitiesLayer();
        assertTrue("NameEntitty Layer exists in TCF file", result != null);
        assertEquals("PERSON [t_0]", result.getEntity(0).toString());

    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneView_ConstituentParseLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneView_ConstituentParseLayerExist");
        ConstituentParsingLayer result = weblichtTcfProfile.getTextCorpusStored().getConstituentParsingLayer();
        assertTrue("Constituent Parser Layer exists in TCF file", result != null);
        assertEquals("c_11 -> ROOT ( c_10 -> S ( c_9 -> NP ( c_8 -> NNP [t_0] ) c_7 -> VP ( c_6 -> VBD [t_1] c_5 -> PP ( c_4 -> TO [t_2] c_3 -> NP ( c_2 -> NNP [t_3] c_1 -> NNP [t_4] ) ) ) c_0 -> . [t_5] ) )", result.getParse(0).toString());
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneView_DependencyParseLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneView_DependencyParseLayerExist");
        DependencyParsingLayer result = weblichtTcfProfile.getTextCorpusStored().getDependencyParsingLayer();
        assertTrue("Dependency Parser Layer exists in TCF file", result != null);
        assertEquals("[[t_1] <- [ ], [t_3] <- [t_4], [t_4] <- [t_2], [t_0] <- [t_1], [t_2] <- [t_1]]", result.getParse(0).toString());
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneView_toCoreferenceResolverLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneView_toCoreferenceResolverLayerExist");
        ReferencesLayer result = weblichtTcfProfile.getTextCorpusStored().getReferencesLayer();
        assertTrue("References Layer exists in TCF file", result != null);
        assertEquals("[rc_0  [t_10], rc_1 anaphoric ->[rc_2, rc_0] [t_6], rc_2  [t_0]]", result.getReferencedEntity(0).toString());
    }

}
