package com.abc;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;

public class Json extends HashMap<String, String> implements Serializable{

    final static JsonFactory factory = new JsonFactory();

    public String toJson() throws IOException {
        StringWriter sw = new StringWriter();
        try (JsonGenerator generator = factory.createGenerator(sw)) {
            generator.writeStartObject();
            for (Entry<String, String> e : this.entrySet()) {
                generator.writeStringField(e.getKey(), e.getValue());
            }
            generator.writeEndObject();
            generator.close();
        }
        return sw.toString();
    }

    public static Json fromJson(String json) throws IOException {
        Json ret = new Json();

        try (JsonParser jp = factory.createParser(json)) {
            while (!jp.isClosed()) {
                JsonToken jsonToken = jp.nextToken();
                if(jsonToken == null) break;

                if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                    String fieldName = jp.getCurrentName();
                    jsonToken = jp.nextToken();
                    if (JsonToken.VALUE_STRING.equals(jsonToken)) {
                        ret.put(fieldName, jp.getValueAsString());
                    }
                }
            }
            jp.close();
        }

        return ret;
    }

    @Override
    public String toString() {
        try {
            return toJson();
        } catch (IOException e) {
            return "";
        }
    }
}
