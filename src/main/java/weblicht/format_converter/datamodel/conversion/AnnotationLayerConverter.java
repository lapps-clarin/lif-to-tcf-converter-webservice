/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weblicht.format_converter.datamodel.conversion;

import weblicht.format_converter.datamodel.exceptions.ConversionException;
import java.util.List;
import weblicht.format_converter.datamodel.lif.annotation.xb.LifAnnotationInterpreter;

/**
 *
 * @author felahi
 */
public interface AnnotationLayerConverter {

    public void toLayers(AnnotationLayerFinder layer, List<LifAnnotationInterpreter> annotationlist) throws Exception;

    public void toText(String text) throws ConversionException;

    public void toLanguage(String language) throws Exception;

    public void toToken() throws Exception;

    public void toPos() throws Exception;

    public void toLemma() throws Exception;

    public void toSentences() throws Exception;

    public void toDependencyParser() throws Exception;

    public void toConstituentParser() throws Exception;

    public void toNameEntity() throws Exception;
    
    public void toTextSource(String fileString) throws Exception;

}
