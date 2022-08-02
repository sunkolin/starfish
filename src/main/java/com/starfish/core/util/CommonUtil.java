package com.starfish.core.util;

import com.dtflys.forest.Forest;
import com.google.common.base.Joiner;
import com.starfish.core.enumeration.ResultEnum;
import com.starfish.core.exception.CustomException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * CommonUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-01-11
 */
@SuppressWarnings("unused")
@Slf4j
public final class CommonUtil {

    /**
     * &号
     */
    public static final String AMPERSAND_SYMBOL = "&";

    /**
     * 冒号
     */
    public static final String COLON_SYMBOL = ":";

    /**
     * 问号
     */
    public static final String QUESTION_MARK_SYMBOL = "?";

    /**
     * 等于号
     */
    public static final String EQUAL_SYMBOL = "=";

    private CommonUtil(){
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
     * 获取不带横线的uuid
     *
     * @return uuid
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
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
        String id2 = StringUtil.random("0123456789", 4);

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
    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * 生成指定范围的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 结果
     */
    public static long random(long min, long max) {
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
    public static String contact(String url, Map<?, ?> params) {
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
        if (url.contains(QUESTION_MARK_SYMBOL)) {
            url = url.concat(AMPERSAND_SYMBOL).concat(paramsString);
        } else {
            url = url.concat(QUESTION_MARK_SYMBOL).concat(paramsString);
        }

        return url;
    }

    public static String contact(Map<?, ?> params) {
        return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(params);
    }

    /**
     * 获取本机局域网IP地址
     *
     * @return 结果
     * @throws Exception 异常
     */
    public static String getLocalIp() throws NoSuchElementException, SocketException {
        String result = "";
        Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = allNetInterfaces.nextElement();
            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress tmp = addresses.nextElement();
                // Inet4Address.isSiteLocalAddress()验证条件去掉
                // 方法验证是否以一下前缀开头，部分情况下不好用，在我的mac下局域网地址是20.18.138.3
                // 10/8 prefix
                // 172.16/12 prefix
                // 192.168/16 prefix
                if (tmp instanceof Inet4Address && !tmp.isLoopbackAddress() && !tmp.getHostAddress().contains(":")) {
                    result = tmp.getHostAddress();
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 获取本机公网IP地址，此接口也能获取地址
     *
     * @return 结果
     */
    public static String getPublicIp() {
        HashMap<String, String> headers = new HashMap<>(20);
        headers.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headers.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36");
        headers.put("cookie", "UM_distinctid=17b80664e762cd-0806aa579995fa-35667c03-13c680-17b80664e777ed; CNZZDATA1278852728=1391488375-1629944092-https%253A%252F%252Fwww.baidu.com%252F%7C1629944092; __51cke__=; __51uvsct__1vGn5KEyNxI88WjH=1; __51vcke__1vGn5KEyNxI88WjH=311712a8-8217-5248-93ec-70cc18e109ba; __51vuft__1vGn5KEyNxI88WjH=1629946793633; INIT_IP_INFO=%E4%B8%AD%E5%9B%BD++%E5%8C%97%E4%BA%AC+%E5%8C%97%E4%BA%AC%E5%B8%82+%E7%94%B5%E4%BF%A1; __tins__20765349=%7B%22sid%22%3A%201629946793615%2C%20%22vd%22%3A%202%2C%20%22expires%22%3A%201629948607357%7D; __51laig__=2; __vtins__1vGn5KEyNxI88WjH=%7B%22sid%22%3A%20%22216c9f22-0d2a-5843-ae6d-37a6f08167c2%22%2C%20%22vd%22%3A%202%2C%20%22stt%22%3A%2013744%2C%20%22dr%22%3A%2013744%2C%20%22expires%22%3A%201629948607372%2C%20%22ct%22%3A%201629946807372%7D");
        String result = Forest.get("https://ip.cn/api/index?ip=&type=0").addHeader(headers).executeAsString();
        // 返回数据格式
        // <200,{"rs":1,"code":0,"address":"中国  北京 北京市 电信","ip":"106.120.64.78","isDomain":0},[Date:"Thu, 26 Aug 2021 03:13:53 GMT", Content-Type:"application/json", Transfer-Encoding:"chunked", Connection:"keep-alive", CF-Cache-Status:"DYNAMIC", Expect-CT:"max-age=604800, report-uri="https://report-uri.cloudflare.com/cdn-cgi/beacon/expect-ct"", Report-To:"{"endpoints":[{"url":"https:\/\/a.nel.cloudflare.com\/report\/v3?s=xxR%2FMmp%2FEx8T6u9lgnFthzXhVATlx2oxK%2FBYEXm6QzOsoWagGQIyiDKYYb0MOKflPKrWg8HFCMEOYvxR1IdIIdlAgOo2%2FDw7pGX1%2F6yzb2jAuozjbuTFcA%3D%3D"}],"group":"cf-nel","max_age":604800}", NEL:"{"success_fraction":0,"report_to":"cf-nel","max_age":604800}", Server:"cloudflare", CF-RAY:"6849e3069e0bc3c4-LAX", alt-svc:"h3-27=":443"; ma=86400, h3-28=":443"; ma=86400, h3-29=":443"; ma=86400, h3=":443"; ma=86400"]>
        String json = result.substring(result.indexOf("{"), result.indexOf("}") + 1);

        // {
        //   "rs":1,
        //   "code":0,
        //   "address":"中国  北京 北京市 电信",
        //   "ip":"106.120.64.78",
        //   "isDomain":0
        // }
        GetPublicAddressResult getPublicAddressResult = JsonUtil.toObject(json, GetPublicAddressResult.class);

        return getPublicAddressResult.getIp();
    }

    @Data
    static class GetPublicAddressResult implements Serializable {

        private Integer rs;

        private Integer code;

        private String address;

        private String ip;

        private Integer isDomain;

    }

}