/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.api;

import de.tuebingen.uni.sfs.lapps.core.layer.impl.LIFAnnotationLayer;
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
/**
 *
 * @author Mohammad Fazleh Elahi
 */
public interface ConvertAnnotations {
    public void toLayer(LIFAnnotationLayer lifLayer) throws LifException, ConversionException, VocabularyMappingException;

    public String toText(String text) throws ConversionException;

    public String toLanguage(String language) throws Exception;

    public TokensLayer toToken(LIFAnnotationLayer lifTokenLayer) throws Exception;

    public PosTagsLayer toPos(LIFAnnotationLayer lifPosLayer) throws Exception;

    public LemmasLayer toLemma(LIFAnnotationLayer lifLemmaLayer) throws Exception;

    public SentencesLayer toSentences(LIFAnnotationLayer lifSentenceLayer) throws Exception;

    public DependencyParsingLayer toDependencyParser(LIFAnnotationLayer lifDependencyLayer) throws Exception;

    public ConstituentParsingLayer toConstituentParser(LIFAnnotationLayer lifConstituentParserLayer) throws Exception;

    public NamedEntitiesLayer toNameEntity(LIFAnnotationLayer lifNamedEntityParserLayer) throws Exception;
    
    public ReferencesLayer toCoreferenceResolver(LIFAnnotationLayer lifCoreferenceLayer) throws Exception;
    
    public void toTextSource(String fileString) throws Exception;
    
}
