/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.clarind.profiler.Values;
import de.tuebingen.uni.sfs.lapps.core.layer.impl.LIFAnnotationLayer;
import de.tuebingen.uni.sfs.lapps.profile.impl.LifProfiler;
import de.tuebingen.uni.sfs.lapps.utils.AnnotationInterpreter;
import eu.clarin.weblicht.wlfxb.tc.api.ConstituentParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.api.DependencyParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.api.LemmasLayer;
import eu.clarin.weblicht.wlfxb.tc.api.NamedEntitiesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.PosTagsLayer;
import eu.clarin.weblicht.wlfxb.tc.api.ReferencesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.TokensLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusStored;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.lappsgrid.discriminator.Discriminators;

/**
 *
 * @author felahi
 */
public class ConvertAnnotationsImplTest {

    private String TOKEN_LAYER_EXAMPLE = "/data/tokens/lif-tokenLayer.json";
    private String POS_LAYER_EXAMPLE = "/data/pos/lif-posLayer.json";
    private String CORFERENCE_LAYER_EXAMPLE = "/data/cor/lif-corferenceLayer.json";
    private String NamedEntity_LAYER_EXAMPLE = "/data/ner/lif-nameEntittyLayer.json";
    private String CONSTITUENT_PARSE_LAYER_EXAMPLE = "/data/con/lif-constituentLayer.json";
    private String DEPENDENCY_PARSE_LAYER_EXAMPLE = "/data/dep/lif-dependencyLayer.json";
    private String SENTENCE_LAYER_EXAMPLE = "/data/dep/lif-dependencyLayer.json";

    private LIFAnnotationLayer lifLayer;
    private ConvertAnnotationsImpl weblichtTcfProfile;

    public ConvertAnnotationsImplTest() {
    }

    /**
     * Test of toToken method, of class ConvertAnnotationsImpl. All the
     * assertEqual will be replaces with XML comparision later.
     */
    @Test
    public void testToToken() throws Exception {
        System.out.println("toToken");
        InputStream is = this.getClass().getResourceAsStream(TOKEN_LAYER_EXAMPLE);
        LifProfiler lappsLifProfile = new LifProfiler(is);
        weblichtTcfProfile = new ConvertAnnotationsImpl(Values.LANG_EN.getName(), "", "");
        for (LIFAnnotationLayer lifLayer : lappsLifProfile.getLifAnnotationLayers().getLayers()) {
            if (lifLayer.getLayer().contains(Discriminators.Uri.TOKEN)) {
                this.lifLayer = lifLayer;
                break;
            }

        }
        TokensLayer result = weblichtTcfProfile.toToken(lifLayer);
        assertEquals("Karen", result.getToken(0).getString());
    }

    /**
     * Test of toPos method, of class ConvertAnnotationsImpl.
     */
    @Test
    public void testToPos() throws Exception {
        System.out.println("toToken");
        InputStream is = this.getClass().getResourceAsStream(POS_LAYER_EXAMPLE);
        LifProfiler lappsLifProfile = new LifProfiler(is);
        weblichtTcfProfile = new ConvertAnnotationsImpl(Values.LANG_EN.getName(), "", "");
        for (LIFAnnotationLayer lifLayer : lappsLifProfile.getLifAnnotationLayers().getLayers()) {
            if (lifLayer.getLayer().contains(Discriminators.Uri.POS)) {
                this.lifLayer = lifLayer;
                break;
            }

        }
        PosTagsLayer result = weblichtTcfProfile.toPos(lifLayer);
        assertEquals("NNP", result.getTag(0).getString());
    }

