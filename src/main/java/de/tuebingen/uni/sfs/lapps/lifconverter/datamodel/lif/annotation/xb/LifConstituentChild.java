/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.xb;

/**
 *
 * @author felahi
 */
public class LifConstituentChild {

    private String childId = null;
    private String catId = null;
    private Long tokenStartId = new Long(0);

    public LifConstituentChild(String constituentId,String catId, Long tokenStartId ){
        this.childId=constituentId;
        this.catId=catId;
        this.tokenStartId=tokenStartId;
    }

    public String getChildId() {
        return childId;
    }

    public String getCatId() {
        return catId;
    }

    public Long getTokenStartId() {
        return tokenStartId;
    }
    
}
