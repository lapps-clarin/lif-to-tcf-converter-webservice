/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.exceptions;

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
