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
public class LifToTcfPosLayerConversionTest {

    private String POS_LAYER_LIF_INPUT = "/data/pos/lif-posLayer.json";
    private String POS_LAYER_TCF_EXPECTED_OUTPUT = "/data/pos/lif-posLayer-output-expected.xml";

    private String POS_LAYER_POS_LAYER_DIFFER_METADATA_LIF_INPUT = "/data/pos/lif-posLayer-differ-metadata.json";
    private String POS_LAYER_POS_LAYER_DIFFER_METADATA_TCF_EXPECTED_OUTPUT = "/data/pos/lif-posLayer-differ-metadata-output-expected.xml";

    private String POS_LAYER_WITH_LEMMA_LIF_INPUT = "/data/pos/lif-posLayer-with-lemma.json";
    private String POS_LAYERWITH_LEMMA_TCF_EXPECTED_OUTPUT = "/data/pos/lif-posLayer-with-lemma-output-expected.xml";

    /**
     * Test of lif to tcf toPos layer conversion.This is a test of an ideal case
     * of pos layer
     */
    @Test
    public void testLayerConversion_whenLifTokenLayer_ContainsPos() throws Exception {
        System.out.println("testLayerConversion_whenPosLayer");
        InputStream input = this.getClass().getResourceAsStream(POS_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(POS_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toPos layer conversion. This is a test when the @type
     * is "http://vocab.lappsgrid.org/Token" instead of
     * "http://vocab.lappsgrid.org/Token#pos"
     */
    @Test
    public void testLayerConversion_whenLifTokenLayer_ContainsPosVariation1() throws Exception {
        System.out.println("testLayerConversion_whenLifTokenLayer_ContainsPosVariation1");
        InputStream input = this.getClass().getResourceAsStream(POS_LAYER_POS_LAYER_DIFFER_METADATA_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(POS_LAYER_POS_LAYER_DIFFER_METADATA_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toPos layer conversion. This is a test when the pos
     * layer contains lemma layer
     */
    @Test
    public void testLayerConversion_whenLifTokenLayer_ContainsPosVariation2() throws Exception {
        System.out.println("testLayerConversion_whenLifTokenLayer_ContainsPosVariation1");
        InputStream input = this.getClass().getResourceAsStream(POS_LAYER_WITH_LEMMA_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(POS_LAYERWITH_LEMMA_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

}
