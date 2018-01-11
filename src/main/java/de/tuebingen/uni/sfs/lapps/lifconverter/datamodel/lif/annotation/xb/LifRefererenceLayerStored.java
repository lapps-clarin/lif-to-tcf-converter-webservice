/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.xb;

import de.tuebingen.uni.sfs.lapps.library.annotation.xb.AnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.library.exception.LifException;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.api.LifMarkable;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.api.LifReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lappsgrid.discriminator.Discriminators;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.api.LifReferenceLayer;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class LifRefererenceLayerStored implements LifReferenceLayer {

    private List<AnnotationInterpreter> tokenList = new ArrayList<AnnotationInterpreter>();
    private Map<String, Long> tokenIdStartIdMapper = new HashMap<String, Long>();
    private Map<String, LifReference> corferenceAnnotations = new HashMap<String, LifReference>();
    private Map<String, LifMarkable> markableAnnotations = new HashMap<String, LifMarkable>();

    public LifRefererenceLayerStored(List<AnnotationInterpreter> lifAnnotations) throws LifException {
        try {
            if (checkAnnotationValidity(lifAnnotations)) {
            }

        } catch (LifException exp) {
            throw new LifException(exp.getMessage());
        }
    }

    public boolean checkAnnotationValidity(List<AnnotationInterpreter> lifAnnotationList) throws LifException {

        for (AnnotationInterpreter annotationObject : lifAnnotationList) {
            if (annotationObject.getUrl().equals(Discriminators.Uri.TOKEN)) {
                this.tokenList.add(annotationObject);
                this.tokenIdStartIdMapper.put(annotationObject.getId(), annotationObject.getStart());
            }
            if (annotationObject.getUrl().equals(Discriminators.Uri.COREF)) {
                LifReference lifReference = new LifReferenceStored(annotationObject.getFeatures());
                corferenceAnnotations.put(annotationObject.getId(), lifReference);
            }
            if (annotationObject.getUrl().equals(Discriminators.Uri.MARKABLE)) {
                LifMarkable lifMarkable = new LifMarkableStored(annotationObject.getFeatures());
                markableAnnotations.put(annotationObject.getId(), lifMarkable);

            }
        }
        if (tokenList.isEmpty()) {
            throw new LifException("No token annotations found inside the view of reference layer!!");
        } else if (corferenceAnnotations.isEmpty()) {
            throw new LifException("No corference annotations found inside the view of reference parser!!");
        } else if (markableAnnotations.isEmpty()) {
            throw new LifException("No markable annotations found inside the view of reference parser!!");
        }
        return true;
    }

    public List<AnnotationInterpreter> getTokenList() {
        return tokenList;
    }

    public Map<String, LifReference> getCorferenceAnnotations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Map<String, LifMarkable> getMarkableAnnotations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
