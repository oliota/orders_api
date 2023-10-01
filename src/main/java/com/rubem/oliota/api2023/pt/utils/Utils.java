package com.rubem.oliota.api2023.pt.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Utility class for various operations.
 */
public class Utils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts an object to its JSON representation, excluding null properties.
     *
     * @param object The object to convert to JSON.
     * @return A JSON string representing the object, or null if an error occurs.
     */
    public static String convertObjectToJsonString(Object object) {
        try {
            ObjectMapper objectMapperWithoutNulls = new ObjectMapper();
            objectMapperWithoutNulls.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

            return objectMapperWithoutNulls.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}



