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
public class LifException extends Exception{
    
    public LifException() {
    }

    public LifException(String message) {
        super(message);
    }

    public LifException(Throwable cause) {
        super(cause);
    }

    public LifException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
