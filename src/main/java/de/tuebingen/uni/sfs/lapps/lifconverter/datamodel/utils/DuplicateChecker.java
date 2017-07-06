/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.utils;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author felahi
 */
public class DuplicateChecker {

    private  Set<Long> duplicateChecker = new HashSet<Long>();

    public DuplicateChecker() {
           duplicateChecker = new HashSet<Long>();
    }

    public  boolean isDuplicate(Long value) {
        //the start value of annotation item of lif file is -1 if that is not used at all.
        if(value<0)
            return false;
        
        if (!duplicateChecker.contains(value)) {
            duplicateChecker.add(value);
            return false;
        }
        return true;
    }
}
