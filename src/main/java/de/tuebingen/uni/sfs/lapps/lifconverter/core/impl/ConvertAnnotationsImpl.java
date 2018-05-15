/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.clarind.profiler.Values;
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
import de.tuebingen.uni.sfs.lapps.core.layer.impl.LIFAnnotationLayer;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionErrorMessage;

/**
 *
 * @author felahi
 */
public class ConvertAnnotationsImpl implements ConvertAnnotations, ConversionErrorMessage {

    private TextCorpusStored textCorpusStored = null;
    private CharOffsetToTokenIdMapper charOffsetToTokenIdMapper = null;

    public ConvertAnnotationsImpl(String language, String text, String textSource) throws ConversionException {
        toLanguage(language);
        toText(text);
        toTextSource(textSource);
    }

    @Override
    public void toLayer(LIFAnnotationLayer lifLayer) throws LifException, ConversionException, VocabularyMappingException {
        ConvertVocabulary tcfLayer = new ConvertVocabulary(lifLayer);
        if (tcfLayer.isValid()) {
            String givenLayer = tcfLayer.getLayer();
            //System.out.println("++++++++++++++++++++++++++++" + givenLayer + "++++++++++++++++++++++++++++");
            try {
                if (givenLayer.contains(TextCorpusLayerTag.TOKENS.toString())) {
                    toToken(lifLayer);
                } else if (givenLayer.contains(TextCorpusLayerTag.POSTAGS.toString())) {
                    toPos(lifLayer);
                } else if (givenLayer.contains(TextCorpusLayerTag.LEMMAS.toString())) {
                    toLemma(lifLayer);
                } else if (givenLayer.contains(TextCorpusLayerTag.SENTENCES.toString())) {
                    toSentences(lifLayer);
                } else if (givenLayer.contains(TextCorpusLayerTag.NAMED_ENTITIES.toString())) {
                    this.toNameEntity(lifLayer);
                } else if (givenLayer.contains(TextCorpusLayerTag.PARSING_DEPENDENCY.toString())) {
                    toDependencyParser(lifLayer);
                } else if (givenLayer.contains(TextCorpusLayerTag.PARSING_CONSTITUENT.toString())) {
                    this.toConstituentParser(lifLayer);
                } else if (givenLayer.contains(TextCorpusLayerTag.REFERENCES.toString())) {
                    this.toCoreferenceResolver(lifLayer);
                }
            } catch (LifException ex) {
                Logger.getLogger(ConvertAnnotationsImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new LifException(MESSAGE_INVALID_LIF);
            } catch (ConversionException ex) {
                Logger.getLogger(ConvertAnnotationsImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new ConversionException(MESSAGE_CONVERSION_FAILED);
            } /*catch (VocabularyMappingException ex) {
                Logger.getLogger(ConvertAnnotationsImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new VocabularyMappingException(MESSAGE_VOCABULARY_CONVERSION_FAILED);
            }*/
        }
    }

    public String toLanguage(String language) throws ConversionException {
        if (language.equals(Values.LANG_EN.getName())) {
            textCorpusStored = new TextCorpusStored(language);
        } else {
            throw new ConversionException(MESSAGE_LANGUAGE_CONVERSION_FAILED);
        }
        return Values.LANG_EN.getName();
    }

    public String toText(String text) throws ConversionException {
        String modifiedText = text.replaceAll("\n", "");
        try {
            textCorpusStored.createTextLayer().addText(modifiedText);
        } catch (Exception ex) {
            Logger.getLogger(ConvertAnnotationsImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(MESSAGE_TEXT_CONVERSION_FAILED);
        }
        return modifiedText;
    }
    
    public TokensLayer toToken(LIFAnnotationLayer lifLayer) throws ConversionException {
        TokenLevelInfoConversion tokenLayerConversion = new TokenLevelInfoConversion(textCorpusStored, lifLayer.getAnnotations());
        this.charOffsetToTokenIdMapper = tokenLayerConversion.getCharOffsetToTokenIdMapper();
        return tokenLayerConversion.getTokensLayer();
    }

    public PosTagsLayer toPos(LIFAnnotationLayer lifPosLayer) throws ConversionException {
        TokenLevelInfoConversion tokenLayerConversion = new TokenLevelInfoConversion(textCorpusStored, lifPosLayer.getAnnotations());
        this.charOffsetToTokenIdMapper = tokenLayerConversion.getCharOffsetToTokenIdMapper();
        return tokenLayerConversion.getPosLayer();
    }

    public LemmasLayer toLemma(LIFAnnotationLayer lifLemmaLayer) throws ConversionException {
        TokenLevelInfoConversion tokenLayerConversion = new TokenLevelInfoConversion(textCorpusStored, lifLemmaLayer.getAnnotations());
        this.charOffsetToTokenIdMapper = tokenLayerConversion.getCharOffsetToTokenIdMapper();
        return tokenLayerConversion.getLemmaLayer();
    }

    public SentencesLayer toSentences(LIFAnnotationLayer lifLayer) throws ConversionException {
        List<AnnotationInterpreter> givenAnnotations =lifLayer.getAnnotations();
        Collections.sort(givenAnnotations);

        try {
            if (textCorpusStored.getTokensLayer().isEmpty()) {
                throw new ConversionException(MESSAGE_TOKEN_LAYER_REQUIRED_FOR_SENTENCE_LAYER);
            }
        } catch (Exception ex) {
            Logger.getLogger(ConvertAnnotationsImpl.class.getName()).log(Level.SEVERE, null, ex);
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
        return sentencesLayer;
    }

    //extremely dirty code. addressed many issues of LIF. needs to be refactor later
    public NamedEntitiesLayer toNameEntity(LIFAnnotationLayer lifLayer) throws ConversionException, VocabularyMappingException, LifException {
        List<AnnotationInterpreter> givenAnnotations =lifLayer.getAnnotations();
        LifNameEntityLayer lifNameEntityLayer = new LifNameEntityLayerStored(givenAnnotations);
        TokenLevelInfoConversion tokenLayerConversion = new TokenLevelInfoConversion(textCorpusStored, lifNameEntityLayer.getTokenList());
        this.charOffsetToTokenIdMapper = tokenLayerConversion.getCharOffsetToTokenIdMapper();
        
        try {
            if (textCorpusStored.getTokensLayer().isEmpty()) {
                throw new ConversionException(MESSAGE_TOKEN_LAYER_REQUIRED_FOR_NAMEENTITY_LAYER);
            }
        } catch (Exception ex) {
            Logger.getLogger(ConvertAnnotationsImpl.class.getName()).log(Level.SEVERE, null, ex);
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
       return namedEntitiesLayer;
    }

    //extremely dirty code. addressed many issues of LIF. needs to be refactor later
    public ConstituentParsingLayer toConstituentParser(LIFAnnotationLayer lifLayer) throws ConversionException, LifException {
        List<AnnotationInterpreter> givenAnnotations =lifLayer.getAnnotations();
        LifConstituentParser lifConstituentParser = new LifConstituentParserStored(givenAnnotations);
        TokenLevelInfoConversion tokenLayerConversion = new TokenLevelInfoConversion(textCorpusStored, lifConstituentParser.getTokenList());
        this.charOffsetToTokenIdMapper = tokenLayerConversion.getCharOffsetToTokenIdMapper();
        this.toSentences(new LIFAnnotationLayer(null,lifConstituentParser.getSentenceList()));
        

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
            Logger.getLogger(ConvertAnnotationsImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(MESSAGE_CONSTITUENT_CONVERSION_FAILED);
        }
        return constituentParsingLayer;
    }

    //extremely dirty code. addressed many issues of LIF. needs to be refactor later
    public DependencyParsingLayer toDependencyParser(LIFAnnotationLayer lifLayer) throws ConversionException, LifException {
        List<AnnotationInterpreter> givenAnnotations =lifLayer.getAnnotations();
        LifDependencyParser lifDependencyParser = new LifDependencyParserStored(givenAnnotations);
        TokenLevelInfoConversion tokenLayerConversion = new TokenLevelInfoConversion(textCorpusStored, lifDependencyParser.getTokenList());
        this.charOffsetToTokenIdMapper = tokenLayerConversion.getCharOffsetToTokenIdMapper();
        this.toSentences(new LIFAnnotationLayer(null,lifDependencyParser.getSentenceList()));

        DependencyParsingLayer dependencyParsingLayer = textCorpusStored.createDependencyParsingLayer(Values.TCF_DEPPARSING_TAGSET_STANFORD.getName(), false, true);
        try {
            List<Dependency> tcfDependencyList = new ArrayList<Dependency>();
            for (Long parseIndex : lifDependencyParser.getParseIndexs()) {
                for (DependencyEntityInfo dependencyEntity : lifDependencyParser.getDependencyEntities(parseIndex)) {
                    Token govonor = null, dependent = null;
                    if (dependencyEntity.getGovIDs() != null && dependencyEntity.getDepIDs() != null) {
                        govonor = this.charOffsetToTokenIdMapper.startIdToToken(dependencyEntity.getGovIDs());
                        dependent = this.charOffsetToTokenIdMapper.startIdToToken(dependencyEntity.getDepIDs());
                        Dependency dependency = dependencyParsingLayer.createDependency(dependencyEntity.getFunc(), dependent, govonor);
                        tcfDependencyList.add(dependency);
                    } else if (dependencyEntity.getDepIDs() != null) {
                        dependent = this.charOffsetToTokenIdMapper.startIdToToken(dependencyEntity.getDepIDs());
                        Dependency dependency = dependencyParsingLayer.createDependency(dependencyEntity.getFunc(), dependent);
                        tcfDependencyList.add(dependency);
                    }

                }
                dependencyParsingLayer.addParse(tcfDependencyList);
            }
        } catch (Exception ex) {
            Logger.getLogger(ConvertAnnotationsImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(MESSAGE_DEPENDENCY_CONVERSION_FAILED);
        }
        return dependencyParsingLayer;
    }
    
   

    //extremely dirty changes since Lif is invalid but we have to continue demo
    public ReferencesLayer toCoreferenceResolver(LIFAnnotationLayer lifLayer) throws ConversionException, LifException {
        List<AnnotationInterpreter> givenAnnotations =lifLayer.getAnnotations();
        LifReferenceLayer lifRefererenceLayer = new LifRefererenceLayerStored(givenAnnotations);
        TokenLevelInfoConversion tokenLayerConversion = new TokenLevelInfoConversion(textCorpusStored, lifRefererenceLayer.getTokenList());
        this.charOffsetToTokenIdMapper = tokenLayerConversion.getCharOffsetToTokenIdMapper();

        if (lifRefererenceLayer.getCorferenceAnnotations().isEmpty() || lifRefererenceLayer.getMarkableAnnotations().isEmpty()) {
            return null;
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
            Logger.getLogger(ConvertAnnotationsImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConversionException(MESSAGE_COREFERENCE_CONVERSION_FAILED);
        }
        return refsLayer;
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

}
