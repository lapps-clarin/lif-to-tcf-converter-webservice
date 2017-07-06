/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.conversion;

import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.exceptions.ConversionException;
import java.io.InputStream;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.model.DataModelLif;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.model.DataModelTcf;

/**
 *
 * @author felahi
 */
public interface DataModelConverter {

    public DataModelTcf convertModel(DataModelLif lifDataModel,InputStream is) throws ConversionException,Exception;
}
