/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import de.tuebingen.uni.sfs.lapps.exceptions.JsonValidityException;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.impl.ConvertAnnotationsImpl;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.VocabularyMappingException;
import de.tuebingen.uni.sfs.lapps.profile.impl.LifProfiler;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class ConverterToolTest {

    private String ALL_LAYER_EXAMPLE = "/data/karen-all.json";
    private ConvertAnnotationsImpl weblichtTcfProfile;

    @Before
    public void setUp() throws VocabularyMappingException, LifException, ConversionException, IOException, JsonValidityException {
        InputStream is = this.getClass().getResourceAsStream(ALL_LAYER_EXAMPLE);
        LifProfiler lappsLifProfile = new LifProfiler(is);
        ConverterTool instance = new ConverterTool();
        weblichtTcfProfile = instance.convertFormat(lappsLifProfile);
    }

    /**
     * Test of convertFormat method, of class ConverterTool.
     */
    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_TextLayerExist() throws Exception {
        System.out.println("testConvertFormatAllLayersInOneFile_TextLayer");
        assertTrue("Text Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getTextLayer() != null);
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_TokenLayerExist() throws Exception {
        System.out.println("testConvertFormatAllLayersInOneFile_TokenLayer");
        assertTrue("Token Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getTextLayer() != null);
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_PosLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_PosLayerExist");
        assertTrue("PosTag Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getPosTagsLayer() != null);
    }

    //This file does not contain a lemma layer. 
    //TO do create another file that takes lemma layer
    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_LemmaLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_LemmaLayerExist");
        assertFalse("Lemma Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getLemmasLayer() != null);
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_SentenceLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_LemmaLayerExist");
        assertTrue("Sentence Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getSentencesLayer() != null);
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_NamedEntityLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_NamedEntityLayerExist");
        assertTrue("NameEntitty Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getNamedEntitiesLayer() != null);
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_ConstituentParseLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_ConstituentParseLayerExist");
        assertTrue("Constituent Parser Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getConstituentParsingLayer() != null);
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_DependencyParseLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_DependencyParseLayerExist");
        assertTrue("Dependency Parser Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getDependencyParsingLayer() != null);
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_toCoreferenceResolverLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_toCoreferenceResolverLayerExist");
        assertTrue("References Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getReferencesLayer() != null);
    }

    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_toTextSourceLayerExist() {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_toTextSourceLayerExist");
        assertTrue("Text source Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getTextSourceLayer() != null);
    }
}
