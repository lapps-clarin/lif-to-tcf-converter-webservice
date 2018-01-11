/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.xb;

import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.api.LifReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.lappsgrid.vocabulary.Features;

/**
 *
 * @author felahi
 */
public class LifReferenceStored implements LifReference{

    private Map<Object, Object> features = new HashMap<Object, Object>();

    public LifReferenceStored(Map<Object, Object> features) {
        if (!features.isEmpty()) {
            this.setFeatures(features);
        }
    }

    public void setFeatures(Map<Object, Object> features) {
        this.features = features;
    }

    public String getRepresentative() {
        return (String) this.features.get(Features.Coreference.REPRESENTATIVE);
    }

    public Set<String> getMentions() {
        return (Set<String>) this.features.get(Features.Coreference.MENTIONS);
    }

    public Map<Object, Object> getFeatures() {
        return features;
    }

}
