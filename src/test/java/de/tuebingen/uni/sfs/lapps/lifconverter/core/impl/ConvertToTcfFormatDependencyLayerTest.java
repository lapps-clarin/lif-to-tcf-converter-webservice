/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.lapps.core.impl.layer.LifSingleLayer;
import de.tuebingen.uni.sfs.lapps.core.impl.profiler.LifFormatImpl;
import eu.clarin.weblicht.wlfxb.tc.api.ConstituentParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.api.DependencyParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.api.LemmasLayer;
import eu.clarin.weblicht.wlfxb.tc.api.NamedEntitiesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.PosTagsLayer;
import eu.clarin.weblicht.wlfxb.tc.api.ReferencesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.SentencesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.TokensLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import java.io.InputStream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.lappsgrid.discriminator.Discriminators;

/**
 *
 * @author felahi
 */
public class ConvertToTcfFormatDependencyLayerTest {

    private String DEPENDENCY_PARSE_LAYER_EXAMPLE = "/data/dep/lif-dependencyLayer.json";
    private String SENTENCE_LAYER_EXAMPLE = "/data/sen/lif-sentenceLayer.json";

    private LifSingleLayer lifLayer;
    private ConvertToTcfFormat weblichtTcfProfile;

    public ConvertToTcfFormatDependencyLayerTest() {
    }

    /**
     * Test of toCoreferenceResolver method, of class ConvertAnnotationsImpl.
     */
    //error in profile
    @Test
    public void testToDependencyParser() throws Exception {
        System.out.println("testToDependencyParser");
        InputStream is = this.getClass().getResourceAsStream(DEPENDENCY_PARSE_LAYER_EXAMPLE);
        LifFormatImpl lappsLifProfile = new LifFormatImpl(is);
        weblichtTcfProfile = new ConvertToTcfFormat(lappsLifProfile);
        for (LifSingleLayer lifLayer : lappsLifProfile.getLifAnnotationLayers().getLayers()) {
            if (lifLayer.getLayer().contains(Discriminators.Uri.DEPENDENCY_STRUCTURE)) {
                this.lifLayer = lifLayer;
                break;
            }

        }
        weblichtTcfProfile.toTcfLayer(lifLayer);
        DependencyParsingLayer result = weblichtTcfProfile.toTcfDependencyParser();
        assertEquals("[nn [t_3] <- [t_4], pobj [t_4] <- [t_2], nsubj [t_0] <- [t_1], prep [t_2] <- [t_1]]", result.getParse(0).toString());
    }

}
