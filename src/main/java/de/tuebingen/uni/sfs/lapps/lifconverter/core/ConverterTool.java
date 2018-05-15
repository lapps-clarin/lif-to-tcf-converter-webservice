package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import com.fasterxml.jackson.core.JsonParseException;
import de.tuebingen.uni.sfs.lapps.core.layer.impl.LIFAnnotationLayer;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.impl.ConvertAnnotationsImpl;
import de.tuebingen.uni.sfs.lapps.core.layer.impl.LIFAnnotationLayers;
import de.tuebingen.uni.sfs.lapps.exceptions.JsonValidityException;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import java.io.InputStream;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.impl.ConvertVocabulary;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.api.ConverterFormat;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.VocabularyMappingException;
import de.tuebingen.uni.sfs.lapps.profile.api.LifProfile;
import de.tuebingen.uni.sfs.lapps.profile.impl.LifProfiler;
import java.io.IOException;

public class ConverterTool implements ConverterFormat {

    private ConvertAnnotationsImpl weblichtTcfProfile;
    private LifProfile lappsLifProfile = null;
    public static final String PARAMETER_PATH = "/models/parameterlist.init";
    public static final String VOCABULARY_PATH = "/models/annotationConversion.init";

    public ConverterTool() throws VocabularyMappingException {
        new ConvertVocabulary(PARAMETER_PATH, VOCABULARY_PATH);
    }

    public synchronized ConvertAnnotationsImpl convertFormat(LifProfile lappsLifProfile) throws LifException, VocabularyMappingException, ConversionException, IOException, JsonValidityException {
        weblichtTcfProfile = new ConvertAnnotationsImpl(lappsLifProfile.getLanguage(), lappsLifProfile.getText(), lappsLifProfile.getFileString());
        for (LIFAnnotationLayer lifLayer : lappsLifProfile.getLifAnnotationLayers().getLayers()) {
            weblichtTcfProfile.toLayer(lifLayer);
        }
        return weblichtTcfProfile;
    }
}
