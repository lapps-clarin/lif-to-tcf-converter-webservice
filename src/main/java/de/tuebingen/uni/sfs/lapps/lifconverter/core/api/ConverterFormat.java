/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.api;

import de.tuebingen.uni.sfs.lapps.lifconverter.core.impl.ConvertToTCFAnnotations;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.profile.LIFProfiler;
import java.io.InputStream;

/**
 *
 * @author felahi
 */
public interface ConverterFormat {

    public ConvertToTCFAnnotations convertFormat(LIFProfiler lifDataModel,InputStream is) throws ConversionException,Exception;
}
