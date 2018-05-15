/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.api;

import de.tuebingen.uni.sfs.lapps.exceptions.JsonValidityException;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.impl.ConvertAnnotationsImpl;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.VocabularyMappingException;
import de.tuebingen.uni.sfs.lapps.profile.api.LifProfile;
import java.io.IOException;
/**
 *
 * @author Mohammad Fazleh Elahi
 */
public interface ConverterFormat {
    public ConvertAnnotationsImpl convertFormat(LifProfile lappsLifProfile) throws LifException,VocabularyMappingException,ConversionException,IOException,JsonValidityException;
}
