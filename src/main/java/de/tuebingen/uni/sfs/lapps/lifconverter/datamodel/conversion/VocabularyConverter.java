/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.conversion;

import de.tuebingen.uni.sfs.lapps.library.annotation.api.AnnotationLayerFinder;
import de.tuebingen.uni.sfs.lapps.library.exception.VocabularyMappingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.configurations.TcfVocabularies;

/**
 *
 * @author felahi
 */
public class VocabularyConverter implements AnnotationLayerFinder {

    public static Map<String, String> layerMapper = new HashMap<String, String>();
    public static Map<String, ToolTagSetAnnoConversion> vocabularyMapper = new HashMap<String, ToolTagSetAnnoConversion>();
    public static Set<String> lifAnnotationlayers = new HashSet<String>();
    private String convertedLayer = null;
    private AnnotationLayerFinder givenLayer = null;

    public VocabularyConverter(String layerMapperfilePath, String vocabularyFilePath) throws VocabularyMappingException {
        try {
            this.setLayers(layerMapperfilePath);
            this.setVocabularies(vocabularyFilePath);
        } catch (Exception ex) {
            throw new VocabularyMappingException("Failed to set parameters from model!!");
        }
    }

    public VocabularyConverter(AnnotationLayerFinder givenLayer) throws VocabularyMappingException {
        this.givenLayer = givenLayer;
        this.toLayer();
        this.toTool();
    }

    private void setLayers(String filePath) throws Exception {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(filePath), TcfVocabularies.GeneralParameters.UNICODE));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parameters = line.split(TcfVocabularies.GeneralParameters.PARAMETER_SEPERATOR);
                String parameter = parameters[0];
                String value = parameters[1];
                layerMapper.put(parameter, value);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new ArrayIndexOutOfBoundsException("The parameters set in " + filePath + " is wrong");
        } catch (Exception ex) {
            Logger.getLogger(VocabularyConverter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(VocabularyConverter.class.getName()).log(Level.SEVERE, null, ex);
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
            reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(filePath), TcfVocabularies.GeneralParameters.UNICODE));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parameters = line.split(TcfVocabularies.GeneralParameters.PARAMETER_SEPERATOR_REG);
                lifTool = parameters[0];
                tcftagSet = parameters[1];
                lifEntity = parameters[2];
                tcfEntity = parameters[3];

                if (vocabularyMapper.containsKey(lifTool)) {
                    ToolTagSetAnnoConversion toolTagSetAnnotationConversion = vocabularyMapper.get(lifTool);
                    toolTagSetAnnotationConversion.addValues(lifEntity, tcfEntity);
                    vocabularyMapper.put(lifTool, toolTagSetAnnotationConversion);
                } else {
                    vocabularyMapper.put(lifTool, new ToolTagSetAnnoConversion(tcftagSet, lifEntity, tcfEntity));
                }

            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new ArrayIndexOutOfBoundsException("The parameters set in " + filePath + " is wrong");
        } catch (Exception ex) {
            Logger.getLogger(VocabularyConverter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(VocabularyConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void toLayer() throws VocabularyMappingException {
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

    private void toTool() throws VocabularyMappingException {
        givenLayer.getTool();
    }

    public boolean isLayerExists() throws VocabularyMappingException {
        if (layerMapper.containsKey(this.givenLayer.getLayer())) {
            return true;
        }
        return false;
    }

    public boolean isToolExists(String tool) throws VocabularyMappingException {
        if (vocabularyMapper.isEmpty()) {
            throw new VocabularyMappingException("vocabularyMapper is empty!!");
        }

        if (vocabularyMapper.containsKey(tool)) {
            return true;
        } else {
            throw new VocabularyMappingException("This tool (" + tool + ") is not registered in the model!!");
        }
    }

    public String getLayer() {
        if (convertedLayer != null) {
            return convertedLayer.toString();
        } else {
            return givenLayer.toString();
        }
    }

    public String getTool() throws VocabularyMappingException {
        if (givenLayer.getTool() != null) {
            return givenLayer.getTool();
        } else {
            throw new VocabularyMappingException("No tool is found for the tool!!");
        }
    }

    public String getProducer() throws VocabularyMappingException {
        if (givenLayer.getProducer() != null) {
            return givenLayer.getProducer();
        } else {
            throw new VocabularyMappingException("No tool producer is found for the tool!!");
        }
    }

    public String getTagSetName(String tool) throws VocabularyMappingException {
        if (isToolExists(tool)) {
            if (vocabularyMapper.get(tool).getConvertedTagSet() != null) {
                return vocabularyMapper.get(tool).getConvertedTagSet();
            } else {
                throw new VocabularyMappingException("No converted tagset found for the tool " + tool + "!!");
            }
        }
        return null;
    }

    public String getVocabularies(String tool, String key) throws VocabularyMappingException {
        if (isToolExists(tool)) {
            if (vocabularyMapper.get(tool).getConvertedVocabularies(key) != null) {
                return vocabularyMapper.get(tool).getConvertedVocabularies(key);
            } else {
                throw new VocabularyMappingException("This vacabulary for (" + key + ") is not registered in the model!!");
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

}
