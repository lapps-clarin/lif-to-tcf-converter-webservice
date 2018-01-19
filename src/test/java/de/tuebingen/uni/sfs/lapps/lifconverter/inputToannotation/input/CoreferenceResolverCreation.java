/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.inputToannotation.input;

import de.tuebingen.uni.sfs.lapps.core.layer.impl.AnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.constants.LifConnstant;
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
 * @author Mohammad Fazleh Elahi
 */
public class CoreferenceResolverCreation {

    private String text = "Karen flew to New York City. She is visiting her cousins.\n";
    private String pennTree = "(ROOT [40.118]\n (S [39.965]\n (NP [12.050] (NNP Karen))\n (VP [26.780] (VBD flew)\n (PP [14.690] (TO to)\n (NP [12.219] (NNP New) (NNP York))))\n (. .)))\n";
    private String[] words =  {"Karen"   ,"flew"     ,"to"      ,"New"    ,"City"   ,"."       ,"She"     ,"is"     ,"visiting" ,"her"     ,"cousins" ,"."};
    private String[] pos =    {"NNP"     ,"VBD"      ,"TO"      ,"NNP"    ,"NNP"    ,"."       ,"PRP"     ,"VBZ"    ,"VBG"      ,"PRP$"    ,"NNS"     ,"."};
    private String[] tokIds = {"tk_0_0"  ,"tk_0_1"   ,"tk_0_2"  ,"tk_0_3" ,"tk_0_4" ,"tk_0_5"  ,"tk_1_0"  ,"tk_1_1" ,"tk_1_2"   ,"tk_1_3"  ,"tk_1_4"  ,"tk_1_5"};
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

    public CoreferenceResolverCreation() {
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
        map.put(LifConnstant.LIF.DiscriminitorsExtended.PENN_TREE, pennTree);
        map.put(LifConnstant.LIF.DiscriminitorsExtended.CONSTITUENTS, constitunetList);
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
    
    /*{
                "metadata": {
                    "contains": {
                        "http://vocab.lappsgrid.org/Token": {
                            "producer": "edu.brandeis.cs.lappsgrid.stanford.corenlp.Coreference:2.0.4",
                            "type": "tokenizer:stanford"
                        },
                        "http://vocab.lappsgrid.org/Coreference": {
                            "producer": "edu.brandeis.cs.lappsgrid.stanford.corenlp.Coreference:2.0.4",
                            "type": "coreference:stanford"
                        },
                        "http://vocab.lappsgrid.org/Markable": {
                            "producer": "edu.brandeis.cs.lappsgrid.stanford.corenlp.Coreference:2.0.4",
                            "type": "markable:stanford"
                        }
                    }
                },
                "annotations": [
                    {
                        "id": "tk_0_0",
                        "start": 0,
                        "end": 5,
                        "@type": "http://vocab.lappsgrid.org/Token",
                        "features": {
                            "word": "Karen",
                            "pos": "NNP"
                        }
                    },
                    {
                        "id": "tk_0_1",
                        "start": 6,
                        "end": 10,
                        "@type": "http://vocab.lappsgrid.org/Token",
                        "features": {
                            "word": "flew",
                            "pos": "VBD"
                        }
                    },
                    {
                        "id": "tk_0_2",
                        "start": 11,
                        "end": 13,
                        "@type": "http://vocab.lappsgrid.org/Token",
                        "features": {
                            "word": "to",
                            "pos": "TO"
                        }
                    },
                    {
                        "id": "tk_0_3",
                        "start": 14,
                        "end": 17,
                        "@type": "http://vocab.lappsgrid.org/Token",
                        "features": {
                            "word": "New",
                            "pos": "NNP"
                        }
                    },
                    {
                        "id": "tk_0_4",
                        "start": 18,
                        "end": 22,
                        "@type": "http://vocab.lappsgrid.org/Token",
                        "features": {
                            "word": "York",
                            "pos": "NNP"
                        }
                    },
                    {
                        "id": "tk_0_5",
                        "start": 23,
                        "end": 27,
                        "@type": "http://vocab.lappsgrid.org/Token",
                        "features": {
                            "word": "City",
                            "pos": "NNP"
                        }
                    },
                    {
                        "id": "tk_0_6",
                        "start": 27,
                        "end": 28,
                        "@type": "http://vocab.lappsgrid.org/Token",
                        "features": {
                            "word": ".",
                            "pos": "."
                        }
                    },
                    {
                        "id": "tk_1_0",
                        "start": 29,
                        "end": 32,
                        "@type": "http://vocab.lappsgrid.org/Token",
                        "features": {
                            "word": "She",
                            "pos": "PRP"
                        }
                    },
                    {
                        "id": "tk_1_1",
                        "start": 33,
                        "end": 35,
                        "@type": "http://vocab.lappsgrid.org/Token",
                        "features": {
                            "word": "is",
                            "pos": "VBZ"
                        }
                    },
                    {
                        "id": "tk_1_2",
                        "start": 36,
                        "end": 44,
                        "@type": "http://vocab.lappsgrid.org/Token",
                        "features": {
                            "word": "visiting",
                            "pos": "VBG"
                        }
                    },
                    {
                        "id": "tk_1_3",
                        "start": 45,
                        "end": 48,
                        "@type": "http://vocab.lappsgrid.org/Token",
                        "features": {
                            "word": "her",
                            "pos": "PRP$"
                        }
                    },
                    {
                        "id": "tk_1_4",
                        "start": 49,
                        "end": 56,
                        "@type": "http://vocab.lappsgrid.org/Token",
                        "features": {
                            "word": "cousins",
                            "pos": "NNS"
                        }
                    },
                    {
                        "id": "tk_1_5",
                        "start": 56,
                        "end": 57,
                        "@type": "http://vocab.lappsgrid.org/Token",
                        "features": {
                            "word": ".",
                            "pos": "."
                        }
                    },
                    {
                        "id": "m_1",
                        "start": 0,
                        "end": 5,
                        "@type": "http://vocab.lappsgrid.org/Markable",
                        "features": {
                            "words": "Karen",
                            "sentenceIndex": "0",
                            "targets": [
                                "tk_0_0"
                            ]
                        }
                    },
                    {
                        "id": "m_3",
                        "start": 29,
                        "end": 32,
                        "@type": "http://vocab.lappsgrid.org/Markable",
                        "features": {
                            "words": "She",
                            "sentenceIndex": "1",
                            "targets": [
                                "tk_1_0"
                            ]
                        }
                    },
                    {
                        "id": "m_5",
                        "start": 45,
                        "end": 48,
                        "@type": "http://vocab.lappsgrid.org/Markable",
                        "features": {
                            "words": "her",
                            "sentenceIndex": "1",
                            "targets": [
                                "tk_1_3"
                            ]
                        }
                    },
                    {
                        "id": "coref_1",
                        "@type": "http://vocab.lappsgrid.org/Coreference",
                        "features": {
                            "representative": "m_1",
                            "mentions": [
                                "m_1",
                                "m_3",
                                "m_5"
                            ]
                        }
                    }
                ]
            }
        ]
    }
}*/

}
