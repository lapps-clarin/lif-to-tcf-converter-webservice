/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.lapps.core.impl.layer.LifSingleLayer;
import de.tuebingen.uni.sfs.lapps.core.impl.profiler.LifFormatImpl;
import eu.clarin.weblicht.wlfxb.tc.api.TokensLayer;
import java.io.InputStream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.lappsgrid.discriminator.Discriminators;

/**
 *
 * @author felahi
 */
public class ConvertToTcfFormatTokenLayerTest {

    private String TOKEN_LAYER_EXAMPLE = "/data/tokens/lif-tokenLayer.json";

    private LifSingleLayer lifLayer;
    private ConvertToTcfFormat weblichtTcfProfile;

    public ConvertToTcfFormatTokenLayerTest() {
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
        weblichtTcfProfile = new ConvertToTcfFormat(lappsLifProfile);
        for (LifSingleLayer lifLayer : lappsLifProfile.getLifAnnotationLayers().getLayers()) {
            if (lifLayer.getLayer().contains(Discriminators.Uri.TOKEN)) {
                this.lifLayer = lifLayer;
                break;
            }

        }
        weblichtTcfProfile.toTcfLayer(lifLayer);
        TokensLayer result = weblichtTcfProfile.toTcfToken();
        assertEquals("Karen", result.getToken(0).getString());
    }

}
