/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.create_input;

import de.tuebingen.uni.sfs.lapps.library.layer.xb.AnnotationInterpreter;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lappsgrid.serialization.lif.Annotation;

/**
 *
 * @author felahi
 */
public class TokenInputCreation {

    private String[] words = {"Karen", "flew", "to", "New", "York", "."};
    private String[] pos = {"NNP", "VBD", "TO", "NNP", "NNP", "."};
    private String[] lemmas = {"Karen", "flew", "to", "New", "York", "."};
    private String[] tokId = {"tok0", "tok1", "tok2", "tok3", "tok4", "tok5"};
    private String urlType = "http://vocab.lappsgrid.org/Token";
    private String label = "Token";
    private Long[] startId = new Long[] {new Long(0),new Long(6),new Long(11),new Long(14),new Long(18),new Long(22)};
    private Long[] endId   = new Long[] {new Long(5),new Long(10),new Long(13),new Long(17),new Long(22),new Long(23)};
    private List<AnnotationInterpreter> tokenAnnotations = new ArrayList<AnnotationInterpreter>();

    public TokenInputCreation() {
        this.setTokenAnnotations();
    }

    private List<AnnotationInterpreter> setTokenAnnotations() {
        //List<AnnotationInterpreter> tokenAnnotations = new ArrayList<AnnotationInterpreter>();
        List<Annotation> annotations = this.setTokenAnnotationList();
        for (Annotation annotation : annotations) {
            AnnotationInterpreter annotationInterpreter = new AnnotationInterpreter(annotation);
            tokenAnnotations.add(annotationInterpreter);
        }
        return tokenAnnotations;
    }

    private List<Annotation> setTokenAnnotationList() {
        List<Annotation> annotations = new ArrayList<Annotation>();
        for (Integer index = 0; index < words.length; index++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("word", words[index]);
            map.put("pos", pos[index]);
            map.put("lemma", lemmas[index]);
            annotations.add(setTokenAnnotation(urlType, label,tokId[index],startId[index],endId[index],map));
        }
        return annotations;
    }

    private Annotation setTokenAnnotation(String urlType, String label,String tokId, Long startId, Long endId,Map<String, String> map) {
        Annotation annotation = new Annotation();
        annotation.setId(tokId);
        annotation.setStart(new Long(startId));
        annotation.setEnd(new Long(endId));
        annotation.setAtType(urlType);
        annotation.setLabel(label);
        annotation.setFeatures(map);
        return annotation;
    }

    public List<AnnotationInterpreter> getTokenAnnotations() {
        return tokenAnnotations;
    }

}
