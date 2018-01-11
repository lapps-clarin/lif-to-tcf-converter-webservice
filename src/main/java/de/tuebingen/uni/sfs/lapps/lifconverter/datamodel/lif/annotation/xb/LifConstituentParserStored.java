/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.xb;

import de.tuebingen.uni.sfs.lapps.library.annotation.xb.AnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.library.constants.LifVocabularies;
import de.tuebingen.uni.sfs.lapps.library.exception.LifException;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.api.LifConstituentParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import org.lappsgrid.discriminator.Discriminators;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.api.LifParseAnnotationProcessing;

/**
 *
 * @author felahi
 */
public class LifConstituentParserStored implements LifConstituentParser, LifParseAnnotationProcessing {

    private Map<Long, List<LifConstituent>> constituentParses = new TreeMap<Long, List<LifConstituent>>();
    private List<AnnotationInterpreter> tokenList = new ArrayList<AnnotationInterpreter>();
    private Map<String, Long> tokenIdStartIdMapper = new HashMap<String, Long>();
    private List<LifConstituent> constituents = new ArrayList<LifConstituent>();
    private Map<String, AnnotationInterpreter> constituentAnnotations = new HashMap<String, AnnotationInterpreter>();
    private Map<String, AnnotationInterpreter> phraseAnnotations = new HashMap<String, AnnotationInterpreter>();

    public LifConstituentParserStored(List<AnnotationInterpreter> lifAnnotations) throws LifException {
        try {
            if (seperateUnitsofParseStruectures(lifAnnotations)) {
                extractParses();
            }

        } catch (LifException exp) {
            throw new LifException(exp.getMessage());
        }
    }

    public boolean seperateUnitsofParseStruectures(List<AnnotationInterpreter> lifAnnotationList) throws LifException {
        boolean tokenlayerFlag = false, constituentFlag = false, phraseStructureFlag = false;
        for (AnnotationInterpreter annotationObject : lifAnnotationList) {
            if (annotationObject.getUrl().equals(Discriminators.Uri.TOKEN)) {
                tokenlayerFlag = true;
                this.tokenList.add(annotationObject);
                this.tokenIdStartIdMapper.put(annotationObject.getId(), annotationObject.getStart());
            }
            if (annotationObject.getUrl().equals(Discriminators.Uri.CONSTITUENT)) {
                constituentFlag = true;
                constituentAnnotations.put(annotationObject.getId(), annotationObject);
            }
            if (annotationObject.getUrl().equals(Discriminators.Uri.PHRASE_STRUCTURE)) {
                phraseStructureFlag = true;
                phraseAnnotations.put(annotationObject.getId(), annotationObject);
            }
        }
        if (!tokenlayerFlag) {
            throw new LifException("No token annotations found inside the view of constituent parser!!");
        } else if (!constituentFlag) {
            throw new LifException("No constituent annotations found inside the view of constituent parser!!");
        } else if (!phraseStructureFlag) {
            throw new LifException("No phrase structure annotations found inside the view of constituent parser!!");
        }
        return true;
    }

    public void extractParses() throws LifException {
        Long parseIndex = new Long(0);
        for (String id : phraseAnnotations.keySet()) {
            parseIndex = parseIndex + 1;
            AnnotationInterpreter annotationObject = phraseAnnotations.get(id);
            this.extractStructures(annotationObject.getFeatures());
            this.constituentParses.put(parseIndex, constituents);
        }
    }

    public void extractStructures(Map<Object, Object> constituentStructureFeatures) throws LifException {
        constituents = new ArrayList<LifConstituent>();

        if (!constituentStructureFeatures.isEmpty()) {
            try {
                List<String> constituents = new LifConstituentStructure(constituentStructureFeatures).getConstituents();
                extractConstituents(constituents);
            } catch (NullPointerException exp) {
                throw new LifException(exp.getMessage());
            }
        } else {
            throw new LifException("the list of " + Discriminators.Uri.CONSTITUENT + " of this" + Discriminators.Uri.PHRASE_STRUCTURE + " is empty");
        }
    }

    private void extractConstituents(List<String> constituents) throws LifException {
        for (String constituentId : constituents) {
            try {
                if (constituentAnnotations.containsKey(constituentId)) {
                    this.constituents.add(new LifConstituent(constituentAnnotations.get(constituentId)));
                } else {
                    throw new LifException("the annotation for the " + Discriminators.Uri.CONSTITUENT + "(" + constituentId + ") is not found");
                }
            } catch (NullPointerException exp) {
                throw new LifException(exp.getMessage());
            }
        }

    }

    public Vector<Long> getParseIndexs() {
        Vector<Long> parseIndexsSort = new Vector<Long>(this.constituentParses.keySet());
        Collections.sort(parseIndexsSort);
        return parseIndexsSort;
    }

    public LifConstituent getRoot(Long parseIndex) throws NullPointerException {
        boolean flag = false;
        List<LifConstituent> constituentLifConstituents = this.constituentParses.get(parseIndex);

        for (LifConstituent lifConstituent : constituentLifConstituents) {
            if (lifConstituent.getCatFunction().contains(LifVocabularies.LIF.TreeSets.CONSTITUENT_ROOT)) {
                flag = true;
                return lifConstituent;
            }
        }
        if (!flag) {
            throw new NullPointerException("No root node found for the parse " + parseIndex);
        }

        return null;
    }

    public List<LifConstituent> getConstituentEntities(Long parseIndex) throws LifException {
        if (this.constituentParses.containsKey(parseIndex)) {
            return this.constituentParses.get(parseIndex);
        } else {
            throw new LifException("No Constituent list found for the tree in Parse" + parseIndex);
        }
    }

    public List<AnnotationInterpreter> getTokenList() {
        return tokenList;
    }

    public Map<String, Long> getTokenIdStartIdMapper() {
        return tokenIdStartIdMapper;
    }

    public void extractParses(List<AnnotationInterpreter> lifAnnotationList) throws LifException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void seperateStructures(AnnotationInterpreter annotationObject) throws LifException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
