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
public class LifToTcfTokenLayerConversionTest {

    private String TOKEN_LAYER_LIF_INPUT = "/data/tokens/lif-tokenLayer.json";
    private String TOKEN_LAYER_TCF_EXPECTED_OUTPUT = "/data/tokens/lif-tokenLayer-output-expected.xml";

    private String TOKEN_LAYER_WITH_POS_LIF_INPUT = "/data/tokens/lif-tokenLayer-with-pos.json";
    private String TOKEN_LAYER_WITH_POS_TCF_EXPECTED_OUTPUT = "/data/tokens/lif-tokenLayer-with-pos-output-expected.xml";
    
    private String TOKEN_LAYER_WITH_EXTRA_LIF_INPUT = "/data/tokens/lif-tokenLayer-with-extra.json";

    /**
     * Test of lif to tcf toToken layer conversion. The meta data of the layer
     * indicates token and the annotation contains only token string
     */
    @Test
    public void testLayerConversion_whenLifTokenLayer_ContainTokens() throws Exception {
        System.out.println("testLayerConversion_whenTokenLayer");
        InputStream input = this.getClass().getResourceAsStream(TOKEN_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(TOKEN_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toToken layer conversion. The meta data of the layer
     * indicates token and the annotation contains token string and pos layer
     */
    @Test
    public void testLayerConversion_whenLifTokenLayer_ContainTokensPos() throws Exception {
        System.out.println("testLayerConversion_whenTokenLayer");
        InputStream input = this.getClass().getResourceAsStream(TOKEN_LAYER_WITH_POS_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(TOKEN_LAYER_WITH_POS_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }
    
     /**
     * Test of lif to tcf toToken layer conversion. The feature dictionary has unknown features.
     */
    @Test
    public void testLayerConversion_whenLifTokenLayer_ContainUnknowFeature() throws Exception {
        System.out.println("testLayerConversion_whenTokenLayer");
        InputStream input = this.getClass().getResourceAsStream(TOKEN_LAYER_WITH_EXTRA_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(TOKEN_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }


}
