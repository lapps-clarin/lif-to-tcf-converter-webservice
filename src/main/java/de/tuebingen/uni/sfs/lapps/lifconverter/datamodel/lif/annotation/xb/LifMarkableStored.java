/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.xb;

import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.api.LifMarkable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.lappsgrid.vocabulary.Features;

/**
 *
 * @author felahi
 */
public class LifMarkableStored implements LifMarkable{

    private Map<Object, Object> features = new HashMap<Object, Object>();

    public LifMarkableStored(Map<Object, Object> features) {
        if (!features.isEmpty()) {
            this.setFeatures(features);
        }
    }

    public void setFeatures(Map<Object, Object> features) {
        this.features = features;
    }

    public Set<String> getTargets() {
        return  (Set<String>) this.features.get(Features.Markable.TARGETS);
    }

}
