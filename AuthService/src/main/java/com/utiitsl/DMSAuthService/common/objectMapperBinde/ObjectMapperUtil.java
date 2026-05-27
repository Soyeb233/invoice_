package com.utiitsl.DMSAuthService.common.objectMapperBinde;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class ObjectMapperUtil {

    public static ObjectMapper mapper;
    static{
        mapper = new ObjectMapper();
    }

    public static <E extends Object,T extends Object> T copyPropertiesByMapper(E object, Class<T> valueType){
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String json = mapper.writeValueAsString(object);
            return mapper.readValue(json, valueType);
        } catch (IOException e) {

        }
        return null;
    }

    public static <E extends Object,T extends Object> T copyMapToObject(E object, Class<T> valueType){
        return  mapper.convertValue(object ,valueType);
    }

}