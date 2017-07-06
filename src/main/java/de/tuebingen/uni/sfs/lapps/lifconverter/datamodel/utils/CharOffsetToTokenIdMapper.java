/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.utils;

import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.exceptions.ConversionException;
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author felahi
 */
public class CharOffsetToTokenIdMapper {

    private Long startId = new Long(0);
    private String tokenId = null;
    private Token token = null;
    private Map<Long, String> startIdTokenIdMapper = new HashMap<Long, String>();
    private Map<String, Token> tokenIdToken = new HashMap<String, Token>();

    public CharOffsetToTokenIdMapper(Map<Long, String> startIdTokenIdMapper, Map<String, Token> tokenIdToken, Long start) {
        this.startIdTokenIdMapper = startIdTokenIdMapper;
        this.tokenIdToken = tokenIdToken;
        this.startId = start;
    }

    public CharOffsetToTokenIdMapper(Map<Long, String> startIdTokenIdMapper, Map<String, Token> tokenIdToken) {
        this.startIdTokenIdMapper = startIdTokenIdMapper;
        this.tokenIdToken = tokenIdToken;
    }

    public List<String> getTokenIdsFromStartIds(Long start, Long end) throws ConversionException {
        List<String> tokenIds = new ArrayList<String>();
        for (Long charId = start; charId <end; charId++) {
            if (this.startIdTokenIdMapper.containsKey(charId)) {
                String tokenId = this.startIdTokenIdMapper.get(charId);
                tokenIds.add(tokenId);
            }
        }
        return tokenIds;
    }

    public List<Token> getTokenIdsFromCharIds(Set<Long> charOffsetIds) throws Exception {
        List<Token> tokens = new ArrayList<Token>();
        for (Long charId : charOffsetIds) {
            Token token = this.startIdToToken(charId);
            tokens.add(token);
        }
        return tokens;
    }

    public Token startIdToToken(Long start) throws ConversionException {
        this.startId = start;
        if (this.isTokenExist()) {
            return this.token;
        } else {
            throw new ConversionException("fail to map from character offset to token id!!");
        }
    }

    /*public Token startIdToToken(Long start) throws Exception {
        this.startId = start;
        try {
            if (this.isTokenExist()) {
                return this.token;
            }
        } catch (Exception e) {
            throw new Exception("start id to Token id mapping failed!!");
        }
        return null;
    }*/
    public boolean isTokenExist() {

        if (isTokenIdExist()) {
            if (tokenIdToken.containsKey(tokenId)) {
                this.token = tokenIdToken.get(tokenId);
            }
            return true;
        }

        return false;
    }

    public boolean isTokenIdExist() {

        if (startIdTokenIdMapper.containsKey(startId)) {
            this.tokenId = startIdTokenIdMapper.get(startId);
            return true;
        }

        return false;
    }

    public boolean isCharIdExists() {
        if (startIdTokenIdMapper.containsKey(startId)) {
            return true;
        }
        return false;
    }

    public Long getStartId() {
        return startId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public Token getToken() {
        return token;
    }

    public Token getToken(String tokenId) {
        return this.tokenIdToken.get(tokenId);
    }

}
