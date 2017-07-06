/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.tcf.xb;

/**
 *
 * @author felahi
 */
public class TcfDependencyEntity {

    private Long depIDs = null;
    private Long govIDs = null;
    private String func = null;

    public TcfDependencyEntity(String func) {
        this.func = func;
    }

    public TcfDependencyEntity(Long depIDs, Long govIDs, String func) {
        this.depIDs = depIDs;
        this.govIDs = govIDs;
        this.func = func;
    }

    public Long getDepIDs() {
        return depIDs;
    }

    public Long getGovIDs() {
        return govIDs;
    }

    public String getFunc() {
        return func;
    }

    public void setDepIDs(Long depIDs) {
        this.depIDs = depIDs;
    }

    public void setGovIDs(Long govIDs) {
        this.govIDs = govIDs;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    @Override
    public String toString() {
        return "DependencyEntity{" + "depIDs=" + depIDs + ", govIDs=" + govIDs + ", func=" + func + '}';
    }

}