    /**
     * Test of toLemma method, of class ConvertAnnotationsImpl.
     */
    @Ignore
    public void testToLemma() throws Exception {
        System.out.println("toLemma");
        LIFAnnotationLayer lifLemmaLayer = null;
        ConvertAnnotationsImpl instance = null;
        LemmasLayer expResult = null;
        LemmasLayer result = instance.toLemma(lifLemmaLayer);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toCoreferenceResolver method, of class ConvertAnnotationsImpl.
     */
    //needs to a add any example of sentence layer
    @Ignore
    public void testToSentences() throws Exception {
        System.out.println("testToSentences");
        InputStream is = this.getClass().getResourceAsStream(SENTENCE_LAYER_EXAMPLE);
        LifProfiler lappsLifProfile = new LifProfiler(is);
        weblichtTcfProfile = new ConvertAnnotationsImpl(Values.LANG_EN.getName(), "", "");
        for (LIFAnnotationLayer lifLayer : lappsLifProfile.getLifAnnotationLayers().getLayers()) {
            if (lifLayer.getLayer().contains(Discriminators.Uri.SENTENCE)) {
                this.lifLayer = lifLayer;
                break;
            }

        }
    }

    /**
     * Test of toCoreferenceResolver method, of class ConvertAnnotationsImpl.
     */
    //error in profile
    @Test
    public void testToCoreferenceResolver() throws Exception {
        System.out.println("toCoreferenceResolver");
        InputStream is = this.getClass().getResourceAsStream(CORFERENCE_LAYER_EXAMPLE);
        LifProfiler lappsLifProfile = new LifProfiler(is);
        weblichtTcfProfile = new ConvertAnnotationsImpl(Values.LANG_EN.getName(), "", "");
        for (LIFAnnotationLayer lifLayer : lappsLifProfile.getLifAnnotationLayers().getLayers()) {
            if (lifLayer.getLayer().contains(Discriminators.Uri.COREF)) {
                this.lifLayer = lifLayer;
                break;
            }

        }
        ReferencesLayer result = weblichtTcfProfile.toCoreferenceResolver(lifLayer);
        assertEquals("[rc_0  [t_6], rc_1 anaphoric ->[rc_0] [t_0]]", result.getReferencedEntity(0).toString());
    }

    /**
     * Test of toCoreferenceResolver method, of class ConvertAnnotationsImpl.
     */
    //error in profile
    @Test
    public void testToNameEntity() throws Exception {
        System.out.println("testToNameEntity");
        InputStream is = this.getClass().getResourceAsStream(NamedEntity_LAYER_EXAMPLE);
        LifProfiler lappsLifProfile = new LifProfiler(is);
        weblichtTcfProfile = new ConvertAnnotationsImpl(Values.LANG_EN.getName(), "", "");
        for (LIFAnnotationLayer lifLayer : lappsLifProfile.getLifAnnotationLayers().getLayers()) {
            if (lifLayer.getLayer().contains(Discriminators.Uri.NE)) {
                this.lifLayer = lifLayer;
                break;
            }

        }
        NamedEntitiesLayer result = weblichtTcfProfile.toNameEntity(lifLayer);
        assertEquals("LOCATION [t_3]", result.getEntity(0).toString());
    }

    /**
     * Test of toCoreferenceResolver method, of class ConvertAnnotationsImpl.
     */
    //error in profile
    @Test
    public void testToConstituentParser() throws Exception {
        System.out.println("testToConstituentParser");
        InputStream is = this.getClass().getResourceAsStream(CONSTITUENT_PARSE_LAYER_EXAMPLE);
        LifProfiler lappsLifProfile = new LifProfiler(is);
        weblichtTcfProfile = new ConvertAnnotationsImpl(Values.LANG_EN.getName(), "", "");
        for (LIFAnnotationLayer lifLayer : lappsLifProfile.getLifAnnotationLayers().getLayers()) {
            if (lifLayer.getLayer().contains(Discriminators.Uri.PHRASE_STRUCTURE)) {
                this.lifLayer = lifLayer;
                break;
            }

        }
        ConstituentParsingLayer result = weblichtTcfProfile.toConstituentParser(lifLayer);
        assertEquals("c_11 -> ROOT ( c_10 -> S ( c_9 -> NP ( c_8 -> NNP [t_0] ) c_7 -> VP ( c_6 -> VBD [t_1] c_5 -> PP ( c_4 -> TO [t_2] c_3 -> NP ( c_2 -> NNP [t_3] c_1 -> NNP [t_4] ) ) ) c_0 -> . [t_5] ) )", result.getParse(0).toString());
    }

    /**
     * Test of toCoreferenceResolver method, of class ConvertAnnotationsImpl.
     */
    //error in profile
    @Test
    public void testToDependencyParser() throws Exception {
        System.out.println("testToDependencyParser");
        InputStream is = this.getClass().getResourceAsStream(DEPENDENCY_PARSE_LAYER_EXAMPLE);
        LifProfiler lappsLifProfile = new LifProfiler(is);
        weblichtTcfProfile = new ConvertAnnotationsImpl(Values.LANG_EN.getName(), "", "");
        for (LIFAnnotationLayer lifLayer : lappsLifProfile.getLifAnnotationLayers().getLayers()) {
            if (lifLayer.getLayer().contains(Discriminators.Uri.DEPENDENCY_STRUCTURE)) {
                this.lifLayer = lifLayer;
                break;
            }

        }
        DependencyParsingLayer result = weblichtTcfProfile.toDependencyParser(lifLayer);
        assertEquals("[nn [t_3] <- [t_4], pobj [t_4] <- [t_2], nsubj [t_0] <- [t_1], prep [t_2] <- [t_1]]", result.getParse(0).toString());
    }

}
