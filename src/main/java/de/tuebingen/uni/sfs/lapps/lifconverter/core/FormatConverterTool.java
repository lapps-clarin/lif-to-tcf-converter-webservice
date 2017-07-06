package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.conversion.VocabularyConverter;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.exceptions.VocabularyMappingException;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.model.DataModelLif;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.model.DataModelTcf;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.conversion.AnnotationLayerFinder;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.conversion.DataModelConverter;

public class FormatConverterTool implements DataModelConverter {

    private DataModelTcf convertedDataModel = null;
    private DataModelLif givenDataModel = null;
    public static final String PARAMETER_PATH = "/models/parameterlist.init";
    public static final String VOCABULARY_PATH = "/models/annotation_conversion.init";

    public FormatConverterTool() throws ConversionException, VocabularyMappingException {
        new VocabularyConverter(PARAMETER_PATH,VOCABULARY_PATH);
    }

    public synchronized DataModelTcf convertModel(DataModelLif lifDataModel, InputStream input) throws Exception {
        convertedDataModel = new DataModelTcf(input);
        givenDataModel = lifDataModel;
        convertedDataModel.toLanguage(givenDataModel.getLanguage());
        convertedDataModel.toText(givenDataModel.getText());
        //the text source layer is temporary closed.
        //convertedDataModel.toTextSource(givenDataModel.getFileString());
        try {
            convertAnnotationLayers();
            this.display();
        } catch (ConversionException conExp) {
            Logger.getLogger(FormatConverterTool.class.getName()).log(Level.SEVERE, null, conExp);
        } catch (VocabularyMappingException vocExp) {
            Logger.getLogger(FormatConverterTool.class.getName()).log(Level.SEVERE, null, vocExp);
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
        AnnotationLayerFinder tcfLayer = new VocabularyConverter(lifLayer);
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
        if (convertedDataModel.getTextCorpusStored().getTextSourceLayer() != null) {
            System.out.println("Text Source Layer");
            System.out.println(convertedDataModel.getTextCorpusStored().getTextSourceLayer().getText().toString().substring(0, 99));
        }
        
        }
        
        catch(IndexOutOfBoundsException exIndex ){
             Logger.getLogger(FormatConverterTool.class.getName()).log(Level.SEVERE, null, exIndex);
        }
        
        catch(Exception ex){
             Logger.getLogger(FormatConverterTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
