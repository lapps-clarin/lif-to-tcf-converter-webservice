/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import org.junit.Test;
import de.tuebingen.uni.sfs.lapps.lifconverter.tests.utils.LifToTcfConversionAssertUtils;
import java.io.InputStream;
import org.junit.Ignore;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class LifToTcfAllLayerConversionTest {

    private String SINGLE_VIEW_SINGLE_LAYER_EXAMPLE = "/data/all/tool/karen-all.json";
    private String SINGLE_VIEW_SINGLE_LAYER_EXAMPLE_EXPECTED_OUTPUT = "/data/all/tool/karen-all-output-expected.xml";

    private String SINGLE_VIEW_ALL_LAYERS_EXAMPLE = "/data/all/gold/lif-all-1_1_0.json";
    private String SINGLE_VIEW_ALL_LAYERS_EXAMPLE_EXPECTED_OUTPUT = "/data/all/gold/lif-all-1_1_0-output-expected.xml";

    private String VIEW_REFERENCE_EXAMPLE = "/data/all/ref/lif-viewref.json";
    private String VIEW_REFERENCE_EXPECTED_OUTPUT = "/data/all/ref/lif-viewref-output-expected.xml";

    /**
     * Test of all layer when layers are scattered in different views in lif
     * document
     */
    @Test
    public void test_whenSingleViewSingleLayer_LifDocument() throws Exception {
        InputStream input = this.getClass().getResourceAsStream(SINGLE_VIEW_SINGLE_LAYER_EXAMPLE);
        InputStream expectedOutput = this.getClass().getResourceAsStream(SINGLE_VIEW_SINGLE_LAYER_EXAMPLE_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of all layers when a single view contains all layers in lif document
     */
    @Test
    public void test_whenSingleViewContainsAllLayer_LifDocument() throws Exception {
        InputStream input = this.getClass().getResourceAsStream(SINGLE_VIEW_ALL_LAYERS_EXAMPLE);
        InputStream expectedOutput = this.getClass().getResourceAsStream(SINGLE_VIEW_ALL_LAYERS_EXAMPLE_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of all layers when one layer refer other view in lif document
     */
    @Test
    public void test_whenOneViewReferOtherView_LifDocument() throws Exception {
        InputStream input = this.getClass().getResourceAsStream(VIEW_REFERENCE_EXAMPLE);
        InputStream expectedOutput = this.getClass().getResourceAsStream(VIEW_REFERENCE_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

}
