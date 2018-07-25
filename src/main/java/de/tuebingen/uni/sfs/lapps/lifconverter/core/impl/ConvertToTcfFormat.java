/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.lapps.core.api.annotations.LifDependencyParser;
import de.tuebingen.uni.sfs.lapps.core.api.annotations.LifMarkable;
import de.tuebingen.uni.sfs.lapps.core.api.annotations.LifReference;
import de.tuebingen.uni.sfs.lapps.core.api.annotations.LifReferenceLayer;
import de.tuebingen.uni.sfs.lapps.utils.AnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.utils.DependencyEntityInfo;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import de.tuebingen.uni.sfs.lapps.lifconverter.utils.TcfConstituentsTreeBuild;
import eu.clarin.weblicht.wlfxb.tc.api.Constituent;
import eu.clarin.weblicht.wlfxb.tc.api.ConstituentParse;
import eu.clarin.weblicht.wlfxb.tc.api.ConstituentParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.api.Dependency;
import eu.clarin.weblicht.wlfxb.tc.api.DependencyParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.api.NamedEntitiesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.SentencesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.TextSourceLayer;
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.tc.api.TokensLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusStored;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.tuebingen.uni.sfs.lapps.lifconverter.utils.CharOffsetToTokenIdMapper;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import eu.clarin.weblicht.wlfxb.tc.api.Reference;
import eu.clarin.weblicht.wlfxb.tc.api.ReferencesLayer;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.VocabularyMappingException;
import de.tuebingen.uni.sfs.lapps.core.api.annotations.LifConstituentParser;
import de.tuebingen.uni.sfs.lapps.core.api.annotations.LifNameEntity;
import de.tuebingen.uni.sfs.lapps.core.api.annotations.LifNameEntityLayer;
import de.tuebingen.uni.sfs.lapps.core.api.annotations.LifSentenceLayer;
import de.tuebingen.uni.sfs.lapps.core.api.annotations.LifTokenLayer;
import de.tuebingen.uni.sfs.lapps.core.api.annotations.LifTokenPosLemma;
import de.tuebingen.uni.sfs.lapps.core.api.profiler.LifFormat;
import de.tuebingen.uni.sfs.lapps.lifconverter.constants.ConversionErrorMessage;
import de.tuebingen.uni.sfs.lapps.lifconverter.constants.TcfConstants;
import static de.tuebingen.uni.sfs.lapps.lifconverter.constants.TcfConstants.ANAPHORIC;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.api.LayerConverter;
import de.tuebingen.uni.sfs.lapps.lifconverter.utils.JsonPrettyPrint;
import de.tuebingen.uni.sfs.lapps.utils.DuplicateChecker;
import eu.clarin.weblicht.wlfxb.tc.api.LemmasLayer;
import eu.clarin.weblicht.wlfxb.tc.api.PosTagsLayer;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author felahi
 */
public class ConvertToTcfFormat implements LayerConverter, ConversionErrorMessage {

    private TextCorpusStored textCorpusStored = null;
    private CharOffsetToTokenIdMapper charOffsetToTokenIdMapper = null;
    private Map<String, Long> tokenIdStartIdMapper = new HashMap<String, Long>();
    private Map<String, Token> tokenIdTokenMapper = new HashMap<String, Token>();

    public ConvertToTcfFormat(LifFormat lappsLifFormat) throws ConversionException, LifException, VocabularyMappingException {
        textCorpusStored = new TextCorpusStored(toTcfLanguage(lappsLifFormat.getLanguage()));
        if (lappsLifFormat.getText() != null) {
            toTcfText(lappsLifFormat.getText());
        }
        if (lappsLifFormat.getLifTokenLayer() != null) {
            toTcfToken(lappsLifFormat.getLifTokenLayer());
        }
        if (lappsLifFormat.getLifSentenceLayer() != null) {
            toTcfSentences(lappsLifFormat.getLifSentenceLayer());
        }
        if (lappsLifFormat.getLifNameEntityLayer() != null) {
            this.toTcfNameEntity(lappsLifFormat.getLifNameEntityLayer());
        }
        if (lappsLifFormat.getLifDependencyParser() != null) {
            toTcfDependencyParser(lappsLifFormat.getLifDependencyParser());
        }
        if (lappsLifFormat.getLifConstituentParser() != null) {
            this.toTcfConstituentParser(lappsLifFormat.getLifConstituentParser());
        }
        if (lappsLifFormat.getLifRefererenceLayer() != null) {
            this.toTcfCoreferenceResolver(lappsLifFormat.getLifRefererenceLayer());
        }
        /*if (lappsLifFormat.getFileString() != null) {
            toTcfTextSource(lappsLifFormat.getFileString());
        }*/
    }

