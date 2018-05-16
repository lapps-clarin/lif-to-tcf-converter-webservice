/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.lapps.core.impl.layer.LifSingleLayer;
import de.tuebingen.uni.sfs.lapps.core.impl.profiler.LifFormatImpl;
import eu.clarin.weblicht.wlfxb.tc.api.SentencesLayer;
import java.io.InputStream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.lappsgrid.discriminator.Discriminators;

/**
 *
 * @author felahi
 */
public class ConvertToTcfFormatSentenceLayerTest {

    private String SENTENCE_LAYER_EXAMPLE = "/data/sen/lif-sentenceLayer.json";

    private LifSingleLayer lifLayer;
    private ConvertToTcfFormat weblichtTcfProfile;

    public ConvertToTcfFormatSentenceLayerTest() {
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
        weblichtTcfProfile = new ConvertToTcfFormat(lappsLifProfile);
        for (LifSingleLayer lifLayer : lappsLifProfile.getLifAnnotationLayers().getLayers()) {
            if (lifLayer.getLayer().contains(Discriminators.Uri.SENTENCE)) {
                this.lifLayer = lifLayer;
                break;
            }

        }
        weblichtTcfProfile.toTcfLayer(lifLayer);
        SentencesLayer result = weblichtTcfProfile.toTcfSentences();
        assertEquals("[t_0, t_1, t_2, t_3, t_4, t_5, t_6, t_7, t_8, t_9, t_10, t_11, t_12]", result.getSentence(0).toString());
    }

}
