/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.layer.LifAnnotationLayersStored;
import org.apache.commons.io.IOUtils;
import org.lappsgrid.serialization.lif.Container;
import org.lappsgrid.serialization.lif.View;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.configurations.Vocabularies;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.document.LifDocumentFinder;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.xb.LifAnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.conversion.AnnotationLayerFinder;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.exceptions.LifException;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.exceptions.LifValidityCheck;

/**
 *
 * @author felahi
 */
public class DataModelLif extends DataModel {

    private Map<Integer, List<LifAnnotationInterpreter>> annotationLayerData = new HashMap<Integer, List<LifAnnotationInterpreter>>();
    private Map<Integer, AnnotationLayerFinder> indexAnnotationLayer = new HashMap<Integer, AnnotationLayerFinder>();
    private Vector<Integer> sortedLayer = new Vector<Integer>();
    public Container lifContainer = null;
    private String fileString = null;
    private boolean modelValidity = false;
    private LifValidityCheck lifValidityCheck = new LifValidityCheck();

    public DataModelLif(InputStream input) throws LifException, IOException {
        this.inputDataProcessing(input);
    }

    @Override
    public void inputDataProcessing(InputStream is) {
        try {
            fileString = IOUtils.toString(is, Vocabularies.GeneralParameters.UNICODE);
            LifDocumentFinder lifContainerFinder = lifContainerFinder = new LifDocumentFinder(fileString);
            lifContainer = lifContainerFinder.getMascDocument().getContainer();
            extractAndSortViews();
            modelValidity = true;
        } catch (LifException ex) {
            modelValidity = false;
            Logger.getLogger(DataModelLif.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } catch (IOException ex) {
            modelValidity = false;
            Logger.getLogger(DataModelLif.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } catch (Exception ex) {
            modelValidity = false;
            Logger.getLogger(DataModelLif.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    @Override
    public void process(OutputStream os) {
    }

    public void extractAndSortViews() throws LifException {
        processViews();
        sortedLayer = new Vector(this.indexAnnotationLayer.keySet());
        Collections.sort(sortedLayer);
    }

    private void processViews() throws LifException {
        Integer index = 0;
        List<View> views = lifContainer.getViews();

        /*if (!isViewValid(views)) {
            return;
        }*/
        Set<Integer> ignoreViewsIndex = this.dealWithMultipleLayers(views);

        for (View view : views) {
            if (!ignoreViewsIndex.contains(index)) {
                LifAnnotationLayersStored lifLayer = new LifAnnotationLayersStored(view.getMetadata());
                List<LifAnnotationInterpreter> lifCharOffsetObjectList = lifLayer.processAnnotations(view.getAnnotations());
                if(!lifLayer.isLayerValid()){
                     throw new LifException("The annotation layer is not valid!!"); 
                }
                annotationLayerData.put(index, lifCharOffsetObjectList);
                indexAnnotationLayer.put(index, lifLayer);
            }
            index = index + 1;
        }

    }

    private Set<Integer> dealWithMultipleLayers(List<View> views) throws LifException {
        Map<String, Integer> annotationLayersToConsider = new HashMap<String, Integer>();
        Set<Integer> ignoreIndexSet = new HashSet<Integer>();
        for (Integer index = 0; index < views.size(); index++) {
            View view = views.get(index);
            LifAnnotationLayersStored lifLayer = new LifAnnotationLayersStored(view.getMetadata());
            if (annotationLayersToConsider.containsKey(lifLayer.getLayer())) {
                Integer ignoreIndex = annotationLayersToConsider.get(lifLayer.getLayer());
                ignoreIndexSet.add(ignoreIndex);
            }
            annotationLayersToConsider.put(lifLayer.getLayer(), index);
        }
        return ignoreIndexSet;
    }

    private boolean isViewValid(List<View> views) throws LifException {
        lifValidityCheck.setViews(views);
        if (!lifValidityCheck.isAnnotationLayerValid()) {
            return false;
        }
        return true;
    }

    public Map<Integer, List<LifAnnotationInterpreter>> getAnnotationLayerData() {
        return annotationLayerData;
    }

    public List<LifAnnotationInterpreter> getAnnotationLayerData(Integer index) {
        return annotationLayerData.get(index);
    }

    public Map<Integer, AnnotationLayerFinder> getIndexAnnotationLayer() {
        return indexAnnotationLayer;
    }

    public AnnotationLayerFinder getIndexAnnotationLayer(Integer index) {
        return this.indexAnnotationLayer.get(index);
    }

    public Vector<Integer> getSortedLayer() {
        return sortedLayer;
    }

    @Override
    public boolean isValid() {
        return modelValidity;
    }

    public String getLanguage() {
        return lifContainer.getLanguage();
    }

    public String getText() {
        return lifContainer.getText();
    }

    public String getFileString() {
        return fileString;
    }

    public Container getLifContainer() {
        return lifContainer;
    }

    @Override
    public String toString() {
        return "DataModelLif{" + "indexAnnotationLayer=" + indexAnnotationLayer + ", sortedLayer=" + sortedLayer + '}';
    }

}
