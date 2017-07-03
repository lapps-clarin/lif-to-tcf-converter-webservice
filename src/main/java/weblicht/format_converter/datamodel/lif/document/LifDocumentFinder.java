/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weblicht.format_converter.datamodel.lif.document;

import weblicht.format_converter.datamodel.exceptions.ValidityCheck;
import weblicht.format_converter.datamodel.exceptions.LifValidityCheck;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import weblicht.format_converter.datamodel.exceptions.LifException;
import weblicht.format_converter.datamodel.utils.JsonProcessor;

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
