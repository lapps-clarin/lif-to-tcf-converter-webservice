/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weblicht.format_converter.datamodel.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author felahi
 */
public class JsonProcessor implements InputOutputChecker {

    private String jsonString = null;
    private Map<String, Object> jsonMap = new HashMap<String, Object>();
    private boolean valid = true;

    public JsonProcessor(String jsonString) {
        this.jsonString = jsonString;
        try {
            this.jsonMap = conversionJSONMapToString();
        } catch (Exception ex) {
            valid = false;
            Logger.getLogger(JsonProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private Map<String, Object> conversionJSONMapToString() throws JsonParseException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
        };
        return mapper.readValue(jsonString, typeRef);
    }

    public boolean isInputValid() {
        return valid;
    }

    public String getJsonString() {
        return jsonString;
    }

    public Map<String, Object> getJsonMap() {
        return jsonMap;
    }

    public boolean isOutputValid() throws IOException {
        if (!jsonMap.isEmpty()) {
            return true;
        }
        return false;
    }
}
