/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weblicht.format_converter.datamodel.exceptions;

import java.util.Set;
import weblicht.format_converter.datamodel.exceptions.LifException;
import weblicht.format_converter.datamodel.utils.JsonProcessor;

/**
 *
 * @author felahi
 */
public interface ValidityCheck {

    public boolean isValid() throws LifException;

    public boolean isNonEmptyDocument() throws LifException;
    
    public boolean isDocumentStructureValid() throws LifException;
    
    public boolean isToplevelAnnotationValid() throws LifException;
    
    public boolean isAnnotationLayerValid() throws LifException;
    
    public boolean isAnnotationValid() throws LifException;

}
