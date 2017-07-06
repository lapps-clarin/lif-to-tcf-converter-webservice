/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.lif.document;

import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.exceptions.LifValidityCheck;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.exceptions.ValidityCheck;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.utils.JsonProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.exceptions.LifException;

/**
 *
 * @author felahi
 */
public class LifDocumentFinder {

    private LifDocument mascDocument = new LifDocument();

    public LifDocumentFinder(String jsonString) throws LifException, IOException {
        JsonProcessor jsonObject = new JsonProcessor(jsonString);
        if (jsonObject.isInputValid()) {
            jsonToLifObjectMapping(jsonObject);
        } else {
            throw new LifException(LifValidityCheck.INVALID_JSON_FILE);
        }

    }

    private void jsonToLifObjectMapping(JsonProcessor jsonObject) throws LifException, IOException {
        ValidityCheck lifDocumentValidityCheck = new LifValidityCheck(jsonObject);
        ObjectMapper mapper = new ObjectMapper();
        if (lifDocumentValidityCheck.isValid()) {
            mascDocument = mapper.readValue(jsonObject.getJsonString(), LifDocument.class);
        }

    }

    public LifDocument getMascDocument() {
        return mascDocument;
    }

}
