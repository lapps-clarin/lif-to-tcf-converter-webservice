/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.lapps.lifconverter.core.ConverterTool;
import de.tuebingen.uni.sfs.lapps.profile.api.LifProfile;
import de.tuebingen.uni.sfs.lapps.profile.impl.LifProfiler;
import java.io.File;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.lappsgrid.discriminator.Discriminators;

/**
 *
 * @author felahi
 */
public class ConverToTCFAfterConversionTest {

    private String POS_EXAMPLE2 = "other/lif-posLayer-tcfToLifCon.json";
    private String TOKEN_EXAMPLE2 = "other/lif-tokSen-tcfToLifCon.json";
    private String FILE_LIF = Discriminators.Alias.JSON;
    private ClassLoader classLoader = getClass().getClassLoader();
    private File inputFile;
    private InputStream targetStream;
    private LifProfile givenLifFormat;
    private ConvertToTCFAnnotations instance;

   
    /**
     * Test of toToken method, of class ConvertToTCFAnnotations.
     */
    @Test
    public void testTokenLayerTCFtoLifConverter() throws Exception {

        inputFile = new File(classLoader.getResource(TOKEN_EXAMPLE2).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfiler(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertTrue("Token Layer exists in TCF file", instance.getTextCorpusStored().getTextLayer() != null);
        assertEquals("0: t_0 -> Karen", instance.getTextCorpusStored().getTokensLayer().getToken(0).toString());
        assertTrue("Sentence Layer exists in TCF file", instance.getTextCorpusStored().getSentencesLayer() != null);
        System.out.println(instance.getTextCorpusStored().getSentencesLayer().getSentence(0).toString());
    }
    
    /**
     * Test of toPos method, of class ConvertToTCFAnnotations.
     */
    @Test
    public void testPosLayerTCFtoLifConverter() throws Exception {

        inputFile = new File(classLoader.getResource(POS_EXAMPLE2).getFile());
        targetStream = FileUtils.openInputStream(inputFile);
        givenLifFormat = new LifProfiler(FileUtils.openInputStream(inputFile));
        ConverterTool Convertertool = new ConverterTool();
        Convertertool.convertFormat(givenLifFormat, targetStream);
        instance = Convertertool.getConvertedDataModel();

        Assert.assertTrue("input file has json extension", inputFile.getName().contains(FILE_LIF));
        Assert.assertTrue("input lif file is valid", givenLifFormat.isValid());
        assertTrue("PosTag Layer exists in TCF file", instance.getTextCorpusStored().getPosTagsLayer() != null);
        assertEquals("NNP [t_0]", instance.getTextCorpusStored().getPosTagsLayer().getTag(0).toString());
        //assertEquals("[t_0, t_1, t_2, t_3, t_4, t_5]", instance.getTextCorpusStored().getSentencesLayer().getSentence(0).toString());
    }

 }