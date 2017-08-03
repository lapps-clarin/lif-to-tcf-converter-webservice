/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.create_input;

import de.tuebingen.uni.sfs.lapps.library.annotation.AnnotationInterpreter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lappsgrid.discriminator.Discriminators;
import org.lappsgrid.serialization.lif.Annotation;
import org.lappsgrid.vocabulary.Features;

/**
 *
 * @author felahi
 */
public class DependencyParseInputCreation {

    private String text = "Karen flew to New York.";
    private String labelToken = "Token";
    private String[] words = {"Karen", "flew", "to", "New", "York", "."};
    private String[] pos = {"NNP", "VBD", "TO", "NNP", "NNP", "."};
    private String[] tokIds = {"tk_0_0", "tk_0_1", "tk_0_2", "tk_0_3", "tk_0_4", "tk_0_5"};
    private Long[] tokStartId = new Long[]{new Long(0), new Long(6), new Long(11), new Long(14), new Long(18), new Long(22)};
    private Long[] tokEndId = new Long[]{new Long(5), new Long(10), new Long(13), new Long(17), new Long(22), new Long(23)};

    private String urlDependencyStructureType = Discriminators.Uri.DEPENDENCY_STRUCTURE;
    private String urlDependencyType = Discriminators.Uri.DEPENDENCY;
    private String urlTokenType = Discriminators.Uri.TOKEN;

    private static String[] depIds = {"dep_0_0", "dep_0_1", "dep_0_2", "dep_0_3"};
    private static String[] depIdLabels = {"nn", "pobj", "nsubj", "prep"};

    private static Map<String, Map<String, String>> depFeatures = new HashMap<String, Map<String, String>>();
    private List<AnnotationInterpreter> dependencyParseAnnotations = new ArrayList<AnnotationInterpreter>();

    static {
        Map<String, String> features = new HashMap<String, String>();

        features = new HashMap<String, String>();
        features.put(Features.Dependency.GOVERNOR, "tk_0_4");
        features.put(Features.Dependency.DEPENDENT, "tk_0_3");
        depFeatures.put(depIds[0], features);

        features = new HashMap<String, String>();
        features.put(Features.Dependency.GOVERNOR, "tk_0_2");
        features.put(Features.Dependency.DEPENDENT, "tk_0_4");
        depFeatures.put(depIds[1], features);

        features = new HashMap<String, String>();
        features.put(Features.Dependency.GOVERNOR, "tk_0_1");
        features.put(Features.Dependency.DEPENDENT, "tk_0_0");
        depFeatures.put(depIds[2], features);

        features = new HashMap<String, String>();
        features.put(Features.Dependency.GOVERNOR, "tk_0_1");
        features.put(Features.Dependency.DEPENDENT, "tk_0_2");
        depFeatures.put(depIds[3], features);
    }

    public DependencyParseInputCreation() {
        this.setDependencyParseAnnotations();
    }

    private void setDependencyParseAnnotations() {
        List<Annotation> tokenAnnotations = this.setTokenAnnotationList();
        List<Annotation> dependencyAnnotations = this.setDependencyAnnotationList();
        List<Annotation> parseAnnotations = this.setDepenndencyParseAnnotationList();
        parseAnnotations.addAll(tokenAnnotations);
        parseAnnotations.addAll(dependencyAnnotations);

        for (Annotation annotation : parseAnnotations) {
            AnnotationInterpreter annotationInterpreter = new AnnotationInterpreter(annotation);
            dependencyParseAnnotations.add(annotationInterpreter);
        }
    }

    private List<Annotation> setTokenAnnotationList() {
        List<Annotation> annotations = new ArrayList<Annotation>();
        for (Integer index = 0; index < words.length; index++) {
            Map<Object, Object> map = new HashMap<Object, Object>();
            map.put(Features.Token.WORD, words[index]);
            map.put(Features.Token.POS, pos[index]);
            annotations.add(setTokenAnnotation(urlTokenType, labelToken, tokIds[index], tokStartId[index], tokEndId[index], map));
        }
        return annotations;
    }

    private List<Annotation> setDependencyAnnotationList() {
        List<Annotation> annotations = new ArrayList<Annotation>();
        for (Integer index = 0; index < depIds.length; index++) {
            Map<String, String> map = new HashMap<String, String>();
            map = depFeatures.get(depIds[index]);
            annotations.add(setDependencyAnnotation(urlDependencyType, depIdLabels[index], depIds[index], new Long(-1), new Long(-1), map));
        }
        return annotations;
    }

    private List<Annotation> setDepenndencyParseAnnotationList() {
        List<Annotation> annotations = new ArrayList<Annotation>();
        List<String> dependencyList = Arrays.asList("dep_0_0", "dep_0_1", "dep_0_2", "dep_0_3");
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put(Discriminators.Alias.SENTENCE, text);
        map.put("dependencies", dependencyList);
        annotations.add(setTokenAnnotation(urlDependencyStructureType, null, "ds_0", new Long(0), new Long(23), map));
        return annotations;
    }

    private Annotation setTokenAnnotation(String urlType, String label, String tokId, Long startId, Long endId, Map<Object, Object> map) {
        Annotation annotation = new Annotation();
        annotation.setId(tokId);
        annotation.setStart(new Long(startId));
        annotation.setEnd(new Long(endId));
        annotation.setAtType(urlType);
        if (label != null) {
            annotation.setLabel(label);
        }
        annotation.setFeatures(map);
        return annotation;
    }

    private Annotation setDependencyAnnotation(String urlType, String label, String tokId, Long startId, Long endId, Map<String, String> map) {
        Annotation annotation = new Annotation();
        annotation.setId(tokId);
        annotation.setStart(new Long(startId));
        annotation.setEnd(new Long(endId));
        annotation.setAtType(urlType);
        if (label != null) {
            annotation.setLabel(label);
        }
        annotation.setFeatures(map);
        return annotation;
    }

    public List<AnnotationInterpreter> getDependencyParseAnnotations() {
        return dependencyParseAnnotations;
    }

}
