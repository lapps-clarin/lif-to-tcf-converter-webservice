/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.xb;

import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.api.LifDependencyParser;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.tcf.xb.TcfDependencyEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lappsgrid.discriminator.Discriminators;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.api.LifParseAnnotationProcessing;

/**
 *
 * @author felahi
 */
public class LifDependencyParserStored implements LifDependencyParser, LifParseAnnotationProcessing {

    private Map<Long, List<TcfDependencyEntity>> dependencyParses = new TreeMap<Long, List<TcfDependencyEntity>>();
    private List<LifAnnotationInterpreter> tokenList = new ArrayList<LifAnnotationInterpreter>();
    private List<TcfDependencyEntity> dependencyEntities = new ArrayList<TcfDependencyEntity>();

    public LifDependencyParserStored(List<LifAnnotationInterpreter> lifAnnotationList) throws ConversionException {
        seperateUnitsofParseStruectures(lifAnnotationList);
        try {
            extractParses(lifAnnotationList);
        } catch (Exception ex) {
            Logger.getLogger(LifDependencyParserStored.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void extractParses(List<LifAnnotationInterpreter> lifAnnotationList) throws ConversionException {
        Long parseIndex = new Long(0);
        for (LifAnnotationInterpreter annotationObject : lifAnnotationList) {
            if (annotationObject.getUrl().equals(Discriminators.Uri.DEPENDENCY_STRUCTURE)) {
                parseIndex = parseIndex + 1;
                try {
                    this.seperateStructures(annotationObject);
                    this.dependencyParses.put(parseIndex, dependencyEntities);
                } catch (Exception ex) {
                    Logger.getLogger(LifDependencyParserStored.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void seperateStructures(LifAnnotationInterpreter annotationObject) throws ConversionException {
        dependencyEntities = new ArrayList<TcfDependencyEntity>();
        Map<Object, Object> dependencyStructureFeatures = LifAnnotationInterpreter.elementIdMapper.get(annotationObject.getId()).getFeatures();
        if (!dependencyStructureFeatures.isEmpty()) {
            LifDependencyStructure lifDepStructureFeature = new LifDependencyStructure(dependencyStructureFeatures);
            List<String> dependencies = lifDepStructureFeature.getDependencies();
            for (String dependencyId : dependencies) {
                try {
                    if (LifAnnotationInterpreter.elementIdMapper.containsKey(dependencyId)) {
                        LifAnnotationInterpreter depAnnotationInterpreter = LifAnnotationInterpreter.elementIdMapper.get(dependencyId);
                        Map<Object, Object> dependencyFeatures = depAnnotationInterpreter.getFeatures();
                        LifDependency lifDepFeature = new LifDependency(dependencyFeatures, depAnnotationInterpreter.getLabel());
                        TcfDependencyEntity dependencyTcfEntity = new TcfDependencyEntity(lifDepFeature.getDependency_function());
                        if (lifDepFeature.isDependantExist()) {
                            dependencyTcfEntity.setDepIDs(getTokenStartId(lifDepFeature.getDependent()));
                        }
                        if (lifDepFeature.isGovonorExist()) {
                            dependencyTcfEntity.setGovIDs(getTokenStartId(lifDepFeature.getGovernor()));
                        }
                        dependencyEntities.add(dependencyTcfEntity);

                    } else {
                        throw new Exception("no lif token found from dependency id!!");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(LifDependencyParserStored.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }

    public boolean seperateUnitsofParseStruectures(List<LifAnnotationInterpreter> lifAnnotationList) throws ConversionException {
        for (LifAnnotationInterpreter annotationObject : lifAnnotationList) {
            if (annotationObject.getUrl().equals(Discriminators.Uri.TOKEN)) {
                this.tokenList.add(annotationObject);
            }
        }
        return true;
    }

    public Long getTokenStartId(String lifTokenId) throws Exception {
        if (LifAnnotationInterpreter.elementIdMapper.containsKey(lifTokenId)) {
            return LifAnnotationInterpreter.elementIdMapper.get(lifTokenId).getStart();
        }
        return new Long(0);
    }

    public List<LifAnnotationInterpreter> getTokenList() {
        return tokenList;
    }

    public Map<Long, List<TcfDependencyEntity>> getDependencyEntities() {
        return dependencyParses;
    }

    public List<TcfDependencyEntity> getDependencyEntities(Long parseIndex) throws Exception {
        if (this.dependencyParses.containsKey(parseIndex)) {
            return this.dependencyParses.get(parseIndex);
        } else {
            throw new Exception("No Dependency list found for in Parse" + parseIndex);
        }
    }

    public Vector<Long> getParseIndexs() throws Exception {
        Vector<Long> parseIndexsSort = new Vector<Long>(this.dependencyParses.keySet());
        Collections.sort(parseIndexsSort);
        return parseIndexsSort;
    }

}
