/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.utils;

import de.tuebingen.uni.sfs.lapps.library.annotation.LifAnnotationInterpreter;
import java.util.List;

/**
 *
 * @author fazlehelahi
 */
public class LifTcfUtils {
    
    public static LifAnnotationInterpreter[] convertToArray(List<LifAnnotationInterpreter> list) {
        LifAnnotationInterpreter[] stringArray = new LifAnnotationInterpreter[list.size()];
        Integer index = 0;
        for (LifAnnotationInterpreter charOffsetLifObject : list) {
            stringArray[index++] = charOffsetLifObject;
        }
        return stringArray;
    }

    
}
