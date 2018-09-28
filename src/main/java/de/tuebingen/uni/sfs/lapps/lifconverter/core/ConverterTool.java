package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import de.tuebingen.uni.sfs.lapps.core.converter.impl.ConvertToTcfFormat;
import de.tuebingen.uni.sfs.lapps.exceptions.JsonValidityException;
import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import de.tuebingen.uni.sfs.lapps.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.exceptions.VocabularyMappingException;
import eu.clarin.weblicht.wlfxb.io.WLDObjector;
import eu.clarin.weblicht.wlfxb.io.WLFormatException;
import eu.clarin.weblicht.wlfxb.xb.WLData;
import java.io.IOException;
import java.io.OutputStream;
import de.tuebingen.uni.sfs.lapps.core.lifwrapper.profiler.LifFormat;
import de.tuebingen.uni.sfs.lapps.core.converter.api.ConvertLayer;
import de.tuebingen.uni.sfs.lapps.core.converter.api.ConvertFormat;

public class ConverterTool implements ConvertFormat {

    private ConvertLayer weblichtTcfProfile;
    private LifFormat lappsLifProfile = null;
    public static final String PARAMETER_PATH = "/models/parameterlist.init";
    public static final String VOCABULARY_PATH = "/models/annotationConversion.init";

    public ConverterTool()  {
        
    }

    public synchronized ConvertLayer convertFormat(LifFormat lappsLifFormat) throws LifException, ConversionException, IOException, JsonValidityException, VocabularyMappingException {
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
