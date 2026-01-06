package com.starfish.core.util;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * JsonUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-05-30
 */
public class JsonUtil {

    private JsonUtil() {
        // constructor
    }

    /**
     * 对象转json，null也返回
     */
    public static String toJson(Object object) {
//        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        ObjectMapper mapper = JsonMapper.builder().enable(SerializationFeature.INDENT_OUTPUT).build();
        return mapper.writeValueAsString(object);
    }

    public static <T> T toObject(String json, Class<T> cls) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, cls);
    }

    public static String getString(String json, String propertyName) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        if (propertyName.contains(".")) {
            propertyName = propertyName.replace(".", "/");
            return rootNode.at(propertyName).asString();
        }
        return rootNode.path(propertyName).asString();
    }

    public static int getInt(String json, String propertyName) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.path(propertyName).asInt();
    }

    public static long getLong(String json, String propertyName) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.path(propertyName).asLong();
    }

    public static short getShort(String json, String propertyName) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.path(propertyName).asShort();
    }

    public static boolean getBoolean(String json, String propertyName) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.path(propertyName).asBoolean();
    }

    public static float getFloat(String json, String propertyName) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.path(propertyName).asFloat();
    }

    public static double getDouble(String json, String propertyName) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.path(propertyName).asDouble();
    }

    public static BigInteger getBigInteger(String json, String propertyName) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.path(propertyName).asBigInteger();
    }

    public static BigDecimal getBigDecimal(String json, String propertyName) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.path(propertyName).asDecimal();
    }

    public static boolean has(String json, String propertyName) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.has(propertyName);
    }

//    JsonNode cityNode = rootNode.at("/user/address/city");


}

