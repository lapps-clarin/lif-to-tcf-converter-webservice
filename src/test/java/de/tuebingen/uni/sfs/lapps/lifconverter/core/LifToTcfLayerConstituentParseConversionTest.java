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
public class LifToTcfLayerConstituentParseConversionTest {

    private String CONSTITUENT_PARSE_LAYER_LIF_INPUT = "/data/con/lif-constituentLayer.json";
    private String CONSTITUENT_PARSE_LAYER_LIF_WITH_SENTENCELAYER_SEPERATE_INPUT = "/data/con/lif-constituentLayer-with-sentencelayer-seperate.json";
    private String CONSTITUENT_PARSE_LAYER_TCF_EXPECTED_OUTPUT = "/data/con/lif-constituentLayer-output-expected.xml";

    private String CONSTITUENT_PARSE_LAYER_LIF_MULTIPLE_SENTENCES_INPUT = "/data/con/lif-constituentLayer-paragraph.json";
    private String CONSTITUENT_PARSE_LAYER_TCF_MULTIPLE_SENTENCES_EXPECTED_OUTPUT = "/data/con/lif-constituentLayer-paragraph-output-expected.xml";

    private String CONSTITUENT_PARSE_LAYER_LIF_WITHOUT_SENTENCE_INPUT = "/data/con/lif-constituentLayer-without-sentence.json";
    private String CONSTITUENT_PARSE_LAYER_LIF_WITHOUT_ROOT_INPUT = "/data/con/lif-constituentLayer-without-root.json";
    private String CONSTITUENT_PARSE_LAYER_LIF_WITHOUT_TOKENS_INPUT = "/data/con/lif-constituentLayer-without-tokens.json";
    private String CONSTITUENT_PARSE_LAYER_LIF_MISSING_ANNOTATIONS_INPUT = "/data/con/lif-constituentLayer-without-annotation.json";

    /**
     * Test of lif to tcf toConstituebt layer conversion. This test when all the
     * annotations of lif are in place. The sentence layer is inside the same
     * view.
     */
    @Test
    public void testLayerConversion_whenConstituentParserLayer() throws Exception {
        System.out.println("testLayerConversion_whenConstituentParserLayer");
        InputStream input = this.getClass().getResourceAsStream(CONSTITUENT_PARSE_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(CONSTITUENT_PARSE_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toConstituebt layer conversion. This test when
     * sentence layer is not inside in phrase structure layer. Sentence layer is
     * a seperate layer.
     */
    @Test
    public void testLayerConversion_whenConstituentParserLayer_WithSenetenceSeperate() throws Exception {
        System.out.println("testLayerConversion_whenConstituentParserLayer_WithSenetenceSeperate");
        InputStream input = this.getClass().getResourceAsStream(CONSTITUENT_PARSE_LAYER_LIF_WITH_SENTENCELAYER_SEPERATE_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(CONSTITUENT_PARSE_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toConstituebt layer conversion. This test when many
     * sentences inside in phrase structure layer. This is test of parsing
     * paragraph
     */
    @Test
    public void testLayerConversion_whenConstituentParserLayer_WithParagraph() throws Exception {
        System.out.println("testLayerConversion_whenConstituentParserLayer_WithParagraph");
        InputStream input = this.getClass().getResourceAsStream(CONSTITUENT_PARSE_LAYER_LIF_MULTIPLE_SENTENCES_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(CONSTITUENT_PARSE_LAYER_TCF_MULTIPLE_SENTENCES_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toConstituebt layer conversion. This is a test when
     * sentence layer does not exist.
     */
    @Test(expected = WebApplicationException.class)
    public void testLayerConversion_whenConstituentParserLayer_WithoutSentence() throws Exception {
        System.out.println("testLayerConversion_whenConstituentParserLayer_WithoutSentence");
        InputStream input = this.getClass().getResourceAsStream(CONSTITUENT_PARSE_LAYER_LIF_WITHOUT_SENTENCE_INPUT);
        LifToTcfConversionAssertUtils.lifToTcf(input);
    }

    /**
     * Test of lif to tcf toConstituebt layer conversion. This is a test when
     * root does not exist in lif. So a tcf tree building is failed.
     */
    @Test(expected = WebApplicationException.class)
    public void testLayerConversion_whenConstituentParserLayer_WithoutRoot() throws Exception {
        System.out.println("testLayerConversion_whenConstituentParserLayer_WithoutRoot");
        InputStream input = this.getClass().getResourceAsStream(CONSTITUENT_PARSE_LAYER_LIF_WITHOUT_ROOT_INPUT);
        LifToTcfConversionAssertUtils.lifToTcf(input);
    }

    /**
     * Test of lif to tcf toConstituebt layer conversion. This is a test when
     * token annotation missing in LIF.
     */
    @Test(expected = WebApplicationException.class)
    public void testLayerConversion_whenConstituentParserLayer_WithoutToken() throws Exception {
        System.out.println("testLayerConversion_whenConstituentParserLayer_MissingAnnotations");
        InputStream input = this.getClass().getResourceAsStream(CONSTITUENT_PARSE_LAYER_LIF_WITHOUT_TOKENS_INPUT);
        LifToTcfConversionAssertUtils.lifToTcf(input);
    }

    /**
     * Test of lif to tcf toConstituebt layer conversion. This is a test when
     * one ( or more) constituent annotation missing in LIF.
     */
    @Test(expected = WebApplicationException.class)
    public void testLayerConversion_whenConstituentParserLayer_MissingAnnotation() throws Exception {
        System.out.println("testLayerConversion_whenConstituentParserLayer_MissingAnnotations");
        InputStream input = this.getClass().getResourceAsStream(CONSTITUENT_PARSE_LAYER_LIF_MISSING_ANNOTATIONS_INPUT);
        LifToTcfConversionAssertUtils.lifToTcf(input);
    }

}
