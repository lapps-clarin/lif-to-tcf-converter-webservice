/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.lapps.core.impl.layer.LifSingleLayer;
import de.tuebingen.uni.sfs.lapps.core.impl.profiler.LifFormatImpl;
import eu.clarin.weblicht.wlfxb.tc.api.PosTagsLayer;
import java.io.InputStream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.lappsgrid.discriminator.Discriminators;

/**
 *
 * @author felahi
 */
public class ConvertToTcfFormatPosLayerTest {

    private String POS_LAYER_EXAMPLE = "/data/pos/lif-posLayer.json";

    private LifSingleLayer lifLayer;
    private ConvertToTcfFormat weblichtTcfProfile;

    public ConvertToTcfFormatPosLayerTest() {
    }

    /**
     * Test of toPos method, of class ConvertAnnotationsImpl.
     */
    @Test
    public void testToPos() throws Exception {
        System.out.println("testToPos");
        InputStream is = this.getClass().getResourceAsStream(POS_LAYER_EXAMPLE);
        LifFormatImpl lappsLifProfile = new LifFormatImpl(is);
        weblichtTcfProfile = new ConvertToTcfFormat(lappsLifProfile);
        for (LifSingleLayer lifLayer : lappsLifProfile.getLifAnnotationLayers().getLayers()) {
            if (lifLayer.getLayer().contains(Discriminators.Uri.POS)) {
                this.lifLayer = lifLayer;
                break;
            }

        }
        weblichtTcfProfile.toTcfLayer(lifLayer);
        PosTagsLayer result = weblichtTcfProfile.toTcfPos();
        assertEquals("NNP", result.getTag(0).getString());
    }
}
