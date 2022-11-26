package com.starfish.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.starfish.core.exception.CustomException;

/**
 * JsonUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-05-30
 */
public class JsonUtil {

    private JsonUtil(){
        // constructor
    }

    /**
     * 对象转json，null也返回
     */
    public static String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new CustomException();
        }
    }

    public static <T> T toObject(String json, Class<T> cls) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, cls);
        } catch (JsonProcessingException e) {
            throw new CustomException();
        }
    }

}

