package de.tuebingen.uni.sfs.lapps.lifconverter.resources;

import de.tuebingen.uni.sfs.lapps.exceptions.LifException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.ConverterTool;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.ConversionException;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.api.TcfFormat;
import de.tuebingen.uni.sfs.lapps.lifconverter.exceptions.VocabularyMappingException;
import de.tuebingen.uni.sfs.lapps.core.impl.profiler.LifFormatImpl;
import eu.clarin.weblicht.wlfxb.io.WLFormatException;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.api.FormatConverter;

@Path("con")
public class ConverterResource {

    private static final String TEXT_TCF_XML = "text/tcf+xml";
    private static final String APPLICATION_JSON = "application/json";
    private static final String FALL_BACK_MESSAGE = "Data processing failed";
    private static final String TEMP_FILE_PREFIX = "ne-output-temp";
    private static final String TEMP_FILE_SUFFIX = ".xml";

    private FormatConverter tool;

    public ConverterResource() {
        try {
            tool = new ConverterTool();
        } catch (VocabularyMappingException ex) {
            throw new WebApplicationException(createResponse(ex, Response.Status.INTERNAL_SERVER_ERROR));
        }
    }

    @Path("bytes")
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(TEXT_TCF_XML)
    public Response processWithBytesArray(final InputStream input) {
        // prepare the storage for TCF output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        // process incoming TCF and output resulting TCF with new annotation layer(s) added
        process(input, output, tool);
        // if no exceptions occur to this point, return OK status and TCF output
        // with the added annotation layer(s)
        return Response.ok(output.toByteArray()).build();
    }

    @Path("stream")
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(TEXT_TCF_XML)
    public StreamingOutput processWithStreaming(final InputStream input) {

        // prepare temporary file and temprary output stream for writing TCF
        OutputStream tempOutputData = null;
        File tempOutputFile = null;
        try {
            tempOutputFile = File.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX);
            tempOutputData = new BufferedOutputStream(new FileOutputStream(tempOutputFile));
        } catch (IOException ex) {
            if (tempOutputData != null) {
                try {
                    tempOutputData.close();
                } catch (IOException e) {
                    throw new WebApplicationException(createResponse(ex, Response.Status.INTERNAL_SERVER_ERROR));
                }
            }
            if (tempOutputFile != null) {
                tempOutputFile.delete();
            }
            throw new WebApplicationException(createResponse(ex, Response.Status.INTERNAL_SERVER_ERROR));
        }

        // process incoming TCF and output resulting TCF with new annotation layer(s) added
        process(input, tempOutputData, tool);

        // if there were no errors reading and writing TCF data, the resulting
        // TCF can be sent as StreamingOutput from the TCF output temporary file
        return new StreamingTempFileOutput(tempOutputFile);

    }

    private void process(InputStream input, OutputStream output, FormatConverter tool) {

        try {
            LifFormatImpl lifFormat = new LifFormatImpl(input);
            tool.convertFormat(lifFormat);
            tool.process(output);
        } catch (LifException ex) {
            throw new WebApplicationException(createResponse(ex, Response.Status.BAD_REQUEST));
        } catch (VocabularyMappingException ex) {
            throw new WebApplicationException(createResponse(ex, Response.Status.INTERNAL_SERVER_ERROR));
        } catch (ConversionException ex) {
            throw new WebApplicationException(createResponse(ex, Response.Status.INTERNAL_SERVER_ERROR));
        } catch (WLFormatException ex) {
            throw new WebApplicationException(createResponse(ex, Response.Status.BAD_REQUEST));
        } catch (Exception ex) {
            throw new WebApplicationException(createResponse(ex, Response.Status.INTERNAL_SERVER_ERROR));
        } finally {
            try {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException ex) {
                        throw new WebApplicationException(createResponse(ex, Response.Status.INTERNAL_SERVER_ERROR));
                    }
                }
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException ex) {
                        throw new WebApplicationException(createResponse(ex, Response.Status.INTERNAL_SERVER_ERROR));
                    }
                }
            } catch (Exception ex) {
                throw new WebApplicationException(createResponse(ex, Response.Status.INTERNAL_SERVER_ERROR));
            }
        }
    }

 /* if exception message is provided, use it as it is;
     * if exception message is null, use fall back message
     * (needs to be non-empty String in order to prevent
     * HTTP container generated html message) */
    private Response createResponse(Exception ex, Response.Status status) {
        String message = ex.getMessage();
        if (message == null) {
            message = FALL_BACK_MESSAGE;
        }
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, message, ex);
        return Response.status(status).entity(message).type(MediaType.TEXT_PLAIN).build();
    }
}
