/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.utils;

import de.tuebingen.uni.sfs.lapps.core.impl.annotation.LifConstituent;
import eu.clarin.weblicht.wlfxb.tc.api.Constituent;
import eu.clarin.weblicht.wlfxb.tc.api.ConstituentParse;
import eu.clarin.weblicht.wlfxb.tc.api.ConstituentParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;

/**
 *
 * @author felahi
 */
public class TcfConstituentsTreeBuild implements ConstituentParse {

    public static final String CONSTITUENT_ROOT = "ROOT";
    private Stack<String> travelStack = new Stack<String>();
    private Map<String, Constituent> vistedNodes = new HashMap<String, Constituent>();
    private Map<String, LifConstituent> idConstituents = new HashMap<String, LifConstituent>();
    private Map<String, Long> tokenIdStartIdMapper = new HashMap<String, Long>();
    private CharOffsetToTokenIdMapper charOffsetToTokenIdMapper = null;
    private ConstituentParsingLayer constituentParsingLayer = null;
    private Constituent rootConstituent = null;

    public TcfConstituentsTreeBuild(LifConstituent rootNode, List<LifConstituent> constituentLifConstituents, Map<String, Long> tokenIdStartIdMapper,
            CharOffsetToTokenIdMapper charOffsetToTokenIdMapper, ConstituentParsingLayer constituentParsingLayer) throws ConversionException {
        this.tokenIdStartIdMapper = tokenIdStartIdMapper;
        this.charOffsetToTokenIdMapper = charOffsetToTokenIdMapper;
        this.constituentParsingLayer = constituentParsingLayer;
        this.setIdInitialConstituents(constituentLifConstituents);
        this.buildTree(rootNode);
    }

    private void buildTree(LifConstituent rootNode) throws ConversionException, NullPointerException {
        try {
            if (rootNode != null) {
                this.exploreTree(rootNode);
            } else {
                throw new NullPointerException("The root node null. The tree building failed!!");
            }
            this.setTreeRoot(rootNode);
        } catch (ConversionException ex) {
            throw new ConversionException("The constituent node null. The tree building failed!!");
        }
    }

    private void exploreTree(LifConstituent rootNode) throws ConversionException {
        this.travelStack.push(rootNode.getConstituentId());

        for (Integer index = 0; index < 50; index++) {
            if (travelStack.isEmpty()) {
                break;
            } else {
                String nodeToVisit = travelStack.pop();
                if (this.checkVisited(nodeToVisit)) {
                } else {
                    addItemInStack(nodeToVisit);
                }
            }
        }

    }

    private void addItemInStack(String nodeToVisit) {
        this.travelStack.push(nodeToVisit);
        LifConstituent node = this.idConstituents.get(nodeToVisit);
        for (String childId : node.getChildrenList()) {
            this.travelStack.push(childId);
        }
    }

    private void setIdInitialConstituents(List<LifConstituent> constituentLifConstituents) {
        for (LifConstituent lifConstituent : constituentLifConstituents) {
            this.idConstituents.put(lifConstituent.getConstituentId(), lifConstituent);
        }
    }

    private boolean checkVisited(String parentId) throws ConversionException {
        LifConstituent lifConstituent = null;

        if (this.idConstituents.containsKey(parentId)) {
            lifConstituent = this.idConstituents.get(parentId);
        } else {
            throw new ConversionException("The constituent ids(" + parentId + ") are wrong in lif processing file!!");
        }

        if (lifConstituent.getChildrenList().size() > 1) {
            return childListGreaterThanOne(lifConstituent, parentId);
        } else if (lifConstituent.getChildrenList().size() == 1) {
            return childListIsOne(lifConstituent, parentId);
        }

        return false;
    }

    private boolean childListGreaterThanOne(LifConstituent lifConstituent, String parentId) {
        boolean flag = true;
        for (String childId : lifConstituent.getChildrenList()) {
            if (!this.vistedNodes.containsKey(childId)) {
                flag = false;
                break;
            }

        }
        if (flag) {
            Constituent constituent = this.setNonTerminalNode(parentId);
            this.vistedNodes.put(parentId, constituent);
            return true;
        }
        return flag;
    }

