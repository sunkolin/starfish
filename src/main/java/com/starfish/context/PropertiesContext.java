package com.starfish.context;

import java.util.HashMap;
import java.util.Map;

/**
 * PropertiesContext
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-06-03
 */
public class PropertiesContext {

    private PropertiesContext(){

    }

    public static final Map<String,String> properties = new HashMap<>();

    public static String get(String key){
        return properties.get(key);
    }

    public static String set(String key,String value){
        return properties.put(key,value);
    }

}
