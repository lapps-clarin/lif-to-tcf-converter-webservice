/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import de.tuebingen.uni.sfs.lapps.lifconverter.tests.utils.LifToTcfConversionAssertUtils;
import java.io.InputStream;
import org.junit.Test;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class LifToTcfLemmaLayerConversionTest {

    private String LEMMA_LAYER_LIF_INPUT = "/data/lem/lif-lemmaLayer.json";
    private String LEMMA_LAYER_TCF_EXPECTED_OUTPUT = "/data/lem/lif-lemmaLayer-output-expected.xml";

    private String LEMMA_LAYER_WITH_POS_LIF_INPUT = "/data/lem/lif-lemmaLayer-variation1.json";
    private String LEMMA_LAYER_WITH_POS_LIF_TCF_EXPECTED_OUTPUT = "/data/lem/lif-lemmaLayer-variation1-output-expected.xml";

    /**
     * Test of lif to tcf toLemma layer conversion. This is ideal case of lemma
     * layer
     */
    @Test
    public void testLayerConversion_whenLifLemmaLayer_ContainsLemma() throws Exception {
        System.out.println("testLayerConversion_whenLifTokenLayer_ContainsLemma");
        InputStream input = this.getClass().getResourceAsStream(LEMMA_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(LEMMA_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toLemma layer conversion. This is a test when the
     * lemma layer contains pos layer but metadata does not mention that.
     */
    @Test
    public void testLayerConversion_whenLifLemmaLayer_ContainsLemmaPos() throws Exception {
        System.out.println("testLayerConversion_whenLifTokenLayer_ContainsLemmaPos");
        InputStream input = this.getClass().getResourceAsStream(LEMMA_LAYER_WITH_POS_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(LEMMA_LAYER_WITH_POS_LIF_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

}
