/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class JsonPrettyPrint {

    public static String formatJsonString(String json) throws IOException {
        String prettyJson = json;
        ObjectMapper mapper = new ObjectMapper();
        Object jsonObject = mapper.readValue(json, Object.class);
        prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        return prettyJson;
    }

}
