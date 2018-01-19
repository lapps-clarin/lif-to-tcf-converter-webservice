package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import de.tuebingen.uni.sfs.lapps.lifconverter.core.impl.ConvertToTCFAnnotations;
import de.tuebingen.uni.sfs.lapps.core.layer.api.AnnotationLayerFinder;
import de.tuebingen.uni.sfs.lapps.exceptions.VocabularyMappingException;
import de.tuebingen.uni.sfs.lapps.core.layer.impl.LifAnnotationProcess;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.tuebingen.uni.sfs.lapps.lifconverter.core.impl.ConvertVocabulary;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.api.ConverterFormat;

public class ConverterTool implements ConverterFormat {

    private ConvertToTCFAnnotations convertedDataModel = null;
    private LifAnnotationProcess givenDataModel = null;
    public static final String PARAMETER_PATH = "/models/parameterlist.init";
    public static final String VOCABULARY_PATH = "/models/annotation_conversion.init";

    public ConverterTool() throws ConversionException, VocabularyMappingException {
        new ConvertVocabulary(PARAMETER_PATH, VOCABULARY_PATH);
    }

    public synchronized ConvertToTCFAnnotations convertFormat(LifAnnotationProcess lifDataModel, InputStream input) throws Exception {
        convertedDataModel = new ConvertToTCFAnnotations(input);
        givenDataModel = lifDataModel;
        convertedDataModel.toLanguage(givenDataModel.getLanguage());
        convertedDataModel.toText(givenDataModel.getText());
        convertedDataModel.toTextSource(givenDataModel.getFileString());
        try {
            convertAnnotationLayers();
            //this.display();
        } catch (ConversionException conExp) {
            Logger.getLogger(ConverterTool.class.getName()).log(Level.SEVERE, null, conExp);
        } catch (VocabularyMappingException vocExp) {
            Logger.getLogger(ConverterTool.class.getName()).log(Level.SEVERE, null, vocExp);
        }
        return convertedDataModel;
    }

    protected void convertAnnotationLayers() throws VocabularyMappingException, ConversionException, Exception {
        for (Integer layerIndex : givenDataModel.getSortedLayer()) {
            AnnotationLayerFinder tcfLayer = converAnnotationlayervocabularies(layerIndex);
            convertAnnotations(tcfLayer, layerIndex);
        }
    }

    protected void convertAnnotations(AnnotationLayerFinder tcfLayer, Integer layerIndex) throws ConversionException, Exception {
        convertedDataModel.toLayers(tcfLayer, givenDataModel.getAnnotationLayerData(layerIndex));
    }

    protected AnnotationLayerFinder converAnnotationlayervocabularies(Integer layerIndex) throws VocabularyMappingException {
        AnnotationLayerFinder lifLayer = givenDataModel.getIndexAnnotationLayer(layerIndex);
        AnnotationLayerFinder tcfLayer = new ConvertVocabulary(lifLayer);
        return tcfLayer;
    }

    private void display() {

        try {
            if (convertedDataModel.getTextCorpusStored().getTextLayer() != null) {
                System.out.println("Text Layer");
                System.out.println(convertedDataModel.getTextCorpusStored().getTextLayer().toString());
            }
            if (convertedDataModel.getTextCorpusStored().getTokensLayer() != null) {
                System.out.println("Token Layer");
                System.out.println(convertedDataModel.getTextCorpusStored().getTokensLayer().toString());
            }
            if (convertedDataModel.getTextCorpusStored().getDependencyParsingLayer() != null) {
                System.out.println("Dependency Parser Layer");
                System.out.println(convertedDataModel.getTextCorpusStored().getDependencyParsingLayer().getParse(0).toString());
            }
            if (convertedDataModel.getTextCorpusStored().getPosTagsLayer() != null) {
                System.out.println("PosTags Layer");
                System.out.println(convertedDataModel.getTextCorpusStored().getPosTagsLayer().toString());
            }
            if (convertedDataModel.getTextCorpusStored().getLemmasLayer() != null) {
                System.out.println("Lemma Layer");
                System.out.println(convertedDataModel.getTextCorpusStored().getLemmasLayer().toString());
            }
            if (convertedDataModel.getTextCorpusStored().getSentencesLayer() != null) {
                System.out.println("Sentence Layer");
                System.out.println(convertedDataModel.getTextCorpusStored().getSentencesLayer().toString());
            }
            if (convertedDataModel.getTextCorpusStored().getNamedEntitiesLayer() != null) {
                System.out.println("NameEntitty Layer");
                System.out.println(convertedDataModel.getTextCorpusStored().getNamedEntitiesLayer().toString());
            }
            if (convertedDataModel.getTextCorpusStored().getConstituentParsingLayer() != null) {
                System.out.println("Constituent Parser Layer");
                System.out.println(convertedDataModel.getTextCorpusStored().getConstituentParsingLayer().toString());
            }
            if (convertedDataModel.getTextCorpusStored().getReferencesLayer() != null) {
                System.out.println("References Layer");
                System.out.println(convertedDataModel.getTextCorpusStored().getReferencesLayer().toString());
            }
            if (convertedDataModel.getTextCorpusStored().getTextSourceLayer() != null) {
                System.out.println("Text Source Layer");
                System.out.println(convertedDataModel.getTextCorpusStored().getTextSourceLayer().getText().toString().substring(0, 99));
            }

        } catch (IndexOutOfBoundsException exIndex) {
            Logger.getLogger(ConverterTool.class.getName()).log(Level.SEVERE, null, exIndex);
        } catch (Exception ex) {
            Logger.getLogger(ConverterTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
