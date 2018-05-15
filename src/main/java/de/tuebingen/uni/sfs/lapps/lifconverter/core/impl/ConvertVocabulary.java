/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.lapps.core.layer.impl.LIFAnnotationLayer;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.VocabularyMappingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class ConvertVocabulary  {

    public static final String UNICODE = "UTF-8";
    public static final String PARAMETER_SEPERATOR_REG = "\\=";
    public static final String PARAMETER_SEPERATOR = "=";
    public static Map<String, String> layerMapper = new HashMap<String, String>();
    public static Map<String, ConvertToolTagset> vocabularyMapper = new HashMap<String, ConvertToolTagset>();
    public static Set<String> lifAnnotationlayers = new HashSet<String>();
    private String convertedLayer = null;
    private LIFAnnotationLayer givenLayer = null;

    public ConvertVocabulary(String layerMapperfilePath, String vocabularyFilePath) throws VocabularyMappingException {
        try {
            this.setLayers(layerMapperfilePath);
            this.setVocabularies(vocabularyFilePath);
        } catch (Exception ex) {
            throw new VocabularyMappingException("Failed to set parameters from model!!");
        }
    }

    public ConvertVocabulary(LIFAnnotationLayer givenLayer)  {
        this.givenLayer = givenLayer;
        this.toLayer();
    }

    private void setLayers(String filePath) throws Exception {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(filePath), UNICODE));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parameters = line.split(PARAMETER_SEPERATOR);
                String parameter = parameters[0];
                String value = parameters[1];
                layerMapper.put(parameter, value);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new ArrayIndexOutOfBoundsException("The parameters set in " + filePath + " is wrong");
        } catch (Exception ex) {
            Logger.getLogger(ConvertVocabulary.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ConvertVocabulary.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setVocabularies(String filePath) throws Exception {
        BufferedReader reader = null;
        String lifTool = null;
        String tcftagSet = null;
        String lifEntity = null;
        String tcfEntity = null;

        try {
            reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(filePath), UNICODE));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parameters = line.split(PARAMETER_SEPERATOR_REG);
                lifTool = parameters[0];
                tcftagSet = parameters[1];
                lifEntity = parameters[2];
                tcfEntity = parameters[3];

                if (vocabularyMapper.containsKey(lifTool)) {
                    ConvertToolTagset toolTagSetAnnotationConversion = vocabularyMapper.get(lifTool);
                    toolTagSetAnnotationConversion.addValues(lifEntity, tcfEntity);
                    vocabularyMapper.put(lifTool, toolTagSetAnnotationConversion);
                } else {
                    vocabularyMapper.put(lifTool, new ConvertToolTagset(tcftagSet, lifEntity, tcfEntity));
                }

            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new ArrayIndexOutOfBoundsException("The parameters set in " + filePath + " is wrong");
        } catch (Exception ex) {
            Logger.getLogger(ConvertVocabulary.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ConvertVocabulary.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void toLayer()  {
        if (isValid()) {
            this.convertedLayer = layerMapper.get(givenLayer.getLayer());
        }
    }

    public String getLayer() {
        if (convertedLayer != null) {
            return convertedLayer.toString();
        } else {
            return givenLayer.toString();
        }
    }

    public boolean isValid() {
        return layerMapper.containsKey(givenLayer.getLayer());
    }

}
