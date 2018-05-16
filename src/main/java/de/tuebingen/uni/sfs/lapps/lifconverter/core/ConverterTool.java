package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import de.tuebingen.uni.sfs.lapps.core.impl.layer.LifSingleLayer;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.impl.ConvertToTcfFormat;
import de.tuebingen.uni.sfs.lapps.exceptions.JsonValidityException;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.impl.ConvertVocabulary;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.api.TcfFormat;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.VocabularyMappingException;
import eu.clarin.weblicht.wlfxb.io.WLDObjector;
import eu.clarin.weblicht.wlfxb.io.WLFormatException;
import eu.clarin.weblicht.wlfxb.xb.WLData;
import java.io.IOException;
import java.io.OutputStream;
import de.tuebingen.uni.sfs.lapps.core.api.profiler.LifFormat;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.api.FormatConverter;

public class ConverterTool implements FormatConverter {

    private TcfFormat weblichtTcfProfile;
    private LifFormat lappsLifProfile = null;
    public static final String PARAMETER_PATH = "/models/parameterlist.init";
    public static final String VOCABULARY_PATH = "/models/annotationConversion.init";

    public ConverterTool() throws VocabularyMappingException {
        new ConvertVocabulary(PARAMETER_PATH, VOCABULARY_PATH);
    }

    public synchronized TcfFormat convertFormat(LifFormat lappsLifFormat) throws LifException, VocabularyMappingException, ConversionException, IOException, JsonValidityException {
        weblichtTcfProfile = new ConvertToTcfFormat(lappsLifFormat);
        return weblichtTcfProfile;
    }

    @Override
    public void process(OutputStream os) throws ConversionException {
        WLData wlData = new WLData(weblichtTcfProfile.getTextCorpusStored());
        try {
            WLDObjector.write(wlData, os);
        } catch (WLFormatException ex) {
            throw new ConversionException(ex.getMessage());
        }
    }

}
