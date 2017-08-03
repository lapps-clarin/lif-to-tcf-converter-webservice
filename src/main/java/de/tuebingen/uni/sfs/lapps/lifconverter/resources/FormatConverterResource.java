package de.tuebingen.uni.sfs.lapps.lifconverter.resources;

import de.tuebingen.uni.sfs.lapps.library.exception.LifException;
import de.tuebingen.uni.sfs.lapps.library.exception.VocabularyMappingException;
import de.tuebingen.uni.sfs.lapps.library.model.DataModelLif;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.tuebingen.uni.sfs.lapps.lifconverter.core.FormatConverterTool;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.conversion.DataModelConverter;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.model.DataModelTcf;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.exceptions.ConversionException;

@Path("con")
public class FormatConverterResource {

    private static final String TEXT_TCF_XML = "text/tcf+xml";
    private static final String APPLICATION_JSON = "application/json";
    private static final String FALL_BACK_MESSAGE = "Data processing failed";
    private static final String TEMP_FILE_PREFIX = "ne-output-temp";
    private static final String TEMP_FILE_SUFFIX = ".xml";

    private DataModelConverter tool;

    public FormatConverterResource() {
        try {
            tool = new FormatConverterTool();
        } catch (VocabularyMappingException ex) {
            throw new WebApplicationException(createResponse(ex, Response.Status.INTERNAL_SERVER_ERROR));
        } catch (ConversionException ex) {
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

    private void process(final InputStream input, OutputStream output, DataModelConverter tool) {
        try {
            DataModelLif dataModelLif = new DataModelLif(input);
            if (dataModelLif.isValid()) {
                DataModelTcf tcfDataModel = tool.convertModel(dataModelLif, input);
                tcfDataModel.process(output);
            } else {
                throw new LifException("The lif file is in correct!!");
            }

        } catch (LifException exlIF) {
            Logger.getLogger(FormatConverterResource.class.getName()).log(Level.SEVERE, null, exlIF);
        } catch (Exception ex) {
            Logger.getLogger(FormatConverterResource.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(FormatConverterResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException ex) {
                    Logger.getLogger(FormatConverterResource.class.getName()).log(Level.SEVERE, null, ex);
                }
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
