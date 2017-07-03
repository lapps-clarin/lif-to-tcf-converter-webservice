/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weblicht.format_converter.datamodel.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import weblicht.format_converter.datamodel.exceptions.LifException;

/**
 *
 * @author felahi
 */
public abstract class DataModel {

    public abstract void inputDataProcessing(InputStream is) throws LifException;

    public abstract void process(OutputStream os);

    public abstract boolean isValid();
}
