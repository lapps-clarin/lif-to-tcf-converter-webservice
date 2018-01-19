/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.exceptions;

/**
 *
 * @author felahi
 */
public class VocabularyMappingException extends Exception{

  public VocabularyMappingException() {
  }

  public VocabularyMappingException(String message) {
    super(message);
  }

  public VocabularyMappingException(Throwable cause) {
    super(cause);
  }

  public VocabularyMappingException(String message, Throwable cause) {
    super(message, cause);
  }
    
}