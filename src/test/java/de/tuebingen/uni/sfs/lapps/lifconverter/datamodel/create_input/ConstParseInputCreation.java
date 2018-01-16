/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.create_input;

import de.tuebingen.uni.sfs.lapps.library.annotation.layer.xb.AnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.library.constants.LifVocabularies;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lappsgrid.discriminator.Discriminators;
import org.lappsgrid.serialization.lif.Annotation;
import org.lappsgrid.vocabulary.Features;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.configurations.TcfVocabularies;

/**
 *
 * @author felahi
 */
public class ConstParseInputCreation {

    private String text = "Karen flew to New York.";
    private String pennTree = "(ROOT [40.118]\n (S [39.965]\n (NP [12.050] (NNP Karen))\n (VP [26.780] (VBD flew)\n (PP [14.690] (TO to)\n (NP [12.219] (NNP New) (NNP York))))\n (. .)))\n";
    private String[] words = {"Karen", "flew", "to", "New", "York", "."};
    private String[] pos = {"NNP", "VBD", "TO", "NNP", "NNP", "."};
    private String[] tokIds = {"tk_0_0", "tk_0_1", "tk_0_2", "tk_0_3", "tk_0_4", "tk_0_5"};
    private Long[] tokStartId = new Long[]{new Long(0), new Long(6), new Long(11), new Long(14), new Long(18), new Long(22)};
    private Long[] tokEndId = new Long[]{new Long(5), new Long(10), new Long(13), new Long(17), new Long(22), new Long(23)};

    private String urlTokenType = Discriminators.Uri.TOKEN;
    private String labelToken = "Token";

    private String urlConstituentType = Discriminators.Uri.CONSTITUENT;
    private static String[] constIds = {"c_0_0", "c_0_1", "c_0_2", "c_0_3", "c_0_4", "c_0_5", "c_0_6", "c_0_7", "c_0_8", "c_0_9", "c_0_10", "c_0_11"};
    private static String[] constIdLabels = {"ROOT", "S", "NP", "VP", ".", "NNP", "VBD", "PP", "TO", "NP", "NNP", "NNP"};
    private String urlParseStructureType = Discriminators.Uri.PHRASE_STRUCTURE;

    private static Map<String, List<String>> constChild = new HashMap<String, List<String>>();
    private List<AnnotationInterpreter> constitunetParseAnnotations = new ArrayList<AnnotationInterpreter>();

    static {
        List<String> childList = new ArrayList<String>();

        childList = new ArrayList<String>();
        childList.add("c_0_1");
        constChild.put(constIds[0], childList);

        childList = new ArrayList<String>();
        childList.add("c_0_2");
        childList.add("c_0_3");
        childList.add("c_0_4");
        constChild.put(constIds[1], childList);

        childList = new ArrayList<String>();
        childList.add("c_0_5");
        constChild.put(constIds[2], childList);

        childList = new ArrayList<String>();
        childList.add("c_0_6");
        childList.add("c_0_7");
        constChild.put(constIds[3], childList);

        childList = new ArrayList<String>();
        childList.add("tk_0_5");
        constChild.put(constIds[4], childList);

        childList = new ArrayList<String>();
        childList.add("tk_0_0");
        constChild.put(constIds[5], childList);

        childList = new ArrayList<String>();
        childList.add("tk_0_1");
        constChild.put(constIds[6], childList);

        childList = new ArrayList<String>();
        childList.add("c_0_8");
        childList.add("c_0_9");
        constChild.put(constIds[7], childList);

        childList = new ArrayList<String>();
        childList.add("tk_0_2");
        constChild.put(constIds[8], childList);

        childList = new ArrayList<String>();
        childList.add("c_0_10");
        childList.add("c_0_11");
        constChild.put(constIds[9], childList);

        childList = new ArrayList<String>();
        childList.add("tk_0_3");
        constChild.put(constIds[10], childList);

        childList = new ArrayList<String>();
        childList.add("tk_0_4");
        constChild.put(constIds[11], childList);
    }

    public ConstParseInputCreation() {
        this.setConstituentParseAnnotations();
    }

    private void setConstituentParseAnnotations() {
        List<Annotation> tokenAnnotations = this.setTokenAnnotationList();
        List<Annotation> constituentAnnotations = this.setConstituentAnnotationList();
        List<Annotation> parseAnnotations = this.setConstituentParseAnnotationList();
        parseAnnotations.addAll(tokenAnnotations);
        parseAnnotations.addAll(constituentAnnotations);

        for (Annotation annotation : parseAnnotations) {
            AnnotationInterpreter annotationInterpreter = new AnnotationInterpreter(annotation);
            constitunetParseAnnotations.add(annotationInterpreter);
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

    private List<Annotation> setConstituentAnnotationList() {
        List<Annotation> annotations = new ArrayList<Annotation>();
        for (Integer index = 0; index < constChild.size(); index++) {
            Map<String, List<String>> map = new HashMap<String, List<String>>();
            map.put(Features.Constituent.CHILDREN, constChild.get(constIds[index]));
            annotations.add(setConstituentAnnotation(urlConstituentType, constIdLabels[index], constIds[index], new Long(-1), new Long(-1), map));
        }
        return annotations;
    }

    private List<Annotation> setConstituentParseAnnotationList() {
        List<Annotation> annotations = new ArrayList<Annotation>();
        List<String> constitunetList = Arrays.asList("c_0_0", "c_0_1", "c_0_2", "c_0_3", "c_0_4", "c_0_5", "c_0_6", "c_0_7", "c_0_8", "c_0_9", "c_0_10", "c_0_11");
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put(Discriminators.Alias.SENTENCE, text);
        map.put(LifVocabularies.LIF.DiscriminitorsExtended.PENN_TREE, pennTree);
        map.put(LifVocabularies.LIF.DiscriminitorsExtended.CONSTITUENTS, constitunetList);
        annotations.add(setTokenAnnotation(urlParseStructureType, null, "ps_0", new Long(0), new Long(23), map));
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

    private Annotation setConstituentAnnotation(String urlType, String label, String tokId, Long startId, Long endId, Map<String, List<String>> map) {
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

    public List<AnnotationInterpreter> getConstitunetParseAnnotations() {
        return constitunetParseAnnotations;
    }

}
