/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.inputToannotation.input;

import de.tuebingen.uni.sfs.lapps.utils.AnnotationInterpreter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lappsgrid.serialization.lif.Annotation;

/**
 *
 * @author felahi
 */
public class NameEntityInputCreation {

    private String[] words = {"Karen", "New", "York"};
    private String[] tokId = {"ne_0", "ne_1", "ne_2"};
    private String[] nameEntityUrls = {"http://vocab.lappsgrid.org/Person",
        "http://vocab.lappsgrid.org/Location",
        "http://vocab.lappsgrid.org/Location"};
    private Long[] startId = new Long[]{new Long(0), new Long(14), new Long(18)};
    private Long[] endId = new Long[]{new Long(5), new Long(17), new Long(22)};
    private List<AnnotationInterpreter> nameEntityAnnotations = new ArrayList<AnnotationInterpreter>();

    public NameEntityInputCreation() {
        this.setNameEntityAnnotations();
    }

    private List<AnnotationInterpreter> setNameEntityAnnotations() {
        List<Annotation> annotations = this.setNameEntityAnnotationList();
        for (Annotation annotation : annotations) {
            AnnotationInterpreter annotationInterpreter = new AnnotationInterpreter(annotation);
            nameEntityAnnotations.add(annotationInterpreter);
        }
        return nameEntityAnnotations;
    }

    private List<Annotation> setNameEntityAnnotationList() {
        List<Annotation> annotations = new ArrayList<Annotation>();
        for (Integer index = 0; index < words.length; index++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("word", words[index]);
            annotations.add(setNameEntityAnnotation(nameEntityUrls[index], tokId[index], startId[index], endId[index], map));
        }
        return annotations;
    }

    private Annotation setNameEntityAnnotation(String urlType, String tokId, Long startId, Long endId, Map<String, String> map) {
        Annotation annotation = new Annotation();
        annotation.setId(tokId);
        annotation.setStart(new Long(startId));
        annotation.setEnd(new Long(endId));
        annotation.setAtType(urlType);
        annotation.setFeatures(map);
        return annotation;
    }

    public List<AnnotationInterpreter> getNameEntityAnnotations() {
        return nameEntityAnnotations;
    }
}
