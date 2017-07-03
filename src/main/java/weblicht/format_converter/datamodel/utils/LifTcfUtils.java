/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weblicht.format_converter.datamodel.utils;

import java.util.List;
import weblicht.format_converter.datamodel.lif.annotation.xb.LifAnnotationInterpreter;

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
