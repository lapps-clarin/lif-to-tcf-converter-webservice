package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import de.tuebingen.uni.sfs.lapps.lifconverter.core.impl.ConvertToTCFAnnotations;
import de.tuebingen.uni.sfs.lapps.core.layer.api.AnnotationLayerFinder;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import java.io.InputStream;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.impl.ConvertVocabulary;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.api.ConverterFormat;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.VocabularyMappingException;
import de.tuebingen.uni.sfs.lapps.profile.api.LifProfile;

public class ConverterTool implements ConverterFormat {

    private ConvertToTCFAnnotations convertedDataModel = null;
    private LifProfile lifProfiler = null;
    public static final String PARAMETER_PATH = "/models/parameterlist.init";
    public static final String VOCABULARY_PATH = "/models/annotation_conversion.init";

    public ConverterTool() throws ConversionException, VocabularyMappingException {
        new ConvertVocabulary(PARAMETER_PATH, VOCABULARY_PATH);
    }

    public synchronized ConvertToTCFAnnotations convertFormat(LifProfile lifDataModel, InputStream input) throws LifException, VocabularyMappingException, ConversionException {
        convertedDataModel = new ConvertToTCFAnnotations(input);
        lifProfiler = lifDataModel;
        convertedDataModel.toLanguage(lifProfiler.getLanguage());
        convertedDataModel.toText(lifProfiler.getText());
        //temporarily closed
        //convertedDataModel.toTextSource(lifProfiler.getFileString());
        convertAnnotationLayers();
        //this.display();
        return convertedDataModel;
    }

    protected void convertAnnotationLayers() throws VocabularyMappingException, ConversionException, LifException {
        for (Integer layerIndex : lifProfiler.getSortedLayer()) {
            AnnotationLayerFinder tcfLayer = converAnnotationlayervocabularies(layerIndex);
            convertAnnotations(tcfLayer, layerIndex);
        }
    }

    protected void convertAnnotations(AnnotationLayerFinder tcfLayer, Integer layerIndex) throws ConversionException, LifException, VocabularyMappingException {
        convertedDataModel.toLayers(tcfLayer, lifProfiler.getAnnotationLayerData(layerIndex));
    }

    protected AnnotationLayerFinder converAnnotationlayervocabularies(Integer layerIndex) throws VocabularyMappingException, LifException {
        AnnotationLayerFinder lifLayer = lifProfiler.getIndexAnnotationLayer(layerIndex);
        AnnotationLayerFinder tcfLayer = new ConvertVocabulary(lifLayer);
        return tcfLayer;
    }

    public ConvertToTCFAnnotations getConvertedDataModel() {
        return convertedDataModel;
    }
}