    private boolean childListIsOne(LifConstituent lifConstituent, String parentId) throws ConversionException {
        String childId = lifConstituent.getChildrenList().iterator().next();

        if (tokenIdStartIdMapper.containsKey(childId)) {
            Constituent terminal = this.setLexicon(parentId, childId);
            //Constituent terminalConstituent = setTerminalNode(parentId, terminal);
            this.vistedNodes.put(parentId, terminal);
            return true;
        } else {
            if (vistedNodes.containsKey(childId)) {
                Constituent constituent = this.setNonTerminalNode(parentId, childId);
                this.vistedNodes.put(parentId, constituent);
                return true;
            } else {
                Constituent childconstituent = this.vistedNodes.get(childId);
                this.vistedNodes.put(parentId, childconstituent);
                return false;
            }
        }
    }

    private Constituent setLexicon(String parentId, String childId) throws ConversionException {
        LifConstituent parentLifConstituent = this.idConstituents.get(parentId);
        Long startId = tokenIdStartIdMapper.get(childId);
        Token token = charOffsetToTokenIdMapper.startIdToToken(startId);
        return this.createTcfTerminal(parentLifConstituent.getCatFunction(), token);
    }

    private Constituent setTerminalNode(String parentId, Constituent terminal) {
        LifConstituent parentLifConstituent = this.idConstituents.get(parentId);
        List<Constituent> childConstituents = new ArrayList<Constituent>();
        childConstituents.add(terminal);
        return this.createTcfConstituent(parentLifConstituent.getCatFunction(), childConstituents);
    }

    private Constituent setNonTerminalNode(String parentId, String childId) {
        LifConstituent parentLifConstituent = this.idConstituents.get(parentId);
        List<Constituent> childConstituents = getChildLists(childId);
        return this.createTcfConstituent(parentLifConstituent.getCatFunction(), childConstituents);
    }

    private Constituent setNonTerminalNode(String parentId) {
        LifConstituent parentLifConstituent = this.idConstituents.get(parentId);
        List<String> childIdlist = parentLifConstituent.getChildrenList();
        List<Constituent> childConstituentList = setConstituentChildList(childIdlist);
        return this.createTcfConstituent(parentLifConstituent.getCatFunction(), childConstituentList);
    }

    private List<Constituent> setConstituentChildList(List<String> childIdlist) {
        List<Constituent> childConstituentList = new ArrayList<Constituent>();
        for (String childId : childIdlist) {
            Constituent childConstituent = this.vistedNodes.get(childId);
            childConstituentList.add(childConstituent);
        }
        return childConstituentList;
    }

    private List<Constituent> getChildLists(String childId) {
        List<Constituent> childConstituentList = new ArrayList<Constituent>();
        Constituent childConstituent = this.vistedNodes.get(childId);
        childConstituentList.add(childConstituent);
        return childConstituentList;
    }

    private Constituent setTreeRoot(LifConstituent rootNode) throws ConversionException {
        if (this.vistedNodes.containsKey(rootNode.getConstituentId())) {
            if (this.vistedNodes.get(rootNode.getConstituentId()) != null) {
                rootConstituent = this.vistedNodes.get(rootNode.getConstituentId());
                if (rootConstituent.toString().contains(CONSTITUENT_ROOT)) {
                    return rootConstituent;
                } else {
                    throw new ConversionException("The root node in tcf is not build in Depth first search tree making");
                }
            }
        }
        return null;
    }

    private Constituent createTcfTerminal(String childId, Token token) {
        return this.constituentParsingLayer.createTerminalConstituent(childId, token);
    }

    private Constituent createTcfConstituent(String constituentId, String catFunction, List<Constituent> childConstituentList) {
        return this.constituentParsingLayer.createConstituent(constituentId, catFunction, childConstituentList);
    }

    private Constituent createTcfConstituent(String catFunction, List<Constituent> childConstituentList) {
        return this.constituentParsingLayer.createConstituent(catFunction, childConstituentList);
    }

    public Constituent getRoot() {
        return rootConstituent;
    }
}
