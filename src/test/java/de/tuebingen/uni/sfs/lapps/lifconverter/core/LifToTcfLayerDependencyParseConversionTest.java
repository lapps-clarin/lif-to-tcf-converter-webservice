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
public class LifToTcfLayerDependencyParseConversionTest {

    private String DEPENDENCY_PARSE_LAYER_LIF_INPUT = "/data/dep/lif-dependencyLayer.json";
    private String DEPENDENCY_PARSE_LAYER_TCF_EXPECTED_OUTPUT = "/data/dep/lif-dependencyLayer-output-expected.xml";

    private String DEPENDENCY_PARSE_LAYER_LIF_WITHOUT_SENTENCE_INPUT = "/data/dep/lif-dependencyLayer-without-sentence.json";
    private String DEPENDENCY_PARSE_LAYER_LIF_WITH_SENTENCE_INPUT = "/data/dep/lif-dependencyLayer-with-sentence.json";
    private String DEPENDENCY_PARSE_LAYER_TCF_WITH_SENTENCE_EXPECTED_OUTPUT = "/data/dep/lif-dependencyLayer-with-senetence-output-expected.xml";

    /**
     * Test of lif to tcf toDependency layer conversion. This is a test of
     * dependency parser when everything is in place.
     */
    @Test
    public void testLayerConversion_whenDependencyParserLayer() throws Exception {
        System.out.println("testLayerConversion_whenDependencyParserLayer");
        InputStream input = this.getClass().getResourceAsStream(DEPENDENCY_PARSE_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(DEPENDENCY_PARSE_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toDependency layer conversion. This test when sentence
     * layer is not inside in dependency structure layer.
     */
    @Test
    public void testLayerConversion_whenDependencyParserLayerr_WithSenetenceSeperate() throws Exception {
        System.out.println("testLayerConversion_whenDependencyParserLayerr__WithSenetenceSeperate");
        InputStream input = this.getClass().getResourceAsStream(DEPENDENCY_PARSE_LAYER_LIF_WITH_SENTENCE_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(DEPENDENCY_PARSE_LAYER_TCF_WITH_SENTENCE_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toDependency layer conversion. This is a test when
     * sentence layer does not exist.
     */
    @Test(expected = WebApplicationException.class)
    public void testLayerConversion_whenDependencyParserLayerr_WithoutSentence() throws Exception {
        System.out.println("testLayerConversion_whenDependencyParserLayerr_WithoutSentence");
        InputStream input = this.getClass().getResourceAsStream(DEPENDENCY_PARSE_LAYER_LIF_WITHOUT_SENTENCE_INPUT);
        LifToTcfConversionAssertUtils.lifToTcf(input);
    }

}
