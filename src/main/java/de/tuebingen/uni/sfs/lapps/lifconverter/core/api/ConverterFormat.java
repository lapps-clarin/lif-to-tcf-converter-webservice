/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.api;

import de.tuebingen.uni.sfs.lapps.lifconverter.core.impl.ConvertToTCFAnnotations;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import java.io.InputStream;
import de.tuebingen.uni.sfs.lapps.profile.api.LifProfile;

/**
 *
 * @author felahi
 */
public interface ConverterFormat {

    public ConvertToTCFAnnotations convertFormat(LifProfile lifDataModel,InputStream is) throws ConversionException,Exception;
}
