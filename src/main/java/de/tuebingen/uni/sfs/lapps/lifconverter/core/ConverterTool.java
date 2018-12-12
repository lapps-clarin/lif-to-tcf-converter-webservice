package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import de.tuebingen.uni.sfs.lapps.core.converter.impl.LifToTcfAnnoLayerConverter;
import de.tuebingen.uni.sfs.lapps.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.exceptions.VocabularyMappingException;
import eu.clarin.weblicht.wlfxb.io.WLDObjector;
import eu.clarin.weblicht.wlfxb.io.WLFormatException;
import eu.clarin.weblicht.wlfxb.xb.WLData;
import java.io.OutputStream;
import de.tuebingen.uni.sfs.lapps.core.lifwrapper.profiler.api.LifFormat;
import de.tuebingen.uni.sfs.lapps.core.converter.api.FormatConverter;
import de.tuebingen.uni.sfs.lapps.core.converter.api.LayersConverter;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusStored;
import java.io.File;

public class ConverterTool implements FormatConverter {

    private TextCorpusStored tcfFormat;
    public static final String PARAMETER_PATH = "/models/parameterlist.init";
    public static final String VOCABULARY_PATH = "/models/annotationConversion.init";

    public ConverterTool() {

    }

    public synchronized TextCorpusStored convertLifToTcf(LifFormat lifFormat) throws  ConversionException, VocabularyMappingException, LifException {
        LayersConverter lifToTCFLayersConverter = new LifToTcfAnnoLayerConverter(lifFormat);
        tcfFormat = lifToTCFLayersConverter.getTextCorpusStored();
        return tcfFormat;
    }

    @Override
    public void write(OutputStream os) throws ConversionException {
        WLData wlData = new WLData(tcfFormat);
        try {
            WLDObjector.write(wlData, os);
        } catch (WLFormatException ex) {
            throw new ConversionException(ex.getMessage());
        }
    }

    @Override
    public File convertLifToTcf(File lifFile) throws VocabularyMappingException, ConversionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
