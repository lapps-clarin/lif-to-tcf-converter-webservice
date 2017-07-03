/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weblicht.format_converter.datamodel.lif.annotation.api;

import java.util.List;
import weblicht.format_converter.datamodel.exceptions.ConversionException;
import weblicht.format_converter.datamodel.lif.annotation.xb.LifAnnotationInterpreter;

/**
 *
 * @author felahi
 */
public interface LifParseAnnotationProcessing {

    public void extractParses(List<LifAnnotationInterpreter> lifAnnotationList) throws ConversionException;

    public void seperateStructures(LifAnnotationInterpreter annotationObject) throws ConversionException;

    public boolean seperateUnitsofParseStruectures(List<LifAnnotationInterpreter> lifAnnotationList) throws ConversionException;

}
