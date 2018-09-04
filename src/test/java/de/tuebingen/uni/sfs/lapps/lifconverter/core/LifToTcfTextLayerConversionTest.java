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
public class LifToTcfTextLayerConversionTest {

    private String TEXT_LAYER_LIF_INPUT = "/data/text/lif-textLayer.json";
    private String TEXT_LAYER_TCF_EXPECTED_OUTPUT = "/data/text/lif-textLayer-output-expected.xml";

    private String TEXT_LAYER_WITH_NO_TEXT_LIF_INPUT = "/data/text/lif-textLayer-no-view-empty.json";
    private String TEXT_LAYER_WITH_NO_TEXT_TCF_EXPECTED_OUTPUT = "/data/text/lif-textLayer-no-view-empty-output-expected.xml";

    private String TEXT_LAYER_WITH_NO_TEXT_NO_VIEW_LIF_INPUT = "/data/text/lif-textLayer-no-view-no.json";
    private String TEXT_LAYER_WITH_NO_TEXT_NO_VIEW_TCF_EXPECTED_OUTPUT = "/data/text/lif-textLayer-no-view-no-output-expected.xml";

    /**
     * Test of lif to tcf toText layer conversion. The text layer exists and the
     * view is empty
     */
    @Test
    public void testLayerConversion_whenTextLayer() throws Exception {
        System.out.println("testLayerConversion_whenTokenLayer");
        InputStream input = this.getClass().getResourceAsStream(TEXT_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(TEXT_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toText layer conversion. The text layer does not exist
     * and the view is empty
     */
    @Test
    public void testLayerConversion_whenNoTextLayer() throws Exception {
        System.out.println("testLayerConversion_whenTokenLayer");
        InputStream input = this.getClass().getResourceAsStream(TEXT_LAYER_WITH_NO_TEXT_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(TEXT_LAYER_WITH_NO_TEXT_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toText layer conversion. The text layer does not exist
     * and the view is empty
     */
    @Test
    public void testLayerConversion_whenNoTextNoViewLayer() throws Exception {
        System.out.println("testLayerConversion_whenTokenLayer");
        InputStream input = this.getClass().getResourceAsStream(TEXT_LAYER_WITH_NO_TEXT_NO_VIEW_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(TEXT_LAYER_WITH_NO_TEXT_NO_VIEW_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

}
