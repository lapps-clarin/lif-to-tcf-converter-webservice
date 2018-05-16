/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.lapps.core.impl.layer.LifSingleLayer;
import de.tuebingen.uni.sfs.lapps.core.impl.profiler.LifFormatImpl;
import eu.clarin.weblicht.wlfxb.tc.api.ReferencesLayer;
import java.io.InputStream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.lappsgrid.discriminator.Discriminators;

/**
 *
 * @author felahi
 */
public class ConvertToTcfFormatCoreferencTest {

    private String CORFERENCE_LAYER_EXAMPLE = "/data/cor/lif-corferenceLayer.json";

    private LifSingleLayer lifLayer;
    private ConvertToTcfFormat weblichtTcfProfile;

    public ConvertToTcfFormatCoreferencTest() {
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
        weblichtTcfProfile = new ConvertToTcfFormat(lappsLifProfile);
        for (LifSingleLayer lifLayer : lappsLifProfile.getLifAnnotationLayers().getLayers()) {
            if (lifLayer.getLayer().contains(Discriminators.Uri.COREF)) {
                this.lifLayer = lifLayer;
                break;
            }

        }
        weblichtTcfProfile.toTcfLayer(lifLayer);
        ReferencesLayer result = weblichtTcfProfile.toTcfCoreferenceResolver();
        assertEquals("[rc_0  [t_6], rc_1 anaphoric ->[rc_0] [t_0]]", result.getReferencedEntity(0).toString());
    }

}
