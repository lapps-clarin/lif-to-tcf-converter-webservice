/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import de.tuebingen.uni.sfs.lapps.exceptions.JsonValidityException;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.VocabularyMappingException;
import de.tuebingen.uni.sfs.lapps.core.impl.profiler.LifFormatImpl;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.api.LayerConverter;
import eu.clarin.weblicht.wlfxb.tc.api.ConstituentParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.api.DependencyParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.api.NamedEntitiesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.TokensLayer;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class ConverterToolViewReference {

    private String VIEW_REFERENCE_EXAMPLE = "/data/all/ref/lif-viewref.json";
    private LayerConverter weblichtTcfProfile;

    @Before
    public void setUp() throws VocabularyMappingException, LifException, ConversionException, IOException, JsonValidityException {
        InputStream is = this.getClass().getResourceAsStream(VIEW_REFERENCE_EXAMPLE);
        LifFormatImpl lappsLifProfile = new LifFormatImpl(is);
        ConverterTool instance = new ConverterTool();
        weblichtTcfProfile = instance.convertFormat(lappsLifProfile);
    }

    @Test
    public void testConvertFormat_TokenLayer_WhenViewReference() throws Exception {
        System.out.println("testConvertFormatAllLayersInOneFile_TokenLayer");
        TokensLayer result = weblichtTcfProfile.getTextCorpusStored().getTokensLayer();
        assertTrue("Token Layer exists in TCF file", result != null);
        assertEquals("Karen", result.getToken(0).getString());
    }
    
    @Test
    public void testConvertFormat_WhenAllLayersInOneFile_NamedEntityLayerExist() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_NamedEntityLayerExist");
        NamedEntitiesLayer result = weblichtTcfProfile.getTextCorpusStored().getNamedEntitiesLayer();
        assertTrue("NameEntitty Layer exists in TCF file", result != null);
        System.out.println(weblichtTcfProfile.getTextCorpusStored().getNamedEntitiesLayer().getEntity(0));
        assertEquals("LOCATION [t_3]", result.getEntity(0).toString());
        assertEquals("LOCATION [t_4]", result.getEntity(1).toString());
    }
    
    @Test
    public void testConvertFormat_DependencyParseLayer_WhenViewReference() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_DependencyParseLayerExist");
        DependencyParsingLayer result = weblichtTcfProfile.getTextCorpusStored().getDependencyParsingLayer();
        assertTrue("Dependency Parser Layer exists in TCF file", result != null);
        assertEquals("[nn [t_0] <- [t_0], pobj [t_0] <- [t_0], ROOT [t_0] <- [ ], prep [t_0] <- [t_0], nsubj [t_0] <- [t_0]]", result.getParse(0).toString());
    }
    

    @Test
    public void testConvertFormat_ConstituentParseLayer_WhenViewReference() throws Exception {
        System.out.println("testConvertFormat_WhenAllLayersInOneFile_ConstituentParseLayerExist");
        ConstituentParsingLayer result = weblichtTcfProfile.getTextCorpusStored().getConstituentParsingLayer();
        assertTrue("Constituent Parser Layer exists in TCF file", weblichtTcfProfile.getTextCorpusStored().getConstituentParsingLayer() != null);
        assertEquals("c_17 -> ROOT ( c_16 -> S ( c_15 -> NP ( c_14 -> NNP ( c_13 -> NNP [t_0] ) ) c_12 -> VP ( c_11 -> VBD ( c_10 -> VBD [t_1] ) c_9 -> PP ( c_8 -> TO ( c_7 -> TO [t_2] ) c_6 -> NP ( c_5 -> NNP ( c_4 -> NNP [t_3] ) c_3 -> NNP ( c_2 -> NNP [t_4] ) ) ) ) c_1 -> . ( c_0 -> . [t_5] ) ) )", result.getParse(0).toString());
    }

}
