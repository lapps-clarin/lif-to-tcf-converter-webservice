/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import com.fasterxml.jackson.core.JsonParseException;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.impl.ConvertToolTagset;
import de.tuebingen.uni.sfs.lapps.core.layer.api.AnnotationLayerFinder;
import de.tuebingen.uni.sfs.lapps.exceptions.JsonValidityException;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.api.Constants;
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
 * @author felahi
 */
public class ConvertVocabulary implements AnnotationLayerFinder,Constants {

    public static Map<String, String> layerMapper = new HashMap<String, String>();
    public static Map<String, ConvertToolTagset> vocabularyMapper = new HashMap<String, ConvertToolTagset>();
    public static Set<String> lifAnnotationlayers = new HashSet<String>();
    private String convertedLayer = null;
    private AnnotationLayerFinder givenLayer = null;

    public ConvertVocabulary(String layerMapperfilePath, String vocabularyFilePath) throws VocabularyMappingException {
        try {
            this.setLayers(layerMapperfilePath);
            this.setVocabularies(vocabularyFilePath);
        } catch (Exception ex) {
            throw new VocabularyMappingException("Failed to set parameters from model!!");
        }
    }

    public ConvertVocabulary(AnnotationLayerFinder givenLayer) throws LifException, VocabularyMappingException {
        this.givenLayer = givenLayer;
        this.toLayer();
        this.toTool();
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

    private void toLayer() throws VocabularyMappingException, LifException {
        if (givenLayer.isLayerExists()) {
            if (layerMapper.containsKey(givenLayer.getLayer())) {
                this.convertedLayer = layerMapper.get(givenLayer.getLayer());
            } else {
                throw new VocabularyMappingException(givenLayer.getLayer() + " is not integrated yet!!");
            }
        } else {
            throw new VocabularyMappingException(givenLayer.getLayer() + " is not integrated yet!!");
        }
    }

    private void toTool() throws VocabularyMappingException, LifException {
        givenLayer.getTool();
    }

    public boolean isLayerExists() throws LifException {
        if (layerMapper.containsKey(this.givenLayer.getLayer())) {
            return true;
        }
        return false;
    }

    //this is wrong. Need to be corrected in future..
    public boolean isToolExists(String tool) throws LifException {
        if (vocabularyMapper.isEmpty()) {
            throw new LifException("vocabularyMapper is empty!!");
        }

        if (vocabularyMapper.containsKey(tool)) {
            return true;
        } else {
            throw new LifException("This tool (" + tool + ") is not registered in the model!!");
        }
    }

    public String getLayer() {
        if (convertedLayer != null) {
            return convertedLayer.toString();
        } else {
            return givenLayer.toString();
        }
    }

    public String getTool() throws LifException {
        if (givenLayer.getTool() != null) {
            return givenLayer.getTool();
        } else {
            throw new LifException("No tool is found for the tool!!");
        }
    }

    public String getProducer() throws LifException {
        if (givenLayer.getProducer() != null) {
            return givenLayer.getProducer();
        } else {
            throw new LifException("No tool producer is found for the tool!!");
        }
    }

    //this wrong it needs to be corrected in future--
    public String getTagSetName(String tool) throws LifException {
        if (isToolExists(tool)) {
            if (vocabularyMapper.get(tool).getConvertedTagSet() != null) {
                return vocabularyMapper.get(tool).getConvertedTagSet();
            } else {
                throw new LifException("No converted tagset found for the tool " + tool + "!!");
            }
        }
        return null;
    }

     //this wrong it needs to be corrected in future--
    public String getVocabularies(String tool, String key) throws LifException {
        if (isToolExists(tool)) {
            try {
                if (vocabularyMapper.get(tool).getConvertedVocabularies(key) != null) {
                    return vocabularyMapper.get(tool).getConvertedVocabularies(key);
                } else {
                    throw new LifException("This vacabulary for (" + key + ") is not registered in the model!!");
                }
            } catch (VocabularyMappingException ex) {
                 throw new LifException("This vacabulary for (" + key + ") is not registered in the model!!");
            }
        }
        return null;
    }

    public void getLayerFromSingleUrl() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void getLayerFromMultipleUrls() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Integer getLayerIndex() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isValid() throws JsonParseException, IOException, JsonValidityException, LifException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
