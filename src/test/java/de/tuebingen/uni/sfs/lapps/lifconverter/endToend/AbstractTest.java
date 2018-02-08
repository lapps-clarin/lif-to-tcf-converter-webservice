/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.endToend;

import de.tuebingen.uni.sfs.lapps.profile.LIFProfilerImpl;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.ConverterTool;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.impl.ConvertToTCFAnnotations;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Assert;

/**
 *
 * @author felahi
 */
public class AbstractTest {

    private static final String TEXT_TCF_XML = "text/tcf+xml";
    private static final String APPLICATION_JSON = "application/json";
    private static final String FALL_BACK_MESSAGE = "Data processing failed";
    private static final String TEMP_FILE_PREFIX = "ne-output-temp";
    private static final String TEMP_FILE_SUFFIX = ".xml";
    public OutputStream output = null;
    public File tempOutputFile = null;

    protected InputStream read(String file) throws Exception {
        this.write();
       return  this.getClass().getResourceAsStream(file);
    }

    public void write() throws Exception {
        try {
            tempOutputFile = File.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX);
            output = new BufferedOutputStream(new FileOutputStream(tempOutputFile));
        } catch (IOException ex) {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    System.err.println("output stream is wrong");
                }
            }
            if (tempOutputFile != null) {
                tempOutputFile.delete();
                System.err.println("output file is wrong");
            }

        }

    }

}
