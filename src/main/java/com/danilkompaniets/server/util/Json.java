package com.danilkompaniets.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

public class Json {
    private static ObjectMapper myObjectMapper;

    // Static block to initialize the ObjectMapper
    static {
        myObjectMapper = new ObjectMapper();
        myObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper defaultObjectMapper() {
        return myObjectMapper; // Return the initialized ObjectMapper
    }

    public static JsonNode parse(String jsonSrc) throws JsonProcessingException {
        return myObjectMapper.readTree(jsonSrc);
    }

    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return myObjectMapper.treeToValue(node, clazz);
    }

    public static JsonNode toJson(Object obj) {
        return myObjectMapper.valueToTree(obj);
    }

    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node, true);
    }

    private static String generateJson(Object o, boolean pretty) throws JsonProcessingException {
        ObjectWriter objWriter = myObjectMapper.writer();

        if (pretty) {
            objWriter = objWriter.with(SerializationFeature.INDENT_OUTPUT);
        }

        return objWriter.writeValueAsString(o);
    }
}
