package com.starfish.core.util;

import com.starfish.core.enumeration.ResultEnum;
import com.starfish.core.exception.CustomException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * ObjectUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-01-11
 */
@SuppressWarnings("unused")
@Slf4j
public final class ObjectUtil {

    private ObjectUtil() {
        // constructor
    }

    /**
     * 比较两个对象是否相等
     *
     * @param object1 对象
     * @param object2 对象
     * @return 两个对象相等返回true，否则返回false
     */
    public static boolean equals(Object object1, Object object2) {
        return object1 == object2 || object1.equals(object2);
    }

    /**
     * 判断指定的类是否存在
     *
     * @param className 类名
     * @return 结果
     */
    public static boolean existClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 限制调用此方法的方法名称
     *
     * @param methodName 方法名称
     */
    public static void restrictMethod(String methodName) {
        boolean find = false;
        StackTraceElement[] elements = new Throwable().getStackTrace();
        for (StackTraceElement element : elements) {
            if (element.getMethodName().equals(methodName)) {
                find = true;
                break;
            }
        }
        if (!find) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "调用方法的方法名必须是" + methodName);
        }
    }

    /**
     *
     * 序列化
     *
     * @param object 对象
     * @return 序列化结果
     * @throws IOException IOException
     */
    public static byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream tmp = new ByteArrayOutputStream();
        new ObjectOutputStream(tmp).writeObject(object);
        return tmp.toByteArray();
    }

    /**
     * 反序列
     *
     * @param bytes 字节数组
     * @return 反序列化结果
     * @throws IOException            IOException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        return new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject();
    }

    /*
     * 获取类的路径，以.class结尾
     *
     * @param cls 类
     * @return 路径
     */
    public static String getResourcePath(Class<?> cls) {
        String path = getResourceParentPath(cls);
        String className = cls.getSimpleName() + ".class";
        String result = path + className;
        result = result.replace("%20", " ");
        return result;
    }

    /**
     * 获取类所在的文件夹路径，以/结尾
     *
     * @param cls 类
     * @return 路径
     */
    public static String getResourceParentPath(Class<?> cls) {
        String path = cls.getResource("").getPath();
        path = path.replace("%20", " ");
        return path;
    }

}