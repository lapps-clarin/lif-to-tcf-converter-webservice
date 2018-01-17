/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import de.tuebingen.uni.sfs.lapps.library.model.DataModelLif;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.conversion.ToTCFAnnotationLayer;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.exceptions.ConversionException;
import java.io.InputStream;

/**
 *
 * @author felahi
 */
public interface Converter {

    public ToTCFAnnotationLayer convertModel(DataModelLif lifDataModel,InputStream is) throws ConversionException,Exception;
}
