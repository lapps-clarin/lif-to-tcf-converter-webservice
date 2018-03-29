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

    private ConvertToTCFAnnotations weblichtTcfProfile = null;
    private LifProfile lappsLifProfile = null;
    public static final String PARAMETER_PATH = "/models/parameterlist.init";
    public static final String VOCABULARY_PATH = "/models/annotationConversion.init";

    public ConverterTool() throws ConversionException, VocabularyMappingException {
        new ConvertVocabulary(PARAMETER_PATH, VOCABULARY_PATH);
    }

    public synchronized ConvertToTCFAnnotations convertFormat(LifProfile lifDataModel, InputStream input) throws LifException, VocabularyMappingException, ConversionException {
        lappsLifProfile = lifDataModel;
        weblichtTcfProfile = new ConvertToTCFAnnotations(input);
        weblichtTcfProfile.toLanguage(lappsLifProfile.getLanguage());
        weblichtTcfProfile.toText(lappsLifProfile.getText());
        weblichtTcfProfile.toTextSource(lappsLifProfile.getFileString());
        convertAnnotationLayers();
        return weblichtTcfProfile;
    }

    protected void convertAnnotationLayers() throws VocabularyMappingException, ConversionException, LifException {
        for (Integer layerIndex : lappsLifProfile.getSortedLayer()) {
            AnnotationLayerFinder tcfLayer = converAnnotationlayervocabularies(layerIndex);
            weblichtTcfProfile.toLayers(tcfLayer, lappsLifProfile.getAnnotationLayerData(layerIndex));
        }
    }

    protected AnnotationLayerFinder converAnnotationlayervocabularies(Integer layerIndex) throws VocabularyMappingException, LifException {
        AnnotationLayerFinder lifLayer = lappsLifProfile.getIndexAnnotationLayer(layerIndex);
        AnnotationLayerFinder tcfLayer = new ConvertVocabulary(lifLayer);
        return tcfLayer;
    }

    public ConvertToTCFAnnotations getWeblichtTcfProfile() {
        return weblichtTcfProfile;
    }
}
