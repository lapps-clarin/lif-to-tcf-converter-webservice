/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.api;

import de.tuebingen.uni.sfs.lapps.core.layer.api.AnnotationLayerFinder;
import de.tuebingen.uni.sfs.lapps.core.layer.impl.AnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import java.util.List;

/**
 *
 * @author felahi
 */
public interface ConvertAnnotations {

    public void toLayers(AnnotationLayerFinder layer, List<AnnotationInterpreter> annotationlist) throws Exception;

    public void toText(String text) throws ConversionException;

    public void toLanguage(String language) throws Exception;

    public void toToken() throws Exception;

    public void toPos() throws Exception;

    public void toLemma() throws Exception;

    public void toSentences() throws Exception;

    public void toDependencyParser() throws Exception;

    public void toConstituentParser() throws Exception;

    public void toNameEntity() throws Exception;
    
    public void toCoreferenceResolver() throws Exception;
    
    public void toTextSource(String fileString) throws Exception;
    
}
