/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.clarind.profiler.Values;
import de.tuebingen.uni.sfs.lapps.core.layer.api.AnnotationLayerFinder;
import de.tuebingen.uni.sfs.lapps.core.annotation.api.LifDependencyParser;
import de.tuebingen.uni.sfs.lapps.core.annotation.api.LifMarkable;
import de.tuebingen.uni.sfs.lapps.core.annotation.api.LifReference;
import de.tuebingen.uni.sfs.lapps.core.annotation.api.LifReferenceLayer;
import de.tuebingen.uni.sfs.lapps.utils.AnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.core.annotation.impl.LifConstituent;
import de.tuebingen.uni.sfs.lapps.core.annotation.impl.LifConstituentParserStored;
import de.tuebingen.uni.sfs.lapps.core.annotation.impl.LifDependencyParserStored;
import de.tuebingen.uni.sfs.lapps.core.annotation.impl.LifRefererenceLayerStored;
import de.tuebingen.uni.sfs.lapps.core.annotation.impl.LifTokenPosLemmaStored;
import de.tuebingen.uni.sfs.lapps.utils.DependencyEntityInfo;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import de.tuebingen.uni.sfs.lapps.utils.DuplicateChecker;
import de.tuebingen.uni.sfs.lapps.lifconverter.utils.TcfConstituentsTreeBuild;
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
import de.tuebingen.uni.sfs.lapps.lifconverter.utils.CharOffsetToTokenIdMapper;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import eu.clarin.weblicht.wlfxb.tc.api.Reference;
import eu.clarin.weblicht.wlfxb.tc.api.ReferencesLayer;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.api.ConvertAnnotations;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.VocabularyMappingException;
import de.tuebingen.uni.sfs.lapps.core.annotation.api.LifConstituentParser;
import de.tuebingen.uni.sfs.lapps.core.annotation.api.LifNameEntity;
import de.tuebingen.uni.sfs.lapps.core.annotation.api.LifNameEntityLayer;
import de.tuebingen.uni.sfs.lapps.core.annotation.impl.LifNameEntityLayerStored;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionErrorMessage;

/**
 *
 * @author felahi
 */
public class ConvertToTCFAnnotations implements ConvertAnnotations,ConversionErrorMessage {

    private TextCorpusStored textCorpusStored = null;
    private AnnotationLayerFinder givenToolTagSetVocabularies = null;
    private List<AnnotationInterpreter> givenAnnotations = new ArrayList<AnnotationInterpreter>();
    private CharOffsetToTokenIdMapper charOffsetToTokenIdMapper = null;

    public ConvertToTCFAnnotations(InputStream input) throws ConversionException, LifException {
        toLanguage(Values.LANG_EN.getName());
    }

