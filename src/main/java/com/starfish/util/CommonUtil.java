package com.starfish.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.starfish.constant.Constant;
import com.starfish.enums.ResultEnum;
import com.starfish.exception.CustomException;
import com.starfish.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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
     * 问好
     */
    public static final String QUESTION_MARK = "?";

    /**
     * sensitive words list
     */
    private static List<String> sensitiveWordList = new ArrayList<>();

    static {
        sensitiveWordList = FileUtil.readLines("classpath:words.txt");
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
        String className = cls.getName();
        String classNamePath = className.replace(".", "/") + ".class";
        URL url = cls.getClassLoader().getResource(classNamePath);
        String path = url == null ? "" : url.getFile();
        path = path.replace("%20", " ");
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return path;
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
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
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
     * 获取身份证信息
     *
     * @param idCard id card
     * @return the info
     */
    public static String getIdCardInfo(String idCard) {
        return new RestTemplate().getForObject("http://apistore.baidu.com/microservice/icardinfo?id=" + idCard, String.class);
    }

    /**
     * get short url,use dwz short url service
     * 获取短网址，使用百度短网址服务
     *
     * @param url short url
     * @return data
     */
    public static Result<Object> getShortUrl(String url) {
        Result<Object> result = new Result<>();
        //call remote service
        String data = new RestTemplate().getForObject("http://www.dwz.cn/create.php?url=" + url, String.class);

        //process return data {"tinyurl":"http://www.dwz.cn/yes","status":0,"longurl":"http://www.baidu.com","err_msg":""}
        JSONObject jsonObject = JSON.parseObject(data);
        if (0 == jsonObject.getIntValue("status")) {
            result.setStatus(0);
            result.setBody(jsonObject.getString("tinyurl"));
        } else {
            result.setStatus(-1);
            result.setMessage(jsonObject.getString("err_msg"));
        }
        log.info("result {}", JSON.toJSONString(result));
        //return
        return result;
    }

    /**
     * check sensitive words
     * 判断给定的字符串是否包含敏感词
     *
     * @param words words
     * @return return
     */
    public static boolean containsSensitiveWords(String... words) {
        // default flag is false
        boolean flag = false;
        for (String word : words) {
            if (sensitiveWordList.contains(word)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * get the mobile location
     * 查询手机号地址
     * 已经失效了
     *
     * @param mobile the mobile number
     * @return the address,format : mobile##corporation##province##city
     */
    @Deprecated
    public static String getMobileLocation(String mobile) {
        //call remote interface
        String result = new RestTemplate().getForObject("http://virtual.paipai.com/extinfo/GetMobileProductInfo?amount=10000&callname=getPhoneNumInfoExtCallback&mobile=" + mobile, String.class);

        //process the result,the result format is :
        // getPhoneNumInfoExtCallback({mobile:'15850781443',province:'江苏',isp:'中国移动',stock:'1',amount:'10000',maxprice:'0',minprice:'0',cityname:'南京'});
        //<!--[if !IE]>|xGv00|6741027ad78d9b06f5642b25ebcb1536<![endif]-->
        result = result.replace("getPhoneNumInfoExtCallback(", "");
        result = result.substring(0, result.lastIndexOf(");"));

        JSONObject jsonObject = JSON.parseObject(result);
        result = Joiner.on("##").join(jsonObject.getString("mobile"), jsonObject.getString("isp"), jsonObject.getString("province"), jsonObject.getString("cityname"));

        //return
        return result;
    }

    /**
     * 查询天气
     *
     * @param cityName 城市名称
     * @return 结果
     */
    public static String weather(String cityName) {
        String url = "http://apis.baidu.com/apistore/weatherservice/recentweathers?cityname={cityName}";
        url = url.replace("{cityName}", cityName);
//        url = url.replace("{cityId}",cityId);

        //设置请求头 apiKey
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("apikey", Constant.BAIDU_API_KEY);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity data = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        String jsonResult = (String) data.getBody();
        System.out.println(jsonResult);
        return jsonResult;
    }

    /**
     * 解决poi文件名称中文乱码问题
     *
     * @param request  请求
     * @param filename 文件名称
     * @return 结果
     */
    public static String resolveFilenameCharset(HttpServletRequest request, String filename) {
        String result = filename;
        try {
            String agent = request.getHeader("USER-AGENT");
            // ie
            if (null != agent && agent.contains("MSIE") || null != agent && agent.contains("Trident")) {
                result = java.net.URLEncoder.encode(filename, "UTF8");
            }
            // 火狐,chrome等
            else if (null != agent && agent.contains("Mozilla")) {
                result = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 生成知道范围的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 结果
     */
    public static int nextInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * 生成知道范围的随机数
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

}