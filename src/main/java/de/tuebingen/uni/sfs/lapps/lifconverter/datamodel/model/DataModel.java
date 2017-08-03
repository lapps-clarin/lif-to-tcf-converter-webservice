/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.model;

import de.tuebingen.uni.sfs.lapps.library.exception.LifException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author felahi
 */
public abstract class DataModel {

    public abstract void inputDataProcessing(InputStream is) throws LifException;

    public abstract void process(OutputStream os);

    public abstract boolean isValid();
}
