/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weblicht.format_converter.datamodel.lif.annotation.xb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.lappsgrid.vocabulary.Features;

/**
 *
 * @author felahi
 */
public class LifConstituent {

    private List<String> childrenList = new ArrayList<String>();
    private String constituentId = null;
    private String catFunction = null;
    private String parentId = null;

    public LifConstituent(LifAnnotationInterpreter constAnnotationInterpreter) throws NullPointerException {
        this.constituentId = constAnnotationInterpreter.getId();
        this.catFunction = constAnnotationInterpreter.getLabel();
        this.childrenList = (List<String>) constAnnotationInterpreter.getFeatures().get(Features.Constituent.CHILDREN);
        this.parentId = (String) constAnnotationInterpreter.getFeatures().get(Features.Constituent.PARENT);
    }

    public List<String> getChildrenList() {
        return childrenList;
    }

    public String getConstituentId() {
        return constituentId;
    }

    public String getCatFunction() {
        return catFunction;
    }

    public String getParentId() {
        return parentId;
    }
}
