package com.starfish.util;

import com.starfish.enumeration.ResultEnum;
import com.starfish.exception.CustomException;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * BeanTool
 *
 * @author neacle
 * @version 1.0.0
 * @since 2015-01-29
 */
@SuppressWarnings("unused")
public class BeanUtil {

    /**
     * OBJECT_CLASS_NAME
     */
    private static final String OBJECT_CLASS_NAME = "Object";

    /**
     * 将指定对象属性名称和属性值转化为Map键值对
     * org.apache.commons.beanutils.BeanUtils.describe(Object bean) 返回类型为Map,key类型是String,value类型也是String,
     * 此map传入mybatis如果类型不一致有的地方会报错,例如 limit m,n
     *
     * @param object 对象
     * @return HashMap, key tape is String,value type is Object
     */
    public static HashMap<String, Object> describe(Object object) {
        if (object == null) {
            throw new CustomException(ResultEnum.PARAM_ERROR);
        }

        Class<?> clazz = object.getClass();
        HashMap<String, Object> map = new HashMap<>(20);
        getClass(clazz, map, object);
        return map;
    }

    private static void getClass(Class<?> clazz, HashMap<String, Object> map, Object obj) {
        if (OBJECT_CLASS_NAME.equals(clazz.getSimpleName())) {
            return;
        }

        //取得所有字段，包括公共、保护、默认（包）访问和私有字段 ，但是不包含集成的地段
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length <= 0) {
            throw new CustomException(ResultEnum.FIELD_COUNT_IS_ZERO);
        }

        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            try {
                map.put(name, field.get(obj));
            } catch (IllegalAccessException e) {
                throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), e.getMessage());
            }

        }
        //取得父类的字段
        Class<?> superClass = clazz.getSuperclass();
        getClass(superClass, map, obj);
    }

}
