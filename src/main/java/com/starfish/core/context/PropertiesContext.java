package com.starfish.core.context;

import java.util.HashMap;
import java.util.Map;

/**
 * PropertiesContext
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-03
 */
public class PropertiesContext {

    private PropertiesContext(){

    }

    protected static final Map<String,String> PROPERTIES = new HashMap<>();

    public static String get(String key){
        return PROPERTIES.get(key);
    }

    public static String set(String key,String value){
        return PROPERTIES.put(key,value);
    }

}
