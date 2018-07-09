/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import de.tuebingen.uni.sfs.lapps.core.impl.profiler.LifFormatImpl;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.impl.ConvertToTcfFormat;
import eu.clarin.weblicht.wlfxb.tc.api.ConstituentParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.api.DependencyParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.api.LemmasLayer;
import eu.clarin.weblicht.wlfxb.tc.api.NamedEntitiesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.PosTagsLayer;
import eu.clarin.weblicht.wlfxb.tc.api.ReferencesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.SentencesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.TokensLayer;
import java.io.InputStream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
/**
 *
 * @author felahi
 */
public class ConverterToolEachFileOneLayerTest {

    private String TOKEN_LAYER_EXAMPLE = "/data/tokens/lif-tokenLayer.json";
    private String SENTENCE_LAYER_EXAMPLE = "/data/sen/lif-sentenceLayer.json";
    private String POS_LAYER_EXAMPLE = "/data/pos/lif-posLayer.json";
    private String LEMMA_LAYER_EXAMPLE = "/data/lem/lif-lemmaLayer.json";
    private String NAMED_ENTITY_LAYER_EXAMPLE = "/data/ner/lif-nameEntittyLayer.json";
    private String DEPENDENCY_PARSE_LAYER_EXAMPLE = "/data/dep/lif-dependencyLayer.json";
    private String CORFERENCE_LAYER_EXAMPLE = "/data/cor/lif-corferenceLayer.json";
    private String CONSTITUENT_PARSE_LAYER_EXAMPLE = "/data/con/lif-constituentLayer.json";

    public ConverterToolEachFileOneLayerTest() {
    }

    /**
     * Test of toToken method, of class ConvertAnnotationsImpl. All the
     * assertEqual will be replaces with XML comparision later.
     */
    @Test
    public void testToToken() throws Exception {
        System.out.println("testToToken");
        InputStream is = this.getClass().getResourceAsStream(TOKEN_LAYER_EXAMPLE);
        LifFormatImpl lappsLifProfile = new LifFormatImpl(is);
        ConvertToTcfFormat weblichtTcfProfile = new ConvertToTcfFormat(lappsLifProfile);
        TokensLayer result = weblichtTcfProfile.getTextCorpusStored().getTokensLayer();
        assertEquals("Karen", result.getToken(0).getString());
        assertEquals("flew", result.getToken(1).getString());
    }

    /**
     * Test of toCoreferenceResolver method, of class ConvertAnnotationsImpl.
     */
    //needs to a add any example of sentence layer
    @Test
    public void testToSentences() throws Exception {
        System.out.println("testToSentences");
        InputStream is = this.getClass().getResourceAsStream(SENTENCE_LAYER_EXAMPLE);
        LifFormatImpl lappsLifProfile = new LifFormatImpl(is);
        ConvertToTcfFormat weblichtTcfProfile = new ConvertToTcfFormat(lappsLifProfile);
        SentencesLayer result = weblichtTcfProfile.getTextCorpusStored().getSentencesLayer();
        assertEquals("[t_0, t_1, t_2, t_3, t_4, t_5, t_6, t_7, t_8, t_9, t_10, t_11, t_12]", result.getSentence(0).toString());
    }

    /**
     * Test of toPos method, of class ConvertAnnotationsImpl.
     */
    @Test
    public void testToPos() throws Exception {
        System.out.println("testToPos");
        InputStream is = this.getClass().getResourceAsStream(POS_LAYER_EXAMPLE);
        LifFormatImpl lappsLifProfile = new LifFormatImpl(is);
        ConvertToTcfFormat weblichtTcfProfile = new ConvertToTcfFormat(lappsLifProfile);
        PosTagsLayer result = weblichtTcfProfile.getTextCorpusStored().getPosTagsLayer();
        assertEquals("NNP", result.getTag(0).getString());
        assertEquals("VBD", result.getTag(1).getString());
    }
    
