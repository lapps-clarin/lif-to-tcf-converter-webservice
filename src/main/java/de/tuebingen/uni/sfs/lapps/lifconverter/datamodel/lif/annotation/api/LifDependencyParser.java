/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.api;

import de.tuebingen.uni.sfs.lapps.library.annotation.api.LifToken;
import java.util.List;
import java.util.Vector;

import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.tcf.xb.TcfDependencyEntity;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public interface LifDependencyParser extends LifToken{
    
    public Vector<Long> getParseIndexs() throws Exception;
    
    public  List<TcfDependencyEntity> getDependencyEntities(Long parseIndex) throws Exception;
    

}