    public void toLayers(AnnotationLayerFinder layer, List<AnnotationInterpreter> annotationlist) throws ConversionException, LifException, VocabularyMappingException {
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
            Logger.getLogger(ConvertToTCFAnnotations.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(MESSAGE_INVALID_LIF);
        } catch (ConversionException ex) {
            Logger.getLogger(ConvertToTCFAnnotations.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(MESSAGE_CONVERSION_FAILED);
        } catch (VocabularyMappingException ex) {
            Logger.getLogger(ConvertToTCFAnnotations.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(MESSAGE_VOCABULARY_CONVERSION_FAILED);
        }

    }

    public void toLanguage(String language) throws ConversionException {
        try {
            textCorpusStored = new TextCorpusStored(language);
        } catch (Exception ex) {
            Logger.getLogger(ConvertToTCFAnnotations.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(MESSAGE_LANGUAGE_CONVERSION_FAILED);
        } finally {
            textCorpusStored = new TextCorpusStored(Values.LANG_EN.getName());
        }
    }

    public void toText(String text) throws ConversionException {
        try {
            textCorpusStored.createTextLayer().addText(text.replaceAll("\n", ""));
        } catch (Exception ex) {
            Logger.getLogger(ConvertToTCFAnnotations.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(MESSAGE_TEXT_CONVERSION_FAILED);
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
        Map<String, Token> tokenIdToken = new HashMap<String, Token>();
        Map<String, Token> lifTokenIdTcfToken = new HashMap<String, Token>();
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
                        posLayer = textCorpusStored.createPosTagsLayer(Values.TCF_POSTAGS_TAGSET_PENNTB.getName());
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
                        lifTokenIdTcfToken.put(annotationInterpreter.getId(), token);
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
            charOffsetToTokenIdMapper = new CharOffsetToTokenIdMapper(startIdTokenIdMapper, tokenIdToken, lifTokenIdTcfToken);
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
                throw new ConversionException(MESSAGE_STARTID_TOKEN_CONNECTION_NOT_FOUND);
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
                throw new ConversionException(MESSAGE_TOKEN_LAYER_REQUIRED_FOR_SENTENCE_LAYER);
            }
        } catch (Exception ex) {
            Logger.getLogger(ConvertToTCFAnnotations.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(MESSAGE_TOKEN_LAYER_REQUIRED_FOR_SENTENCE_LAYER);
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

    //extremely dirty code. addressed many issues of LIF. needs to be refactor later
    public void toNameEntity() throws ConversionException, VocabularyMappingException, LifException {
        LifNameEntityLayer lifNameEntityLayer = new LifNameEntityLayerStored(givenAnnotations);
        try {
            if (textCorpusStored.getTokensLayer().isEmpty()) {
                throw new ConversionException(MESSAGE_TOKEN_LAYER_REQUIRED_FOR_NAMEENTITY_LAYER);
            }
        } catch (Exception ex) {
            Logger.getLogger(ConvertToTCFAnnotations.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(MESSAGE_TOKEN_LAYER_REQUIRED_FOR_NAMEENTITY_LAYER);
        }

        NamedEntitiesLayer namedEntitiesLayer = textCorpusStored.createNamedEntitiesLayer("");

        for (LifNameEntity lifNameEntity : lifNameEntityLayer.getNameEntityList()) {
            List<String> tokenIds = charOffsetToTokenIdMapper.getTokenIdsFromStartIds(lifNameEntity.getStart(), lifNameEntity.getEnd());
            List<Token> tokenList = new ArrayList<Token>();
            for (String tokenId : tokenIds) {
                Token token = charOffsetToTokenIdMapper.getToken(tokenId);
                tokenList.add(token);
            }
            if (!tokenList.isEmpty()) {
                String nameEntityKey = lifNameEntity.getCategory();
                if (tokenList.size() == 1) {
                    namedEntitiesLayer.addEntity(nameEntityKey, tokenList.get(0));
                } else {
                    namedEntitiesLayer.addEntity(nameEntityKey, tokenList);
                }
            }
        }

    }

    //extremely dirty code. addressed many issues of LIF. needs to be refactor later
    public void toConstituentParser() throws ConversionException, LifException {
        LifConstituentParser lifConstituentParser = new LifConstituentParserStored(givenAnnotations);
        if (!lifConstituentParser.getTokenList().isEmpty()) {
            this.givenAnnotations = lifConstituentParser.getTokenList();
            this.toToken();
        }
        if (!lifConstituentParser.getSentenceList().isEmpty()) {
            this.givenAnnotations = new ArrayList<AnnotationInterpreter>();
            this.givenAnnotations = lifConstituentParser.getSentenceList();
            this.toSentences();
        }

        ConstituentParsingLayer constituentParsingLayer = textCorpusStored.createConstituentParsingLayer(Values.TCF_PARSING_TAGSET_PENNTB.getName());

        try {
            for (Long parseIndex : lifConstituentParser.getParseIndexs()) {
                LifConstituent lifRoot = lifConstituentParser.getRoot(parseIndex);
                List<LifConstituent> lifConstituents = lifConstituentParser.getConstituentEntities(parseIndex);
                ConstituentParse tcfTreeBuild = new TcfConstituentsTreeBuild(lifRoot, lifConstituents,
                        lifConstituentParser.getTokenIdStartIdMapper(),
                        charOffsetToTokenIdMapper, constituentParsingLayer);
                Constituent tcfRoot = tcfTreeBuild.getRoot();
                constituentParsingLayer.addParse(tcfRoot);
            }

        } catch (Exception ex) {
            Logger.getLogger(ConvertToTCFAnnotations.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(MESSAGE_CONSTITUENT_CONVERSION_FAILED);
        }
    }

    //extremely dirty code. addressed many issues of LIF. needs to be refactor later
    public void toDependencyParser() throws ConversionException, LifException {
        LifDependencyParser lifDependencyParser = new LifDependencyParserStored(givenAnnotations);
        if (!lifDependencyParser.getTokenList().isEmpty()) {
            this.givenAnnotations = lifDependencyParser.getTokenList();
            this.toToken();
        }
        if (!lifDependencyParser.getSentenceList().isEmpty()) {
            this.givenAnnotations = new ArrayList<AnnotationInterpreter>();
            this.givenAnnotations = lifDependencyParser.getSentenceList();
            this.toSentences();
        }

        DependencyParsingLayer dependencyParsingLayer = textCorpusStored.createDependencyParsingLayer(Values.TCF_DEPPARSING_TAGSET_STANFORD.getName(), false, true);
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
        } catch (Exception ex) {
            Logger.getLogger(ConvertToTCFAnnotations.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(MESSAGE_DEPENDENCY_CONVERSION_FAILED);
        }
    }

    //extremely dirty changes since Lif is invalid but we have to continue demo
    public void toCoreferenceResolver() throws ConversionException, LifException {
        LifReferenceLayer lifRefererenceLayer = new LifRefererenceLayerStored(givenAnnotations);
        this.givenAnnotations = lifRefererenceLayer.getTokenList();
        this.toToken();

        if (lifRefererenceLayer.getCorferenceAnnotations().isEmpty() || lifRefererenceLayer.getMarkableAnnotations().isEmpty()) {
            return;
        }

        ReferencesLayer refsLayer = textCorpusStored.createReferencesLayer(null, null, null);

       try {
        Map<String, Reference> markIdReference = new HashMap<String, Reference>();
        List<Reference> tcfReferences = new ArrayList<Reference>();
        Map<String, Token> lifTokenIdToken = charOffsetToTokenIdMapper.getLifTokenIdTcfToken();
        for (String lifMarkableId : lifRefererenceLayer.getMarkableAnnotations().keySet()) {
            LifMarkable lifMarkable = lifRefererenceLayer.getMarkableAnnotations().get(lifMarkableId);
            List<String> lifTokenIds = lifMarkable.getTargets();
            List<Token> tcftokens = new ArrayList<Token>();
            for (String lifTokenId : lifTokenIds) {
                if (lifTokenIdToken.containsKey(lifTokenId)) {
                    tcftokens.add(lifTokenIdToken.get(lifTokenId));
                }
            }
            Reference reference = refsLayer.createReference(tcftokens);
            markIdReference.put(lifMarkableId, reference);
            tcfReferences.add(reference);
        }

        for (String lifCorferId : lifRefererenceLayer.getCorferenceAnnotations().keySet()) {
            LifReference lifReference = lifRefererenceLayer.getCorferenceAnnotations().get(lifCorferId);
            String repMarkableId = lifReference.getRepresentative();
            if (markIdReference.containsKey(repMarkableId)) {
                Reference refRep = markIdReference.get(repMarkableId);
                Reference[] refMentions = new Reference[lifReference.getMentions().size() - 1];
                Integer index = 0;
                boolean flag = false;
                for (String mentionMarkableId : lifReference.getMentions()) {
                    if (!mentionMarkableId.equals(repMarkableId) && markIdReference.containsKey(mentionMarkableId)) {
                        refMentions[index++] = markIdReference.get(mentionMarkableId);
                        flag = true;
                    }

                }
                if (flag) {
                    refsLayer.addRelation(refRep, "anaphoric", refMentions);
                }

            }

        }
        refsLayer.addReferent(tcfReferences);
        } catch (Exception ex) {
            Logger.getLogger(ConvertToTCFAnnotations.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(MESSAGE_COREFERENCE_CONVERSION_FAILED);
        }
    }

    public void toTextSource(String fileString) {
        TextSourceLayer textSourceLayer = textCorpusStored.createTextSourceLayer();
        textSourceLayer.addText(fileString);
    }

    public void process(OutputStream os) throws WLFormatException {
        WLData wlData = new WLData(textCorpusStored);
        WLDObjector.write(wlData, os);
    }

    public TextCorpusStored getTextCorpusStored() {
        return textCorpusStored;
    }

    public void setGivenAnnotations(List<AnnotationInterpreter> givenAnnotations) {
        this.givenAnnotations = givenAnnotations;
    }
}
