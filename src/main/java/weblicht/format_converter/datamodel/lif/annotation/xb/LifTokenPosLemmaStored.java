/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weblicht.format_converter.datamodel.lif.annotation.xb;

import java.util.HashMap;
import java.util.Map;
import static org.lappsgrid.vocabulary.Features.Token.LEMMA;
import static org.lappsgrid.vocabulary.Features.Token.POS;
import static org.lappsgrid.vocabulary.Features.Token.WORD;
import weblicht.format_converter.datamodel.lif.annotation.api.LifTokenPosLemma;

/**
 *
 * @author felahi
 */
public class LifTokenPosLemmaStored implements LifTokenPosLemma {

    private Map<Object, Object> features = new HashMap<Object, Object>();

    public LifTokenPosLemmaStored(Map<Object, Object> features) {
        if(features.containsKey(WORD)||features.containsKey(POS)||features.containsKey(LEMMA))
         this.setFeatures(features);
    }

    public void setFeatures(Map<Object, Object> features) {
        this.features = features;
    }

    public String getWord() {
        return (String) this.features.get(WORD);
    }

    public String getPos() {
        return (String) this.features.get(POS);
    }

    public String getLemma() {
        return (String) this.features.get(LEMMA);
    }

    public Map<Object, Object> getFeatures() {
        return features;
    }

}