    @Override
    public String toTcfLanguage(String language) throws ConversionException {
        if (language != null) {
            if (language.equals("en")) {
                return TcfConstants.LANG_EN;
            }
        }
        return TcfConstants.LANG_EN;
    }

    @Override
    public String toTcfText(String text) throws ConversionException {
        String modifiedText = text.replaceAll("\n", "");
        try {
            textCorpusStored.createTextLayer().addText(modifiedText);
        } catch (Exception ex) {
            Logger.getLogger(ConvertToTcfFormat.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(MESSAGE_TEXT_CONVERSION_FAILED);
        }
        return modifiedText;
    }

    /*@Override
    public void toTcfTextSource(String fileString) throws ConversionException {
        try {
            fileString = JsonPrettyPrint.formatJsonString(fileString);
            TextSourceLayer textSourceLayer = textCorpusStored.createTextSourceLayer();
            textSourceLayer.addText(fileString);
        } catch (Exception ex) {
            Logger.getLogger(ConvertToTcfFormat.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(ex.getMessage());
        }
    }*/

    @Override
    public TokensLayer toTcfToken(LifTokenLayer lifTokenLayer) throws ConversionException {
        TokensLayer tcfTokensLayer = null;
        PosTagsLayer tcfPosLayer = null;
        LemmasLayer tcfLemmaLayer = null;
        DuplicateChecker duplicateChecker = new DuplicateChecker();
        Map<String, Token> lifTokenIdToTcfToken = new HashMap<String, Token>();
        Map<Long, Token> lifStartIdToTcfToken = new HashMap<Long, Token>();

        if (lifTokenLayer.isTokenLayer()) {
            tcfTokensLayer = textCorpusStored.createTokensLayer();
        }
        if (lifTokenLayer.isPosLayer()) {
            tcfPosLayer = textCorpusStored.createPosTagsLayer(TcfConstants.TCF_POSTAGS_TAGSET_PENNTB);
        }
        if (lifTokenLayer.isLemmaLayer()) {
            tcfLemmaLayer = textCorpusStored.createLemmasLayer();
        }

        for (LifTokenPosLemma lifToken : lifTokenLayer.getTokenList()) {
            Token token = null;
            if (!lifToken.getFeatures().isEmpty()) {
                if (!duplicateChecker.isDuplicate(lifToken.getStart())) {
                    if (lifTokenLayer.isTokenLayer()) {
                        token = tcfTokensLayer.addToken(lifToken.getWord());
                        lifStartIdToTcfToken.put(lifToken.getStart(), token);
                        lifTokenIdToTcfToken.put(lifToken.getId(), token);
                    }

                    if (lifTokenLayer.isPosLayer()) {
                        tcfPosLayer.addTag(lifToken.getPos(), token);
                    }

                    if (lifTokenLayer.isLemmaLayer()) {
                        tcfLemmaLayer.addLemma(lifToken.getLemma(), token);
                    }
                }

            }
        }
        if (lifTokenLayer.isTokenLayer()) {
            charOffsetToTokenIdMapper = new CharOffsetToTokenIdMapper(lifTokenIdToTcfToken, lifStartIdToTcfToken);
        }
        return tcfTokensLayer;
    }

    @Override
    public SentencesLayer toTcfSentences(LifSentenceLayer lifSentenceLayer) throws ConversionException, LifException {
        SentencesLayer sentencesLayer = textCorpusStored.createSentencesLayer();

        if (textCorpusStored.getTokensLayer() != null) {
            for (AnnotationInterpreter lifSentence : lifSentenceLayer.getSentenceList()) {
                List<Token> sentenceTokens = new ArrayList<Token>();
                List<Token> tokens = charOffsetToTokenIdMapper.getTcfTokens(lifSentence.getStart(), lifSentence.getEnd());
                for (Token token : tokens) {
                    sentenceTokens.add(token);
                }
                sentencesLayer.addSentence(sentenceTokens);
            }
        } else {
            throw new ConversionException(MESSAGE_TOKEN_LAYER_REQUIRED_FOR_SENTENCE_LAYER);
        }

        return sentencesLayer;
    }

    @Override
    public NamedEntitiesLayer toTcfNameEntity(LifNameEntityLayer lifNameEntityLayer) throws ConversionException, VocabularyMappingException, LifException {
        NamedEntitiesLayer namedEntitiesLayer = textCorpusStored.createNamedEntitiesLayer("");

        for (LifNameEntity lifNameEntity : lifNameEntityLayer.getNameEntityList()) {
            List<Token> tokens = charOffsetToTokenIdMapper.getTcfTokens(lifNameEntity.getStart(), lifNameEntity.getEnd());
            CopyOnWriteArrayList<Token> tokenList = new CopyOnWriteArrayList<Token>(tokens);
            if (!tokenList.isEmpty()) {
                String nameEntityKey = lifNameEntity.getCategory();
                if (tokenList.size() == 1) {
                    namedEntitiesLayer.addEntity(nameEntityKey, tokenList.get(0));
                } else {
                    namedEntitiesLayer.addEntity(nameEntityKey, tokenList);
                }
            }
        }
        return namedEntitiesLayer;
    }

    @Override
    public ConstituentParsingLayer toTcfConstituentParser(LifConstituentParser lifConstituentParser) throws ConversionException, LifException {
        ConstituentParsingLayer constituentParsingLayer = textCorpusStored.createConstituentParsingLayer(TcfConstants.TCF_PARSING_TAGSET_PENNTB);

        try {
            for (Long parseIndex : lifConstituentParser.getParseIndexs()) {
                ConstituentParse tcfTreeBuild = new TcfConstituentsTreeBuild(lifConstituentParser.getRoot(parseIndex),
                        lifConstituentParser.getConstituentEntities(parseIndex),
                        charOffsetToTokenIdMapper,
                        constituentParsingLayer);
                Constituent tcfRoot = tcfTreeBuild.getRoot();
                constituentParsingLayer.addParse(tcfRoot);
            }

        } catch (Exception ex) {
            Logger.getLogger(ConvertToTcfFormat.class.getName()).log(Level.SEVERE, null, ex);
            //throw new ConversionException(MESSAGE_CONSTITUENT_CONVERSION_FAILED);
            throw new ConversionException(ex.getMessage());
        }
        return constituentParsingLayer;
    }

    @Override
    public DependencyParsingLayer toTcfDependencyParser(LifDependencyParser lifDependencyParser) throws ConversionException, LifException {
        DependencyParsingLayer dependencyParsingLayer = textCorpusStored.createDependencyParsingLayer(TcfConstants.TCF_DEPPARSING_TAGSET_STANFORD, false, true);

        try {
            List<Dependency> tcfDependencyList = new ArrayList<Dependency>();
            for (Long parseIndex : lifDependencyParser.getParseIndexs()) {
                for (DependencyEntityInfo dependencyEntity : lifDependencyParser.getDependencyEntities(parseIndex)) {
                    Token govonor = null, dependent = null;
                    if (dependencyEntity.getGovIDs() != null && dependencyEntity.getDepIDs() != null) {
                        govonor = this.charOffsetToTokenIdMapper.getTcfToken(dependencyEntity.getGovIDs());
                        dependent = this.charOffsetToTokenIdMapper.getTcfToken(dependencyEntity.getDepIDs());
                        Dependency dependency = dependencyParsingLayer.createDependency(dependencyEntity.getFunc(), dependent, govonor);
                        tcfDependencyList.add(dependency);
                    } else if (dependencyEntity.getDepIDs() != null) {
                        dependent = this.charOffsetToTokenIdMapper.getTcfToken(dependencyEntity.getDepIDs());
                        Dependency dependency = dependencyParsingLayer.createDependency(dependencyEntity.getFunc(), dependent);
                        tcfDependencyList.add(dependency);
                    }

                }
                dependencyParsingLayer.addParse(tcfDependencyList);
            }
        } catch (Exception ex) {
            Logger.getLogger(ConvertToTcfFormat.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(MESSAGE_DEPENDENCY_CONVERSION_FAILED);
        }
        return dependencyParsingLayer;
    }

    @Override
    public ReferencesLayer toTcfCoreferenceResolver(LifReferenceLayer lifRefererenceLayer) throws ConversionException, LifException {
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
                        refsLayer.addRelation(refRep, ANAPHORIC, refMentions);
                    }

                }
            }
            refsLayer.addReferent(tcfReferences);
        } catch (Exception ex) {
            Logger.getLogger(ConvertToTcfFormat.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(MESSAGE_COREFERENCE_CONVERSION_FAILED);
        }
        return refsLayer;
    }

    @Override
    public TextCorpusStored getTextCorpusStored() {
        return textCorpusStored;
    }

}
