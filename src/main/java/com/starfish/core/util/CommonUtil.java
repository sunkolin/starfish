package com.starfish.core.util;

import com.google.common.base.Joiner;
import com.starfish.core.enumeration.ResultEnum;
import com.starfish.core.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * CommonUtil
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-01-11
 */
@SuppressWarnings(value = "unused")
@Slf4j
public final class CommonUtil {

    /**
     * 问号
     */
    public static final String QUESTION_MARK = "?";

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
     * 获取不带横线的uuid
     *
     * @return uuid
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取带横线的uuid
     *
     * @return uuid
     */
    public static String getUuidWithLine() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取主键，返回18位数字，支持Long类型，在请求量较小系统中可以使用
     * long long的最大值：9223372036854775807
     * long long的最小值：-9223372036854775808
     *
     * @return 结果
     */
    public static String getId() {
        // 第一段：yyyyMMddHHmmss共14位
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String id1 = format.format(new Date());

        //第二段：4位随机数
        String id2 = StringUtil.randomString("0123456789",4);

        String id = id1 + id2;
        log.info("getId(),id={}", id);

        return id;
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

    /**
     * 序列化对象
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
     * 生成指定范围的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 结果
     */
    public static int nextInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * 生成指定范围的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 结果
     */
    public static long nextLong(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }

    /**
     * 移除特殊字符
     *
     * @param string 字符串
     * @return 结果
     */
    public static String removeSpecialCharacter(String string) {
        return string.replace("\\", "").replace("/", "").replace("*", "").replace("?", "").replace("\"", "").replace(":", "").replace("<", "").replace(">", "").replace("|", "");
    }

    /**
     * 截取第一个字符串，
     *
     * @param string1 字符串1，例如http//:www.baidu.com/xxx.png?a=123456
     * @param string2 字符串2，例如?
     * @return 结果，例如http//:www.baidu.com/xxx.png
     */
    public static String substring(String string1, String string2) {
        int index = string1.indexOf(string2);
        if (index != -1) {
            return string1.substring(0, index);
        }
        return string1;
    }

    /**
     * 拼接参数到链接后
     *
     * @param url    链接地址
     * @param params 参数
     * @return 结果
     */
    public static String contact(String url, Map<String, Object> params) {
        // 参数为空，则直接返回url
        if (CollectionUtils.isEmpty(params)) {
            return url == null ? "" : url;
        }

        // 参数不为空，拼接k=v字符串
        String paramsString = Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(params);

        // 链接为空，直接返回拼接的字符串
        if (Strings.isNullOrEmpty(url)) {
            return paramsString;
        }

        // 链接不为空，拼接字符串
        if (url.contains("?")) {
            url = url.concat("&").concat(paramsString);
        } else {
            url = url.concat("?").concat(paramsString);
        }

        return url;
    }

    public static String contact(Map<String, Object> params) {
        return contact("", params);
    }

    /**
     * 获取本机网络地址
     *
     * @return 结果
     * @throws Exception 异常
     */
    public static String getHostAddress() throws Exception {
        String result = "";

        Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = allNetInterfaces.nextElement();
            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress tmp = addresses.nextElement();
                if (tmp instanceof Inet4Address && tmp.isSiteLocalAddress()
                        && !tmp.isLoopbackAddress() && !tmp.getHostAddress().contains(":")) {
                    result = tmp.getHostAddress();
                    break;
                }
            }
        }

        return result;
    }

}