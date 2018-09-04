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
public class LifToTcfNamedEntityLayerConversionTest {

    private String NAMED_ENTITY_LAYER_LIF_INPUT = "/data/ner/lif-nameEntittyLayer.json";
    private String NAMED_ENTITY_LAYER_NO_TOKEN_LAYER_LIF_INPUT = "/data/ner/lif-nameEntittyLayer-no-tokenLayer.json";
    private String NAMED_ENTITY_LAYER_BEFORE_TOKEN_LAYER_LIF_INPUT = "/data/ner/lif-nameEntittyLayer-no-tokenLayer.json";
    private String NAMED_ENTITY_LAYER_EMPTY_LIF_INPUT = "/data/ner/lif-nameEntittyLayer-annotation-empty.json";
    private String NAMED_ENTITY_LAYER_TCF_EXPECTED_OUTPUT = "/data/ner/lif-nameEntittyLayer-output-expected.xml";

    /**
     * Test of lif to tcf toNamed Entity layer conversion.
     */
    @Test
    public void testLayerConversion_whenNameEntityLayer() throws Exception {
        System.out.println("testLayerConversion_whenNameEntityLayer");
        InputStream input = this.getClass().getResourceAsStream(NAMED_ENTITY_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(NAMED_ENTITY_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

    /**
     * Test of lif to tcf toNamed Entity layer conversion. //To do..serialize
     * error. It does not get token view
     
    @Ignore
    public void testLayerConversion_whenNameEntityLayerBeforeTokenLayer() throws Exception {
        System.out.println("testLayerConversion_whenNameEntityLayerBeforeTokenLayer");
        InputStream input = this.getClass().getResourceAsStream(NAMED_ENTITY_LAYER_BEFORE_TOKEN_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(NAMED_ENTITY_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }
    */
    /**
     * Test of lif to tcf toNamed Entity layer conversion. When named entity
     * annotation is empty.
     
    @Ignore
    public void testLayerConversion_whenNameEntityLayerIsEmpty() throws Exception {
        System.out.println("testLayerConversion_whenNameEntityLayerIsEmpty");
        InputStream input = this.getClass().getResourceAsStream(NAMED_ENTITY_LAYER_EMPTY_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(NAMED_ENTITY_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }
     */
    /**
     * Test of lif to tcf toNamed Entity layer conversion. This is a test of
     * Named Entity Layer when there is no token layer.
     */
    @Test(expected = WebApplicationException.class)
    public void testLayerConversion_whenNameEntityLayerNoTokenLayer() throws Exception {
        System.out.println("testLayerConversion_whenNameEntityLayerNoTokenLayer");
        InputStream input = this.getClass().getResourceAsStream(NAMED_ENTITY_LAYER_NO_TOKEN_LAYER_LIF_INPUT);
        InputStream expectedOutput = this.getClass().getResourceAsStream(NAMED_ENTITY_LAYER_TCF_EXPECTED_OUTPUT);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }
}
