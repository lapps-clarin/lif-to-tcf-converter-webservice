/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.annotation.xb;

import de.tuebingen.uni.sfs.lapps.library.constants.LifVocabularies;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.lappsgrid.discriminator.Discriminators;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.configurations.TcfVocabularies;

/**
 *
 * @author felahi
 */
public class LifConstituentStructure {

    private String text = null;
    private String tree = null;
    private List<String> constituents = new ArrayList<String>();

    public LifConstituentStructure(Map<Object, Object> features) throws NullPointerException {
        this.text = (String) features.get(Discriminators.Alias.SENTENCE);
        this.tree = (String) features.get(LifVocabularies.LIF.DiscriminitorsExtended.PENN_TREE);
        this.constituents = (List) features.get(LifVocabularies.LIF.DiscriminitorsExtended.CONSTITUENTS);
    }

    public String getText() {
        return text;
    }

    public List<String> getConstituents() {
        return constituents;
    }

    public String getTree() {
        return tree;
    }

}
