package br.com.compassuol.sp.challenge.ecommerce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
    }

    public static String mapToString(Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

}