/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import de.tuebingen.uni.sfs.lapps.lifconverter.resources.ConverterResource;
import de.tuebingen.uni.sfs.lapps.lifconverter.tests.utils.LifToTcfConversionAssertUtils;
import de.tuebingen.uni.sfs.lapps.lifconverter.utils.StreamingOutputExtended;
import de.tuebingen.uni.sfs.lapps.lifconverter.tests.utils.LifToTcfConversionAssertUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class LifToTcfLayerConversion {

    private String TOKEN_LAYER_LIF_INPUT = "/data/tokens/lif-tokenLayer.json";
    private String TOKEN_LAYER_TCF_EXPECTED_OUTPUT = "/data/tokens/lif-tokenLayer-output-expected.xml";

    private String SENTENCE_LAYER_LIF_INPUT = "/data/sen/lif-sentenceLayer.json";
    private String SENTENCE_LAYER_TCF_EXPECTED_OUTPUT = "/data/sen/lif-sentenceLayer-output-expected.xml";

    private String POS_LAYER_LIF_INPUT = "/data/pos/lif-posLayer.json";
    private String POS_LAYER_TCF_EXPECTED_OUTPUT = "/data/pos/lif-posLayer-output-expected.xml";

    private String LEMMA_LAYER_LIF_INPUT = "/data/lem/lif-lemmaLayer.json";
    private String LEMMA_LAYER_EXAMPLE_TCF_EXPECTED_OUTPUT = "/data/lem/lif-lemmaLayer-output-expected.xml";

    private String NAMED_ENTITY_LAYER_LIF_INPUT = "/data/ner/lif-nameEntittyLayer.json";
    private String NAMED_ENTITY_LAYER_TCF_EXPECTED_OUTPUT = "/data/ner/lif-nameEntittyLayer-output-expected.xml";

    private String DEPENDENCY_PARSE_LAYER_LIF_INPUT = "/data/dep/lif-dependencyLayer.json";
    private String DEPENDENCY_PARSE_LAYER_TCF_EXPECTED_OUTPUT = "/data/dep/lif-dependencyLayer-output-expected.xml";

    private String CONSTITUENT_PARSE_LAYER_LIF_INPUT = "/data/con/lif-constituentLayer.json";
    private String CONSTITUENT_PARSE_LAYER_TCF_EXPECTED_OUTPUT = "/data/con/lif-constituentLayer-output-expected.xml";

    private String CORFERENCE_LAYER_LIF_INPUT = "/data/cor/lif-corferenceLayer.json";
    private String CORFERENCE_LAYER_TCF_EXPECTED_OUTPUT = "/data/cor/lif-corferenceLayer-output-expected.xml";

    public LifToTcfLayerConversion() {
    }

    /**
     * Test of lif to tcf toToken layer conversion
     */
    @Test
    public void testLayerConversion_whenTokenLayer() throws Exception {
        System.out.println("testLayerConversion_whenTokenLayer");
        InputStream input = this.getClass().getResourceAsStream(TOKEN_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(TOKEN_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toSentence layer conversion
     */
    @Test
    public void testLayerConversion_whenSentenceLayer() throws Exception {
        System.out.println("testLayerConversion_whenSentenceLayer");
        InputStream input = this.getClass().getResourceAsStream(SENTENCE_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(SENTENCE_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toPos layer conversion
     */
    @Test
    public void testLayerConversion_whenPosLayer() throws Exception {
        System.out.println("testLayerConversion_whenPosLayer");
        InputStream input = this.getClass().getResourceAsStream(POS_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(POS_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toLemma layer conversion
     */
    @Test
    public void testLayerConversion_whenLemmaLayer() throws Exception {
        System.out.println("testLayerConversion_whenLemmaLayer");
        InputStream input = this.getClass().getResourceAsStream(LEMMA_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(LEMMA_LAYER_EXAMPLE_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toNamed Entity layer conversion
     */
    @Test
    public void testLayerConversion_whenNameEntityLayer() throws Exception {
        System.out.println("testLayerConversion_whenNameEntityLayer");
        InputStream input = this.getClass().getResourceAsStream(NAMED_ENTITY_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(NAMED_ENTITY_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toDependency layer conversion
     */
    @Test
    public void testLayerConversion_whenDependencyParserLayer() throws Exception {
        System.out.println("testLayerConversion_whenDependencyParserLayer");
        InputStream input = this.getClass().getResourceAsStream(DEPENDENCY_PARSE_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(DEPENDENCY_PARSE_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toConstituebt layer conversion
     */
    @Test
    public void testLayerConversion_whenConstituentParserLayer() throws Exception {
        System.out.println("testLayerConversion_whenConstituentParserLayer");
        InputStream input = this.getClass().getResourceAsStream(CONSTITUENT_PARSE_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(CONSTITUENT_PARSE_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

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