     /**
     * Test of toPos method, of class ConvertAnnotationsImpl.
     */
    @Test
    public void testToLemma() throws Exception {
        System.out.println("testToPos");
        InputStream is = this.getClass().getResourceAsStream(LEMMA_LAYER_EXAMPLE);
        LifFormatImpl lappsLifProfile = new LifFormatImpl(is);
        ConvertToTcfFormat weblichtTcfProfile = new ConvertToTcfFormat(lappsLifProfile);
        LemmasLayer result = weblichtTcfProfile.getTextCorpusStored().getLemmasLayer();
        assertEquals("Karen", result.getLemma(0).getString());
        assertEquals("flew", result.getLemma(1).getString());
    }
    
    
  

    /**
     * Test of toCoreferenceResolver method, of class ConvertAnnotationsImpl.
     */
    @Test
    public void testToNameEntity() throws Exception {
        System.out.println("testToNameEntity");
        InputStream is = this.getClass().getResourceAsStream(NAMED_ENTITY_LAYER_EXAMPLE);
        LifFormatImpl lappsLifProfile = new LifFormatImpl(is);
        ConvertToTcfFormat weblichtTcfProfile = new ConvertToTcfFormat(lappsLifProfile);
        NamedEntitiesLayer result = weblichtTcfProfile.getTextCorpusStored().getNamedEntitiesLayer();
        assertEquals("LOCATION [t_3]", result.getEntity(0).toString());
    }

    @Test
    public void testToDependencyParser() throws Exception {
        System.out.println("testToDependencyParser");
        InputStream is = this.getClass().getResourceAsStream(DEPENDENCY_PARSE_LAYER_EXAMPLE);
        LifFormatImpl lappsLifProfile = new LifFormatImpl(is);
        ConvertToTcfFormat weblichtTcfProfile = new ConvertToTcfFormat(lappsLifProfile);
        DependencyParsingLayer result = weblichtTcfProfile.getTextCorpusStored().getDependencyParsingLayer();
        assertEquals("[nn [t_3] <- [t_4], pobj [t_4] <- [t_2], nsubj [t_0] <- [t_1], prep [t_2] <- [t_1]]", result.getParse(0).toString());
    }

    /**
     * Test of toCoreferenceResolver method, of class ConvertAnnotationsImpl.
     */
    @Test
    public void testToConstituentParser() throws Exception {
        System.out.println("testToConstituentParser");
        InputStream is = this.getClass().getResourceAsStream(CONSTITUENT_PARSE_LAYER_EXAMPLE);
        LifFormatImpl lappsLifProfile = new LifFormatImpl(is);
        ConvertToTcfFormat weblichtTcfProfile = new ConvertToTcfFormat(lappsLifProfile);
        ConstituentParsingLayer result = weblichtTcfProfile.getTextCorpusStored().getConstituentParsingLayer();
        assertEquals("c_11 -> ROOT ( c_10 -> S ( c_9 -> NP ( c_8 -> NNP [t_0] ) c_7 -> VP ( c_6 -> VBD [t_1] c_5 -> PP ( c_4 -> TO [t_2] c_3 -> NP ( c_2 -> NNP [t_3] c_1 -> NNP [t_4] ) ) ) c_0 -> . [t_5] ) )", result.getParse(0).toString());
    }

    /**
     * Test of toCoreferenceResolver method, of class ConvertAnnotationsImpl.
     */
    //error in profile
    @Test
    public void testToCoreferenceResolver() throws Exception {
        System.out.println("toCoreferenceResolver");
        InputStream is = this.getClass().getResourceAsStream(CORFERENCE_LAYER_EXAMPLE);
        LifFormatImpl lappsLifProfile = new LifFormatImpl(is);
        ConvertToTcfFormat weblichtTcfProfile = new ConvertToTcfFormat(lappsLifProfile);
        ReferencesLayer result = weblichtTcfProfile.getTextCorpusStored().getReferencesLayer();
        assertEquals("[rc_0  [t_6], rc_1 anaphoric ->[rc_0] [t_0]]", result.getReferencedEntity(0).toString());
    }

}
