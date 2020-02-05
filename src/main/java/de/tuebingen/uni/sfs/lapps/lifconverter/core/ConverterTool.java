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
import de.tuebingen.uni.sfs.lapps.core.lifwrapper.profiler.impl.LifFormatImpl;
import de.tuebingen.uni.sfs.lapps.exceptions.JsonValidityException;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusStored;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConverterTool implements FormatConverter {

    private TextCorpusStored tcfFormat;
    public static final String PARAMETER_PATH = "/models/parameterlist.init";
    public static final String VOCABULARY_PATH = "/models/annotationConversion.init";
    private static final String TEMP_FILE_PREFIX = "ne-output-temp";
    private static final String TEMP_FILE_SUFFIX = ".xml";

    public ConverterTool() {

    }

    @Override
    public TextCorpusStored convertLifToTcf(LifFormat lifFormat) throws LifException, VocabularyMappingException, ConversionException, IOException, JsonValidityException {
        LayersConverter lifToTCFLayersConverter = new LifToTcfAnnoLayerConverter(lifFormat);
        tcfFormat = lifToTCFLayersConverter.getTextCorpusStored();
        return tcfFormat;
    }

    @Override
    public void write(OutputStream os) throws ConversionException {
        WLData wlData = new WLData(tcfFormat);
        wlData.setVersion(WLData.XML_VERSION_04);
        try {
            WLDObjector.write(wlData, os);
        } catch (WLFormatException ex) {
            throw new ConversionException(ex.getMessage());
        }
    }

    @Deprecated
    @Override
    public File convertLifToTcf(InputStream input) throws LifException, VocabularyMappingException, ConversionException, IOException, JsonValidityException {
        File tcfFile = File.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX);
        OutputStream fos = new FileOutputStream(tcfFile);
        LifFormatImpl lifFormat = new LifFormatImpl(input);
        tcfFormat = convertLifToTcf(lifFormat);
        write(fos);
        return tcfFile;
    }

}
