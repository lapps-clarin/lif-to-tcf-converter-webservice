/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.api;

import de.tuebingen.uni.sfs.lapps.library.annotation.xb.AnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.library.exception.LifException;
import java.util.List;

import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.exceptions.ConversionException;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public interface LifParseAnnotationProcessing {

    public void extractParses(List<AnnotationInterpreter> lifAnnotationList) throws LifException;

    public void seperateStructures(AnnotationInterpreter annotationObject) throws LifException;

    public boolean seperateUnitsofParseStruectures(List<AnnotationInterpreter> lifAnnotationList) throws LifException;

}
