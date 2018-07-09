/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.api;

import de.tuebingen.uni.sfs.lapps.exceptions.JsonValidityException;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.VocabularyMappingException;
import java.io.IOException;
import java.io.OutputStream;
import de.tuebingen.uni.sfs.lapps.core.api.profiler.LifFormat;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public interface FormatConverter {

    public LayerConverter convertFormat(LifFormat lappsLifProfile) throws LifException, VocabularyMappingException, ConversionException, IOException, JsonValidityException;

    public void process(OutputStream os) throws ConversionException;
}
