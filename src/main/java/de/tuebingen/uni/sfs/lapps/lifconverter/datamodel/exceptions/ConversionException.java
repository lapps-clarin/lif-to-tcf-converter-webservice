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
public class ConversionException extends Exception{

  public ConversionException() {
  }

  public ConversionException(String message) {
    super(message);
  }

  public ConversionException(Throwable cause) {
    super(cause);
  }

  public ConversionException(String message, Throwable cause) {
    super(message, cause);
  }
    
}
