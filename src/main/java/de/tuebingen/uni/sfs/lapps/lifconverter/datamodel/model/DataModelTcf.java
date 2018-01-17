/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.model;

import de.tuebingen.uni.sfs.lapps.library.layer.api.AnnotationLayerFinder;
import de.tuebingen.uni.sfs.lapps.library.annotation.api.LifConstituentParser;
import de.tuebingen.uni.sfs.lapps.library.annotation.api.LifDependencyParser;
import de.tuebingen.uni.sfs.lapps.library.annotation.api.LifMarkable;
import de.tuebingen.uni.sfs.lapps.library.annotation.api.LifReference;
import de.tuebingen.uni.sfs.lapps.library.annotation.api.LifReferenceLayer;
import de.tuebingen.uni.sfs.lapps.library.layer.xb.AnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.library.annotation.xb.LifConstituent;
import de.tuebingen.uni.sfs.lapps.library.annotation.xb.LifConstituentParserStored;
import de.tuebingen.uni.sfs.lapps.library.annotation.xb.LifDependencyParserStored;
import de.tuebingen.uni.sfs.lapps.library.annotation.xb.LifRefererenceLayerStored;
import de.tuebingen.uni.sfs.lapps.library.annotation.xb.LifTokenPosLemmaStored;
import de.tuebingen.uni.sfs.lapps.library.annotation.xb.DependencyEntityInfo;
import de.tuebingen.uni.sfs.lapps.library.exception.LifException;
import de.tuebingen.uni.sfs.lapps.library.exception.VocabularyMappingException;
import de.tuebingen.uni.sfs.lapps.library.utils.xb.DuplicateChecker;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.conversion.AnnotationLayerConverter;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.utils.TcfConstituentsTreeBuild;
import eu.clarin.weblicht.wlfxb.io.WLDObjector;
import eu.clarin.weblicht.wlfxb.io.WLFormatException;
import eu.clarin.weblicht.wlfxb.tc.api.Constituent;
import eu.clarin.weblicht.wlfxb.tc.api.ConstituentParse;
import eu.clarin.weblicht.wlfxb.tc.api.ConstituentParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.api.Dependency;
import eu.clarin.weblicht.wlfxb.tc.api.DependencyParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.api.LemmasLayer;
import eu.clarin.weblicht.wlfxb.tc.api.NamedEntitiesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.PosTagsLayer;
import eu.clarin.weblicht.wlfxb.tc.api.SentencesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.TextSourceLayer;
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.tc.api.TokensLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusStored;
import eu.clarin.weblicht.wlfxb.xb.WLData;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.utils.CharOffsetToTokenIdMapper;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.constants.TcfVocabularies;
import eu.clarin.weblicht.wlfxb.tc.api.Reference;
import eu.clarin.weblicht.wlfxb.tc.api.ReferencesLayer;
import java.util.Arrays;

/**
 *
 * @author felahi
 */
public class DataModelTcf extends DataModel implements AnnotationLayerConverter {

    private TextCorpusStored textCorpusStored = null;
    private AnnotationLayerFinder givenToolTagSetVocabularies = null;
    private List<AnnotationInterpreter> givenAnnotations = new ArrayList<AnnotationInterpreter>();
    private CharOffsetToTokenIdMapper charOffsetToTokenIdMapper = null;
    private Map<String, Token> tokenIdToken = new HashMap<String, Token>();

    public DataModelTcf(InputStream input) throws ConversionException, IOException {
        toLanguage(TcfVocabularies.TCF.TcfConstants.DEFAULT_LANGUAGE);
    }

    public void toLayers(AnnotationLayerFinder layer, List<AnnotationInterpreter> annotationlist) throws Exception {
        //System.out.println("++++++++++++++++++++++++++++" + givenLayer + "++++++++++++++++++++++++++++");
        this.givenAnnotations = annotationlist;
        this.givenToolTagSetVocabularies = layer;
        this.toLayer(this.givenToolTagSetVocabularies.getLayer());
    }

