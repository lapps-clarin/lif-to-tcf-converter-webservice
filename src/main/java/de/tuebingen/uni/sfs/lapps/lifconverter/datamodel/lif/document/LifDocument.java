/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.lappsgrid.serialization.Serializer;
import org.lappsgrid.serialization.lif.Container;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.utils.JsonProcessor;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.configurations.Vocabularies;

/**
 *
 * @author felahi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LifDocument  {

    //"payload"
    private String discriminator;

    @JsonProperty(Vocabularies.LIF.Document.PAYLOAD_KEY_JSON)
    private Container container;

    public LifDocument() {

    }

    public LifDocument(String discriminator, JsonProcessor jsonObject) {
        this.discriminator = discriminator;
        this.container = Serializer.parse(jsonObject.getJsonString(), Container.class);
    }

    /*public boolean isDocumentValid(JsonProcessor jsonObject) throws LifDocumentParseException {
        return (isNonEmptyDocument(jsonObject)
                && isDocumentStructureValid(jsonObject.getJsonMap().keySet())
                && isToplevelAnnotationValid(jsonObject));
    }

    private boolean isNonEmptyDocument(JsonProcessor jsonObject) throws LifDocumentParseException {
        if (jsonObject.getJsonMap().keySet().isEmpty()) {
            throw new LifDocumentParseException(LifValidityCheck.MESSAGE_INVALID_JSON);
        }
        return true;
    }

    public boolean isToplevelAnnotationValid(JsonProcessor jsonObject) throws LifDocumentParseException {
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
                    throw new LifDocumentParseException(LifValidityCheck.MESSAGE_INVALID_LIF_TOPLEVEL);
                }
            }

        }

        if (topLevelAnnotationCheck(topLevelAnnotationSet)) {
            return true;
        }
        return false;
    }

    private boolean isDocumentStructureValid(Set<String> annotationSet) throws LifDocumentParseException {
        if (annotationSet.contains(Vocabularies.LIF.Document.PAYLOAD_KEY_JSON)
                && annotationSet.contains(Vocabularies.LIF.Document.DISCRIMINATOR_KEY_JSON)) {
            return true;
        } else {
            throw new LifDocumentParseException(LifValidityCheck.MESSAGE_INVALID_LIF_DOCUMENT);
        }
    }

    private boolean topLevelAnnotationCheck(Set<String> annotationSet) throws LifDocumentParseException {
        if (!annotationSet.contains(Vocabularies.LIF.Document.LifDocumentTopLevel.CONTEXT_KEY_LIF)) {
            throw new LifDocumentParseException(LifValidityCheck.MESSAGE_INVALID_LIF_TOPLEVEL_CONTEXT_MISSING);
        } else if (!annotationSet.contains(Vocabularies.LIF.Document.LifDocumentTopLevel.METADATA_KEY_LIF)) {
            throw new LifDocumentParseException(LifValidityCheck.MESSAGE_INVALID_LIF_TOPLEVEL_METADATA_MISSING);
        } else if (!annotationSet.contains(Vocabularies.LIF.Document.LifDocumentTopLevel.VIEWS_KEY_LIF)) {
            throw new LifDocumentParseException(LifValidityCheck.MESSAGE_INVALID_LIF_TOPLEVEL_VIEWS_MISSING);
        } else if (!annotationSet.contains(Vocabularies.LIF.Document.LifDocumentTopLevel.TEXT_KEY_LIF)) {
            throw new LifDocumentParseException(LifValidityCheck.MESSAGE_INVALID_LIF_TOPLEVEL_TEXT_MISSING);
        } else {
            return true;
        }
    }*/

    public String getDiscriminator() {
        return discriminator;
    }

    public Container getContainer() {
        return container;
    }
}
