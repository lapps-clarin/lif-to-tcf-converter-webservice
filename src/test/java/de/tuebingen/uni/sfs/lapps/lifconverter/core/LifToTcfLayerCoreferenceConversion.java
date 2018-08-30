/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import de.tuebingen.uni.sfs.lapps.lifconverter.tests.utils.LifToTcfConversionAssertUtils;
import java.io.InputStream;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class LifToTcfLayerCoreferenceConversion {

    private String CORFERENCE_LAYER_LIF_INPUT = "/data/cor/lif-corferenceLayer.json";
    private String CORFERENCE_LAYER_TCF_EXPECTED_OUTPUT = "/data/cor/lif-corferenceLayer-output-expected.xml";

    /**
     * Test of lif to tcf toCoreferenceResolver layer conversion
     */
    @Test
    public void testLayerConversion_whenCoreferenceResolverLayer() throws Exception {
        System.out.println("testLayerConversion_whenCoreferenceResolverLayer");
        InputStream input = this.getClass().getResourceAsStream(CORFERENCE_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(CORFERENCE_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

}