    public void toLayer(String givenLayer) throws ConversionException, VocabularyMappingException, LifException {

        try {
            if (givenLayer.contains(TextCorpusLayerTag.TOKENS.toString())) {
                toToken();
            } else if (givenLayer.contains(TextCorpusLayerTag.POSTAGS.toString())) {
                toPos();
            } else if (givenLayer.contains(TextCorpusLayerTag.LEMMAS.toString())) {
                toLemma();
            } else if (givenLayer.contains(TextCorpusLayerTag.SENTENCES.toString())) {
                toSentences();
            } else if (givenLayer.contains(TextCorpusLayerTag.NAMED_ENTITIES.toString())) {
                this.toNameEntity();
            } else if (givenLayer.contains(TextCorpusLayerTag.PARSING_DEPENDENCY.toString())) {
                toDependencyParser();
            } else if (givenLayer.contains(TextCorpusLayerTag.PARSING_CONSTITUENT.toString())) {
                this.toConstituentParser();
            } else if (givenLayer.contains(TextCorpusLayerTag.REFERENCES.toString())) {
                this.toCoreferenceResolver();
            }
        } catch (LifException ex) {
            Logger.getLogger(DataModelTcf.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException("LIF annotations are wrong!!");
        } catch (ConversionException ex) {
            Logger.getLogger(DataModelTcf.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException("LIF to TCF conversion failed!!");
        }

    }

    public void toLanguage(String language) throws ConversionException {
        try {
            textCorpusStored = new TextCorpusStored(language);
        } catch (NullPointerException ex) {
            throw new ConversionException("Language conversion failed from lif to tcf failed!!");
        } finally {
            textCorpusStored = new TextCorpusStored(TcfVocabularies.TCF.TcfConstants.DEFAULT_LANGUAGE);
        }
    }

    public void toText(String text) throws ConversionException {
        try {
            textCorpusStored.createTextLayer().addText(text.replaceAll("\n", ""));
        } catch (NullPointerException ex) {
            Logger.getLogger(DataModelTcf.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException("Text conversion failed from lif to tcf failed!!");
        }
    }

    //this is a not good implementation. It will be changed later
    public void toToken() throws ConversionException {
        Collections.sort(givenAnnotations);
        TokensLayer tokensLayer = null;
        PosTagsLayer posLayer = null;
        LemmasLayer lemmaLayer = null;
        boolean wordFlag = false, posFlag = false, lemmaFlag = false, flag = true;
        DuplicateChecker duplicateChecker = new DuplicateChecker();
        tokenIdToken = new HashMap<String, Token>();
        Map<Long, String> startIdTokenIdMapper = new HashMap<Long, String>();

        for (AnnotationInterpreter annotationInterpreter : givenAnnotations) {
            Token token = null;
            LifTokenPosLemmaStored lifToken = new LifTokenPosLemmaStored(annotationInterpreter.getFeatures());

            if (!lifToken.getFeatures().isEmpty()) {

                if (flag) {
                    if (lifToken.getWord() != null) {
                        tokensLayer = textCorpusStored.createTokensLayer();
                        wordFlag = true;
                    }
                    if (lifToken.getPos() != null) {
                        posLayer = textCorpusStored.createPosTagsLayer(TcfVocabularies.TCF.TcfTagSets.POS_TAGSETS);
                        posFlag = true;
                    }
                    if (lifToken.getLemma() != null) {
                        lemmaLayer = textCorpusStored.createLemmasLayer();
                        lemmaFlag = true;
                    }
                    flag = false;
                }

                if (!duplicateChecker.isDuplicate(annotationInterpreter.getStart())) {
                    if (wordFlag) {
                        token = tokensLayer.addToken(lifToken.getWord());
                        startIdTokenIdMapper.put(annotationInterpreter.getStart(), token.getID());
                        tokenIdToken.put(token.getID(), token);
                    }

                    if (posFlag) {
                        token = findConnectedToken(annotationInterpreter.getStart(), token);
                        posLayer.addTag(lifToken.getPos(), token);
                    }

                    if (lemmaFlag) {
                        token = findConnectedToken(annotationInterpreter.getStart(), token);
                        lemmaLayer.addLemma(lifToken.getLemma(), token);
                    }
                }

            }
        }
        if (wordFlag) {
            charOffsetToTokenIdMapper = new CharOffsetToTokenIdMapper(startIdTokenIdMapper, tokenIdToken);
        }
    }

    private Token findConnectedToken(Long start, Token token) throws ConversionException {
        if (token != null) {
            return token;
        } else {
            token = charOffsetToTokenIdMapper.startIdToToken(start);
            if (token != null) {
                return token;
            } else {
                throw new ConversionException("no connected token found for this token-level-layer!!");
            }
        }
    }

    public void toPos() throws ConversionException {
        toToken();
    }

    public void toLemma() throws ConversionException {
        toToken();
    }

    public void toSentences() throws ConversionException {
        Collections.sort(givenAnnotations);

        try {
            if (textCorpusStored.getTokensLayer().isEmpty()) {
                throw new ConversionException("There is no token layer. A token layer is required for sentence layer");
            }
        } catch (NullPointerException nullExp) {
            throw new ConversionException("There is no token layer before sentence layer!!");
        }

        SentencesLayer sentencesLayer = textCorpusStored.createSentencesLayer();

        for (AnnotationInterpreter lifSentence : givenAnnotations) {
            List<Token> sentenceTokens = new ArrayList<Token>();
            List<String> tokenIds = charOffsetToTokenIdMapper.getTokenIdsFromStartIds(lifSentence.getStart(), lifSentence.getEnd());
            for (String tokenId : tokenIds) {
                Token token = charOffsetToTokenIdMapper.getToken(tokenId);
                sentenceTokens.add(token);
            }
            sentencesLayer.addSentence(sentenceTokens);
        }
    }

    public void toNameEntity() throws ConversionException, VocabularyMappingException {
        try {
            if (textCorpusStored.getTokensLayer().isEmpty()) {
                throw new ConversionException("There is no token layer in lif file. For conversion of LIF to TCF nameEntitty layer, a token layer is mandatory!!");
            }
        } catch (NullPointerException nullExp) {
            throw new ConversionException("There is no token layer in lif file. For conversion of LIF to TCF nameEntitty layer, a token layer is mandatory!!");
        }

        String tool = this.givenToolTagSetVocabularies.getTool();
        NamedEntitiesLayer namedEntitiesLayer = textCorpusStored.createNamedEntitiesLayer(this.givenToolTagSetVocabularies.getTagSetName(tool));

        for (AnnotationInterpreter annotationObject : givenAnnotations) {
            List<String> tokenIds = charOffsetToTokenIdMapper.getTokenIdsFromStartIds(annotationObject.getStart(), annotationObject.getEnd());
            List<Token> tokenList = new ArrayList<Token>();
            for (String tokenId : tokenIds) {
                Token token = charOffsetToTokenIdMapper.getToken(tokenId);
                tokenList.add(token);
            }
            if (!tokenList.isEmpty()) {
                String nameEntityKey = this.givenToolTagSetVocabularies.getVocabularies(tool, annotationObject.getUrl());
                if (tokenList.size() == 1) {
                    namedEntitiesLayer.addEntity(nameEntityKey, tokenList.get(0));
                } else {
                    namedEntitiesLayer.addEntity(nameEntityKey, tokenList);
                }
            }
        }

    }

    public void toConstituentParser() throws ConversionException, LifException {
        ConstituentParsingLayer constituentParsingLayer = textCorpusStored.createConstituentParsingLayer(TcfVocabularies.TCF.TcfTagSets.CONSTITUENT_TAGSETS);
        LifConstituentParser lifConstituentParser = new LifConstituentParserStored(givenAnnotations);
        this.givenAnnotations = lifConstituentParser.getTokenList();
        this.toToken();

        try {
            for (Long parseIndex : lifConstituentParser.getParseIndexs()) {
                LifConstituent lifRoot = lifConstituentParser.getRoot(parseIndex);
                List<LifConstituent> lifConstituents = lifConstituentParser.getConstituentEntities(parseIndex);
                ConstituentParse tcfTreeBuild = new TcfConstituentsTreeBuild(lifRoot, lifConstituents,
                        lifConstituentParser.getTokenIdStartIdMapper(),
                        charOffsetToTokenIdMapper, constituentParsingLayer);
                Constituent tcfRoot = tcfTreeBuild.getRoot();
                constituentParsingLayer.addParse(tcfRoot);
                break;
            }

        } catch (NullPointerException nullExp) {
            throw new ConversionException(nullExp.getMessage());
        }
    }

    public void toDependencyParser() throws ConversionException, LifException {
        DependencyParsingLayer dependencyParsingLayer = textCorpusStored.createDependencyParsingLayer(TcfVocabularies.TCF.TcfTagSets.DEPARSING_TAGSETS, true, true);
        LifDependencyParser lifDependencyParser = new LifDependencyParserStored(givenAnnotations);
        this.givenAnnotations = lifDependencyParser.getTokenList();
        this.toToken();

        try {
            List<Dependency> tcfDependencyList = new ArrayList<Dependency>();
            for (Long parseIndex : lifDependencyParser.getParseIndexs()) {
                for (DependencyEntityInfo dependencyEntity : lifDependencyParser.getDependencyEntities(parseIndex)) {
                    Token dependent = this.charOffsetToTokenIdMapper.startIdToToken(dependencyEntity.getDepIDs());
                    Token govonor = this.charOffsetToTokenIdMapper.startIdToToken(dependencyEntity.getGovIDs());
                    Dependency dependency = dependencyParsingLayer.createDependency(dependencyEntity.getFunc(), dependent, govonor);
                    tcfDependencyList.add(dependency);
                }
                dependencyParsingLayer.addParse(tcfDependencyList);
            }
        } catch (Exception exp) {
            throw new ConversionException("the converion of constituent parser failed!!");
        }
    }

    public void toCoreferenceResolver() throws ConversionException, LifException {
        LifReferenceLayer lifRefererenceLayer = new LifRefererenceLayerStored(givenAnnotations);
        this.givenAnnotations = lifRefererenceLayer.getTokenList();
        this.toToken();
        TokensLayer tokensLayer = textCorpusStored.getTokensLayer();
        ReferencesLayer refsLayer = textCorpusStored.createReferencesLayer("BART", "TuebaDZ", null);

        Map<String, Reference> markIdReference = new HashMap<String, Reference>();
        List<Reference> references = new ArrayList<Reference>();
        for (String lifMarkableId : lifRefererenceLayer.getMarkableAnnotations().keySet()) {
            LifMarkable lifMarkable = lifRefererenceLayer.getMarkableAnnotations().get(lifMarkableId);
            List<String> lifTokenIds = lifMarkable.getTargets();
            List<Token> tcftokens = new ArrayList<Token>();
            for (String tokenId : lifTokenIds) {
                if (this.tokenIdToken.containsKey(tokenId)) {
                    tcftokens.add(tokenIdToken.get(tokenId));
                }

            }
            Reference reference = refsLayer.createReference(tcftokens);
            markIdReference.put(lifMarkableId, reference);
            references.add(reference);
        }

        for (String lifCorferId : lifRefererenceLayer.getCorferenceAnnotations().keySet()) {
            LifReference lifReference = lifRefererenceLayer.getCorferenceAnnotations().get(lifCorferId);
            String repMarkableId = lifReference.getRepresentative();
            if (markIdReference.containsKey(repMarkableId)) {
                Reference refRep = markIdReference.get(repMarkableId);
                Reference[] refMentions = new Reference[lifReference.getMentions().size() - 1];
                Integer index = 0;
                for (String mentionMarkableId : lifReference.getMentions()) {
                    if (!mentionMarkableId.equals(repMarkableId) && markIdReference.containsKey(mentionMarkableId)) {
                        refMentions[index++] = markIdReference.get(mentionMarkableId);
                    }

                }
                refsLayer.addRelation(refRep, "anaphoric", refMentions);

            }

        }
        refsLayer.addReferent(references);
    }

    public void toTextSource(String fileString) throws Exception {
        TextSourceLayer textSourceLayer = textCorpusStored.createTextSourceLayer();
        textSourceLayer.addText(fileString);
    }

    public void inputDataProcessing(InputStream is) {

    }

    @Override
    public void process(OutputStream os) {
        WLData wlData = new WLData(textCorpusStored);
        try {
            WLDObjector.write(wlData, os);
        } catch (WLFormatException ex) {
            Logger.getLogger(DataModelTcf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean isValid() {
        if (textCorpusStored != null) {
            return true;
        }
        return false;
    }

    public TextCorpusStored getTextCorpusStored() {
        return textCorpusStored;
    }

    public void setGivenAnnotations(List<AnnotationInterpreter> givenAnnotations) {
        this.givenAnnotations = givenAnnotations;
    }
}
