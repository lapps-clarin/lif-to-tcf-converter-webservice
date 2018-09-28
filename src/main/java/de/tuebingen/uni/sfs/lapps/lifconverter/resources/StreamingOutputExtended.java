/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.resources;

import java.io.File;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author felahi
 */
public interface StreamingOutputExtended extends StreamingOutput{
    public File getFile();
}
