/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weblicht.format_converter.datamodel.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.lappsgrid.discriminator.Discriminators;
import org.lappsgrid.serialization.lif.Annotation;
import org.lappsgrid.serialization.lif.View;
import weblicht.format_converter.datamodel.configurations.Vocabularies;
import weblicht.format_converter.datamodel.conversion.AnnotationLayerConverter;
import weblicht.format_converter.datamodel.conversion.AnnotationLayerFinder;
import weblicht.format_converter.datamodel.exceptions.LifException;
import weblicht.format_converter.datamodel.lif.annotation.xb.LifAnnotationInterpreter;
import weblicht.format_converter.datamodel.utils.JsonProcessor;

/**
 *
 * @author felahi
 */
public class LifValidityCheck implements ValidityCheck {

    //document validity error messages...
    public static final String MESSAGE_INVALID_JSON = "LIF ERROR: No Json key/value found!!";
    public static final String MESSAGE_INVALID_LIF_DOCUMENT = "LIF ERROR: Either discriminator or payload is missing in LIF file!!";
    public static final String MESSAGE_INVALID_LIF_TOPLEVEL = "LIF ERROR: the top level annotation (such as context,metadata,text,views) are missing!!";
    public static final String MESSAGE_INVALID_LIF_TOPLEVEL_CONTEXT_MISSING = "LIF ERROR: one of the top level annotation (context) are missing!!";
    public static final String MESSAGE_INVALID_LIF_TOPLEVEL_VIEWS_MISSING = "LIF ERROR: one of the top level annotation (view) are missing!!";
    public static final String MESSAGE_INVALID_LIF_TOPLEVEL_METADATA_MISSING = "LIF ERROR: one of the top level annotation (metadata) are missing!!";
    public static final String MESSAGE_INVALID_LIF_TOPLEVEL_TEXT_MISSING = "LIF ERROR: one of the top level annotation (text) are missing!!";
    public static final String INVALID_JSON_FILE = "LIF ERROR:INVALID JSON FILE!!";

    //annotation layers validity error messages...
    public static final String NO_ANNOTATION_FOUND = "LIF ERROR: The view is empty!!";
    public static final String NO_ANNOTATION_IN_METADATA = "The metadata defination is wrong!!";

    private JsonProcessor jsonObject = null;
    private List<View> views = new ArrayList<View>();
    private Annotation annotation = null;

    public LifValidityCheck() {

    }

    public LifValidityCheck(JsonProcessor jsonObject) {
        this.jsonObject = jsonObject;
    }

    public LifValidityCheck(Annotation annotation) {
        this.annotation = annotation;
    }

    public boolean isValid() throws LifException {
        return (isNonEmptyDocument()
                && isDocumentStructureValid()
                && isToplevelAnnotationValid());
    }

    public boolean isNonEmptyDocument() throws LifException {
        if (jsonObject.getJsonMap().keySet().isEmpty()) {
            throw new LifException(MESSAGE_INVALID_JSON);
        }
        return true;
    }

    public boolean isDocumentStructureValid() throws LifException {
        Set<String> annotationSet = jsonObject.getJsonMap().keySet();
        if (annotationSet.contains(Vocabularies.LIF.Document.PAYLOAD_KEY_JSON)
                && annotationSet.contains(Vocabularies.LIF.Document.DISCRIMINATOR_KEY_JSON)) {
            return true;
        } else {
            throw new LifException(MESSAGE_INVALID_LIF_DOCUMENT);
        }
    }

    public boolean isToplevelAnnotationValid() throws LifException {
        Set<String> topLevelAnnotationSet = new HashSet<String>();
        LinkedHashMap linkedHashMap = null;

        for (String key : jsonObject.getJsonMap().keySet()) {
            if (key.contains(Vocabularies.LIF.Document.PAYLOAD_KEY_JSON)) {
                Object payLoadContent = jsonObject.getJsonMap().get(key);
                if (payLoadContent instanceof LinkedHashMap) {
                    linkedHashMap = (LinkedHashMap) jsonObject.getJsonMap().get(key);
                    for (Object topLevelAnnoKey : linkedHashMap.keySet()) {
                        topLevelAnnotationSet.add(topLevelAnnoKey.toString());
                    }
                }
                if (payLoadContent instanceof String) {
                    throw new LifException(MESSAGE_INVALID_LIF_TOPLEVEL);
                }
            }

        }

        if (topLevelAnnotationCheck(topLevelAnnotationSet)) {
            return true;
        }
        return false;
    }

    private boolean topLevelAnnotationCheck(Set<String> annotationSet) throws LifException {
        if (!annotationSet.contains(Vocabularies.LIF.Document.LifDocumentTopLevel.CONTEXT_KEY_LIF)) {
            throw new LifException(MESSAGE_INVALID_LIF_TOPLEVEL_CONTEXT_MISSING);
        } else if (!annotationSet.contains(Vocabularies.LIF.Document.LifDocumentTopLevel.METADATA_KEY_LIF)) {
            throw new LifException(MESSAGE_INVALID_LIF_TOPLEVEL_METADATA_MISSING);
        } else if (!annotationSet.contains(Vocabularies.LIF.Document.LifDocumentTopLevel.VIEWS_KEY_LIF)) {
            throw new LifException(MESSAGE_INVALID_LIF_TOPLEVEL_VIEWS_MISSING);
        } else if (!annotationSet.contains(Vocabularies.LIF.Document.LifDocumentTopLevel.TEXT_KEY_LIF)) {
            throw new LifException(MESSAGE_INVALID_LIF_TOPLEVEL_TEXT_MISSING);
        } else {
            return true;
        }
    }

    public boolean isAnnotationLayerValid() throws LifException {
        if (this.views.isEmpty()) {
            throw new LifException(NO_ANNOTATION_FOUND);
        }
        return true;
    }

    public boolean isAnnotationValid() throws LifException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isMetadataLayerValid(String layer, Set<String> metadataInfoInLayers, Set<String> annotationInfoInLayers) throws LifException {
        if (layer.contains(Discriminators.Uri.NE)) {
            return isNamedEntityValid(annotationInfoInLayers);
        } else if (annotationInfoInLayers.contains(layer)) {
            return true;
        } else {
            throw new LifException("The metadata informatiom is wrong!!");
        }
    }

    public boolean isMetadataLayerValid(String lifLayer, Set<String> annotationTypes) throws LifException {
        if (!annotationTypes.contains(lifLayer)) {
            throw new LifException(NO_ANNOTATION_IN_METADATA);
        }
        return true;
    }

    public boolean isTextValid(String text) {
        return false;
    }

    public boolean isLanguageValid(String language) {
        return false;
    }

    public void setViews(List<View> views) {
        this.views = views;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    private boolean isNamedEntityValid(Set<String> annotationInfoInLayers) {
        for (String layer : annotationInfoInLayers) {
            if (layer.equals(Discriminators.Uri.PERSON))
                ; else if (layer.equals(Discriminators.Uri.LOCATION)) 
                 ; else if (layer.equals(Discriminators.Uri.DATE)) 
                     ; else if (layer.equals(Discriminators.Uri.ORGANIZATION)) 
                         ; else {
                return false;
            }
        }
        return true;
    }

}
