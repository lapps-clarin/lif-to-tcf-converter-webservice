/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.api;

import java.util.List;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.xb.LifAnnotationInterpreter;

/**
 *
 * @author felahi
 */
public interface LifToken {
    public List<LifAnnotationInterpreter> getTokenList(); 
}
