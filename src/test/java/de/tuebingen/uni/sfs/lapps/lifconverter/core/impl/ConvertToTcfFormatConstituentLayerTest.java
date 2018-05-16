/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.lapps.core.impl.layer.LifSingleLayer;
import de.tuebingen.uni.sfs.lapps.core.impl.profiler.LifFormatImpl;
import eu.clarin.weblicht.wlfxb.tc.api.ConstituentParsingLayer;
import java.io.InputStream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.lappsgrid.discriminator.Discriminators;

/**
 *
 * @author felahi
 */
public class ConvertToTcfFormatConstituentLayerTest {

    private String CONSTITUENT_PARSE_LAYER_EXAMPLE = "/data/con/lif-constituentLayer.json";

    private LifSingleLayer lifLayer;
    private ConvertToTcfFormat weblichtTcfProfile;

    public ConvertToTcfFormatConstituentLayerTest() {
    }

    /**
     * Test of toCoreferenceResolver method, of class ConvertAnnotationsImpl.
     */
    //error in profile
    @Test
    public void testToConstituentParser() throws Exception {
        System.out.println("testToConstituentParser");
        InputStream is = this.getClass().getResourceAsStream(CONSTITUENT_PARSE_LAYER_EXAMPLE);
        LifFormatImpl lappsLifProfile = new LifFormatImpl(is);
        weblichtTcfProfile = new ConvertToTcfFormat(lappsLifProfile);
        for (LifSingleLayer lifLayer : lappsLifProfile.getLifAnnotationLayers().getLayers()) {
            if (lifLayer.getLayer().contains(Discriminators.Uri.PHRASE_STRUCTURE)) {
                this.lifLayer = lifLayer;
                break;
            }

        }
        weblichtTcfProfile.toTcfLayer(lifLayer);
        ConstituentParsingLayer result = weblichtTcfProfile.toTcfConstituentParser();
        assertEquals("c_11 -> ROOT ( c_10 -> S ( c_9 -> NP ( c_8 -> NNP [t_0] ) c_7 -> VP ( c_6 -> VBD [t_1] c_5 -> PP ( c_4 -> TO [t_2] c_3 -> NP ( c_2 -> NNP [t_3] c_1 -> NNP [t_4] ) ) ) c_0 -> . [t_5] ) )", result.getParse(0).toString());
    }
}
