/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.api;

import de.tuebingen.uni.sfs.lapps.core.impl.layer.LifSingleLayer;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.VocabularyMappingException;
import eu.clarin.weblicht.wlfxb.tc.api.ConstituentParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.api.DependencyParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.api.LemmasLayer;
import eu.clarin.weblicht.wlfxb.tc.api.NamedEntitiesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.PosTagsLayer;
import eu.clarin.weblicht.wlfxb.tc.api.ReferencesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.SentencesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.TokensLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusStored;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public interface TcfFormat {

    public String toTcfText(String text) throws ConversionException;

    public String toTcfLanguage(String language) throws Exception;

    public TokensLayer toTcfToken() throws Exception;

    public PosTagsLayer toTcfPos() throws Exception;

    public LemmasLayer toTcfLemma() throws Exception;

    public SentencesLayer toTcfSentences() throws Exception;

    public DependencyParsingLayer toTcfDependencyParser() throws Exception;

    public ConstituentParsingLayer toTcfConstituentParser() throws Exception;

    public NamedEntitiesLayer toTcfNameEntity() throws Exception;

    public ReferencesLayer toTcfCoreferenceResolver() throws Exception;

    public void toTcfTextSource(String fileString) throws Exception;

    public TextCorpusStored getTextCorpusStored();

}
