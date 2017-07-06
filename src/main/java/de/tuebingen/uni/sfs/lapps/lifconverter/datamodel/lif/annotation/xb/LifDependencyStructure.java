/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.xb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lappsgrid.discriminator.Discriminators;
import org.lappsgrid.vocabulary.Features;

/**
 *
 * @author felahi
 */
public class LifDependencyStructure extends Features.DependencyStructure  {

    private String text = null;
    private List<String> dependencies = new ArrayList<String>();

    public LifDependencyStructure(Map<Object, Object> features) {
        try {
            this.text = (String) features.get(Discriminators.Alias.SENTENCE);
            this.dependencies = (List) features.get(DEPENDENCIES);
        } catch (Exception ex) {
            Logger.getLogger(LifDependencyStructure.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getText() {
        return text;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

}
