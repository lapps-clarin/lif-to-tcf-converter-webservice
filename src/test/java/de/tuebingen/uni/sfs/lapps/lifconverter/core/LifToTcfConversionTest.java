/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import org.junit.Test;
import de.tuebingen.uni.sfs.lapps.lifconverter.tests.utils.LifToTcfConversionAssertUtils;
import java.io.InputStream;
/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class LifToTcfConversionTest {

    private String SINGLE_VIEW_SINGLE_LAYER_EXAMPLE = "/data/all/tool/karen-all.json";
    private String SINGLE_VIEW_SINGLE_LAYER_EXAMPLE_EXPECTED_OUTPUT = "/data/all/tool/karen-all-output-expected.xml";

    private String SINGLE_VIEW_ALL_LAYERS_EXAMPLE = "/data/all/gold/lif-all-1_1_0.json";
    private String SINGLE_VIEW_ALL_LAYERS_EXAMPLE_EXPECTED_OUTPUT = "/data/all/gold/lif-all-1_1_0-output-expected.xml";

    private String VIEW_REFERENCE_EXAMPLE = "/data/all/ref/lif-viewref.json";
    private String VIEW_REFERENCE_EXPECTED_OUTPUT = "/data/all/ref/lif-viewref-output-expected.xml";

    /**
     * Test of all layer scattered in different views in lif
     */
    @Test
    public void test_whenSingleViewReferSingleLayer() throws Exception {
        InputStream input = this.getClass().getResourceAsStream(SINGLE_VIEW_SINGLE_LAYER_EXAMPLE);
        InputStream expectedOutput = this.getClass().getResourceAsStream(SINGLE_VIEW_SINGLE_LAYER_EXAMPLE_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of single layer scattered in single view in lif
     */
    @Test
    public void test_whenSingleViewReferAllLayer() throws Exception {
        InputStream input = this.getClass().getResourceAsStream(SINGLE_VIEW_ALL_LAYERS_EXAMPLE);
        InputStream expectedOutput = this.getClass().getResourceAsStream(SINGLE_VIEW_ALL_LAYERS_EXAMPLE_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of one layer refer other view in lif
     */
    @Test
    public void test_whenOneViewReferOtherView() throws Exception {
        InputStream input = this.getClass().getResourceAsStream(VIEW_REFERENCE_EXAMPLE);
        InputStream expectedOutput = this.getClass().getResourceAsStream(VIEW_REFERENCE_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

}
