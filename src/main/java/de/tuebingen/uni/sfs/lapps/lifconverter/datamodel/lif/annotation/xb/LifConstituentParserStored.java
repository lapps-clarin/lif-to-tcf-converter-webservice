/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.xb;

import de.tuebingen.uni.sfs.lapps.library.annotation.LifAnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.api.LifConstituentParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import org.lappsgrid.discriminator.Discriminators;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.configurations.Vocabularies;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.api.LifParseAnnotationProcessing;

/**
 *
 * @author felahi
 */
public class LifConstituentParserStored implements LifConstituentParser, LifParseAnnotationProcessing {

    private Map<Long, List<LifConstituent>> constituentParses = new TreeMap<Long, List<LifConstituent>>();
    private List<LifAnnotationInterpreter> tokenList = new ArrayList<LifAnnotationInterpreter>();
    private Map<String, Long> tokenIdStartIdMapper = new HashMap<String, Long>();
    private List<LifConstituent> constituents = new ArrayList<LifConstituent>();
    private Map<String, LifAnnotationInterpreter> constituentAnnotations = new HashMap<String, LifAnnotationInterpreter>();
    private Map<String, LifAnnotationInterpreter> phraseAnnotations = new HashMap<String, LifAnnotationInterpreter>();

    public LifConstituentParserStored(List<LifAnnotationInterpreter> lifAnnotations) throws ConversionException {
        try {
            if (seperateUnitsofParseStruectures(lifAnnotations)) {
                extractParses();
            }

        } catch (ConversionException exp) {
            throw new ConversionException(exp.getMessage());
        }
    }

    public boolean seperateUnitsofParseStruectures(List<LifAnnotationInterpreter> lifAnnotationList) throws ConversionException {
        boolean tokenlayerFlag = false, constituentFlag = false, phraseStructureFlag = false;
        for (LifAnnotationInterpreter annotationObject : lifAnnotationList) {
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
            throw new ConversionException("No token annotations found inside the view of constituent parser!!");
        } else if (!constituentFlag) {
            throw new ConversionException("No constituent annotations found inside the view of constituent parser!!");
        } else if (!phraseStructureFlag) {
            throw new ConversionException("No phrase structure annotations found inside the view of constituent parser!!");
        }
        return true;
    }

    public void extractParses() throws ConversionException {
        Long parseIndex = new Long(0);
        for (String id : phraseAnnotations.keySet()) {
            parseIndex = parseIndex + 1;
            LifAnnotationInterpreter annotationObject = phraseAnnotations.get(id);
            this.extractStructures(annotationObject.getFeatures());
            this.constituentParses.put(parseIndex, constituents);
        }
    }

    public void extractStructures(Map<Object, Object> constituentStructureFeatures) throws ConversionException {
        constituents = new ArrayList<LifConstituent>();

        if (!constituentStructureFeatures.isEmpty()) {
            try {
                List<String> constituents = new LifConstituentStructure(constituentStructureFeatures).getConstituents();
                extractConstituents(constituents);
            } catch (NullPointerException exp) {
                throw new ConversionException(exp.getMessage());
            }
        } else {
            throw new ConversionException("the list of " + Discriminators.Uri.CONSTITUENT + " of this" + Discriminators.Uri.PHRASE_STRUCTURE + " is empty");
        }
    }

    private void extractConstituents(List<String> constituents) throws ConversionException {
        for (String constituentId : constituents) {
            try {
                if (constituentAnnotations.containsKey(constituentId)) {
                    this.constituents.add(new LifConstituent(constituentAnnotations.get(constituentId)));
                } else {
                    throw new ConversionException("the annotation for the " + Discriminators.Uri.CONSTITUENT + "(" + constituentId + ") is not found");
                }
            } catch (NullPointerException exp) {
                throw new ConversionException(exp.getMessage());
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
            if (lifConstituent.getCatFunction().contains(Vocabularies.LIF.TreeSets.CONSTITUENT_ROOT)) {
                flag = true;
                return lifConstituent;
            }
        }
        if (!flag) {
            throw new NullPointerException("No root node found for the parse " + parseIndex);
        }

        return null;
    }

    public List<LifConstituent> getConstituentEntities(Long parseIndex) throws ConversionException {
        if (this.constituentParses.containsKey(parseIndex)) {
            return this.constituentParses.get(parseIndex);
        } else {
            throw new ConversionException("No Constituent list found for the tree in Parse" + parseIndex);
        }
    }

    public List<LifAnnotationInterpreter> getTokenList() {
        return tokenList;
    }

    public Map<String, Long> getTokenIdStartIdMapper() {
        return tokenIdStartIdMapper;
    }

    public void extractParses(List<LifAnnotationInterpreter> lifAnnotationList) throws ConversionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void seperateStructures(LifAnnotationInterpreter annotationObject) throws ConversionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
