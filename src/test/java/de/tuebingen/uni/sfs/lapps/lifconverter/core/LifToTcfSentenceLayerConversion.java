/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import de.tuebingen.uni.sfs.lapps.lifconverter.tests.utils.LifToTcfConversionAssertUtils;
import java.io.InputStream;
import javax.ws.rs.WebApplicationException;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class LifToTcfSentenceLayerConversion {

    private String SENTENCE_LAYER_LIF_INPUT = "/data/sen/lif-sentenceLayer.json";
    private String SENTENCE_LAYER_BEFORE_TOKEN_LAYERLIF_INPUT = "/data/sen/lif-sentenceLayer-before-tokenLayer.json";
    private String SENTENCE_LAYER_NO_TOKEN_LAYER_LIF_INPUT = "/data/sen/lif-sentenceLayer-no-tokenlayer.json";
    private String SENTENCE_LAYER_TCF_EXPECTED_OUTPUT = "/data/sen/lif-sentenceLayer-output-expected.xml";

    /**
     * Test of lif to tcf toSentence layer conversion. This is a test when
     * sentence layer and token layer is to gather. The sentence layer comes
     * after token layer.
     */
    @Test
    public void testLayerConversion_whenSentenceLayer_tokenSentence() throws Exception {
        System.out.println("testLayerConversion_whenSentenceLayer_tokenSentence");
        InputStream input = this.getClass().getResourceAsStream(SENTENCE_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(SENTENCE_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toSentence layer conversion. This is a test when
     * sentence layer and token layer is to gather. The token layer comes after
     * sentence layer.
     */
    @Test
    public void testLayerConversion_whenSentenceLayer_Sentencetoken() throws Exception {
        System.out.println("testLayerConversion_whenSentenceLayer_Sentencetoken");
        InputStream input = this.getClass().getResourceAsStream(SENTENCE_LAYER_BEFORE_TOKEN_LAYERLIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(SENTENCE_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toSentence layer conversion. This is a test when
     * sentence layer but no token layer. It throws exception
     */
    @Test(expected = WebApplicationException.class)
    public void testLayerConversion_whenOnlySentenceLayer() throws Exception {
        System.out.println("testLayerConversion_whenOnlySentenceLayer");
        InputStream input = this.getClass().getResourceAsStream(SENTENCE_LAYER_NO_TOKEN_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(SENTENCE_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }
}
