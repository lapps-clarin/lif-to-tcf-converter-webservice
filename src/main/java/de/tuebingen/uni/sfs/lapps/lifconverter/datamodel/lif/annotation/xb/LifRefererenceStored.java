/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.xb;

import de.tuebingen.uni.sfs.lapps.library.annotation.xb.AnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.library.exception.LifException;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.api.LifReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lappsgrid.discriminator.Discriminators;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class LifRefererenceStored implements LifReference {

    private List<AnnotationInterpreter> tokenList = new ArrayList<AnnotationInterpreter>();
    private Map<String, Long> tokenIdStartIdMapper = new HashMap<String, Long>();
    private Map<String, AnnotationInterpreter> corferenceAnnotations = new HashMap<String, AnnotationInterpreter>();
    private Map<String, AnnotationInterpreter> markableAnnotations = new HashMap<String, AnnotationInterpreter>();

    public LifRefererenceStored(List<AnnotationInterpreter> lifAnnotations) throws LifException {
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
                corferenceAnnotations.put(annotationObject.getId(), annotationObject);
            }
            if (annotationObject.getUrl().equals(Discriminators.Uri.MARKABLE)) {
                markableAnnotations.put(annotationObject.getId(), annotationObject);

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

    public Map<String, AnnotationInterpreter> getCorferenceAnnotations() {
        return corferenceAnnotations;
    }

    public Map<String, AnnotationInterpreter> getMarkableAnnotations() {
        return markableAnnotations;
    }

}
