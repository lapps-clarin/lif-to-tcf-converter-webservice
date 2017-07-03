/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weblicht.format_converter.datamodel.conversion;

import weblicht.format_converter.datamodel.exceptions.ConversionException;
import java.io.InputStream;
import weblicht.format_converter.datamodel.model.DataModelLif;
import weblicht.format_converter.datamodel.model.DataModelTcf;

/**
 *
 * @author felahi
 */
public interface DataModelConverter {

    public DataModelTcf convertModel(DataModelLif lifDataModel,InputStream is) throws ConversionException,Exception;
}
