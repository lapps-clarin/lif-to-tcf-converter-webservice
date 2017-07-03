/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weblicht.format_converter.datamodel.lif.annotation.api;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import weblicht.format_converter.datamodel.exceptions.ConversionException;
import weblicht.format_converter.datamodel.lif.annotation.xb.LifConstituent;
import weblicht.format_converter.datamodel.lif.annotation.xb.LifAnnotationInterpreter;

/**
 *
 * @author felahi
 */
public interface LifConstituentParser extends LifToken{
    
    public Vector<Long> getParseIndexs() ;

    public LifConstituent getRoot(Long parseIndex) throws ConversionException;

    public  List<LifConstituent> getConstituentEntities(Long parseIndex) throws ConversionException;

    public Map<String, Long> getTokenIdStartIdMapper();

}
