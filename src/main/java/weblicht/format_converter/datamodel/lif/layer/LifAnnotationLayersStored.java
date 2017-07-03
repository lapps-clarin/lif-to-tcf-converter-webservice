/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weblicht.format_converter.datamodel.lif.layer;

import java.util.ArrayList;
import java.util.HashMap;
import weblicht.format_converter.datamodel.conversion.AnnotationLayerFinder;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.lappsgrid.discriminator.Discriminators;
import org.lappsgrid.serialization.lif.Annotation;
import org.lappsgrid.vocabulary.Metadata;
import weblicht.format_converter.datamodel.exceptions.LifException;
import weblicht.format_converter.datamodel.exceptions.LifValidityCheck;
import weblicht.format_converter.datamodel.exceptions.VocabularyMappingException;
import weblicht.format_converter.datamodel.lif.annotation.xb.LifAnnotationInterpreter;
import weblicht.format_converter.datamodel.utils.DuplicateChecker;

/**
 *
 * @author felahi the function
 */
public class LifAnnotationLayersStored implements AnnotationLayerFinder {

    private Set<String> metadataInfoInLayers = new HashSet<String>();
    private Set<String> annotationInfoInLayers = new HashSet<String>();
    private List<LifAnnotationInterpreter> lifAnnotations = new ArrayList<LifAnnotationInterpreter>();

    private Map metadataMap = null;
    public String lifLayer = null;
    private String producer = null;
    private String tool = null;

    public LifAnnotationLayersStored(Map metadataMap) throws LifException {
        this.metadataMap = metadataMap;
        this.processUrls();
    }

    private void processUrls() {
        for (Object key : metadataMap.keySet()) {
            Map innerMap = (Map) metadataMap.get(key);
            for (Object attribute : innerMap.keySet()) {
                String url = attribute.toString();
                metadataInfoInLayers.add(url);
                Map<String, String> metaDataInformation = (Map<String, String>) innerMap.get(attribute);
                this.findTool(metaDataInformation);
            }
        }
        if (metadataInfoInLayers.size() == 1) {
            setLayerFromSingleUrl();
        } else if (metadataInfoInLayers.size() > 1) {
            setLayerFromMultipleUrls();
        }
    }

    private void findTool(Map<String, String> metaDataInformation) {
        for (String toolKey : metaDataInformation.keySet()) {
            String toolValue = metaDataInformation.get(toolKey);
            if (toolKey.contains(Metadata.Contains.PRODUCER)) {
                producer = toolValue;
            } else if (toolKey.contains(Metadata.Contains.TYPE)) {
                tool = toolValue;
            }

        }
    }

    public List<LifAnnotationInterpreter> processAnnotations(List<Annotation> annotations) throws LifException {
        LifAnnotationInterpreter.elementIdMapper = new HashMap<String, LifAnnotationInterpreter>();
        List<LifAnnotationInterpreter> annotationInterpreterList = new ArrayList<LifAnnotationInterpreter>();
        DuplicateChecker duplicateChecker = new DuplicateChecker();

        for (Annotation annotation : annotations) {
            //if (!duplicateChecker.isDuplicate(annotation.getStart())) {
            LifAnnotationInterpreter charOffsetLifObject = new LifAnnotationInterpreter(annotation);
            annotationInterpreterList.add(charOffsetLifObject);
            annotationInfoInLayers.add(annotation.getAtType());
            //}
        }

        //temporary closed...
        //lifValidityCheck.isMetadataLayerValid(lifLayer,annotationTypes);
        return annotationInterpreterList;
    }

    private void setLayerFromSingleUrl() {
        this.lifLayer = metadataInfoInLayers.iterator().next();
    }

    private void setLayerFromMultipleUrls() {
        for (String url : metadataInfoInLayers) {
            if (url.equalsIgnoreCase(Discriminators.Uri.DEPENDENCY_STRUCTURE)) {
                this.lifLayer = Discriminators.Uri.DEPENDENCY_STRUCTURE;
            } else if (url.equalsIgnoreCase(Discriminators.Uri.PHRASE_STRUCTURE)) {
                this.lifLayer = Discriminators.Uri.PHRASE_STRUCTURE;
            }
        }
    }

    public boolean isLayerExists() {
        if (this.lifLayer != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLayerValid() throws LifException {
        LifValidityCheck lifValidityCheck = new LifValidityCheck();
        return lifValidityCheck.isMetadataLayerValid(this.lifLayer, this.metadataInfoInLayers, this.annotationInfoInLayers);
    }

    public boolean isToolExists(String tool) throws VocabularyMappingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getLayer() {
        return this.lifLayer;
    }

    public String getTool(String tool) throws VocabularyMappingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getTagSetName(String tool) throws VocabularyMappingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getVocabularies(String tool, String key) throws VocabularyMappingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getTool() throws VocabularyMappingException {
        if (tool != null) {
            return tool;
        } else {
            throw new VocabularyMappingException("LIF tool field is not found");
        }
    }

    public List<LifAnnotationInterpreter> getLifAnnotations() {
        return lifAnnotations;
    }

    public String getProducer() throws VocabularyMappingException {
        if (producer != null) {
            return producer;
        } else {
            throw new VocabularyMappingException("LIF producer field is not found");
        }
    }
}
