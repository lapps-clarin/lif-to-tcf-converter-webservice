/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.clarind.profiler.Values;
import de.tuebingen.uni.sfs.lapps.core.impl.annotation.LifTokenPosLemmaStored;
import static de.tuebingen.uni.sfs.lapps.lifconverter.constants.ConversionErrorMessage.MESSAGE_STARTID_TOKEN_CONNECTION_NOT_FOUND;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.utils.CharOffsetToTokenIdMapper;
import de.tuebingen.uni.sfs.lapps.utils.AnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.utils.DuplicateChecker;
import eu.clarin.weblicht.wlfxb.tc.api.LemmasLayer;
import eu.clarin.weblicht.wlfxb.tc.api.PosTagsLayer;
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.tc.api.TokensLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusStored;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class ConvertTokenLevelInfo {

    private CharOffsetToTokenIdMapper charOffsetToTokenIdMapper = null;
    private TextCorpusStored textCorpusStored = null;
    private TokensLayer tcfTokensLayer = null;
    private PosTagsLayer tcfPosLayer = null;
    private LemmasLayer tcfLemmaLayer = null;
    private List<AnnotationInterpreter> givenAnnotations = new ArrayList<AnnotationInterpreter>();

    public ConvertTokenLevelInfo(TextCorpusStored textCorpusStored, List<AnnotationInterpreter> givenAnnotations) throws ConversionException {
        this.textCorpusStored = textCorpusStored;
        this.givenAnnotations = givenAnnotations;
        this.toToken();
    }

    //this is a not good implementation. It will be changed later
    public void toToken() throws ConversionException {
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
                        tcfTokensLayer = textCorpusStored.createTokensLayer();
                        wordFlag = true;
                    }
                    if (lifToken.getPos() != null) {
                        tcfPosLayer = textCorpusStored.createPosTagsLayer(Values.TCF_POSTAGS_TAGSET_PENNTB.getName());
                        posFlag = true;
                    }
                    if (lifToken.getLemma() != null) {
                        tcfLemmaLayer = textCorpusStored.createLemmasLayer();
                        lemmaFlag = true;
                    }
                    flag = false;
                }

                if (!duplicateChecker.isDuplicate(annotationInterpreter.getStart())) {
                    if (wordFlag) {
                        token = tcfTokensLayer.addToken(lifToken.getWord());
                        startIdTokenIdMapper.put(annotationInterpreter.getStart(), token.getID());
                        tokenIdToken.put(token.getID(), token);
                        lifTokenIdTcfToken.put(annotationInterpreter.getId(), token);
                    }

                    if (posFlag) {
                        token = findConnectedToken(annotationInterpreter.getStart(), token);
                        tcfPosLayer.addTag(lifToken.getPos(), token);
                    }

                    if (lemmaFlag) {
                        token = findConnectedToken(annotationInterpreter.getStart(), token);
                        tcfLemmaLayer.addLemma(lifToken.getLemma(), token);
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

    public CharOffsetToTokenIdMapper getCharOffsetToTokenIdMapper() {
        return charOffsetToTokenIdMapper;
    }

    public TokensLayer getTokensLayer() {
        return tcfTokensLayer;
    }

    public PosTagsLayer getPosLayer() {
        return tcfPosLayer;
    }

    public LemmasLayer getLemmaLayer() {
        return tcfLemmaLayer;
    }
}
