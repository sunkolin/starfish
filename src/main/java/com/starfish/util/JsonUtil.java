package com.starfish.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * JsonUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-05-30
 */
public class JsonUtil {

    private JsonUtil() {

    }

    /**
     * 对象转json
     */
    public static String toString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString(object);
    }

}
