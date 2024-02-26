package com.starfish.core.util;

import com.dtflys.forest.Forest;
import com.dtflys.forest.http.ForestHeaderMap;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.starfish.core.enumeration.ResultEnum;
import com.starfish.core.exception.CustomException;
import com.starfish.core.model.Result;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import com.google.common.collect.Lists;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WebUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @see org.springframework.web.util.WebUtils
 * @see org.springframework.web.util.HtmlUtils
 * @see org.springframework.web.util.JavaScriptUtils
 * @since 2015-07-24
 */
@SuppressWarnings("unused")
@Slf4j
public class WebUtil extends HtmlUtils {

    /**
     * 淘宝查询IP地址接口
     */
    private static final String TAOBAO_INTERFACE_URL = "https://ip.taobao.com/outGetIpInfo?accessKey=alibaba-inc";

    /**
     * 新浪查询IP地址接口
     */
    private static final String SINA_INTERFACE_URL = "https://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json";

    public static final String HTTP_URL_PREFIX = "http";

    public static final String HTTPS_URL_PREFIX = "https";

    public static final String WS_URL_PREFIX = "ws";

    public static final String WSS_URL_PREFIX = "wss";

    private static final Map<String, String> CONTENT_TYPE = new HashMap<>();

    /**
     * 多媒体内容的ContentType
     */
    private static final List<String> MEDIA_CONTENT_TYPE_LIST = Lists.newArrayList("application/x-mpegURL", "application/octet-stream", "video", "audio");

    static {
        initContentType();
    }

    private WebUtil() {
        // constructor
    }

    /**
     * 初始化content type
     */
    private static void initContentType() {
        List<String> contentTypeList = FileUtil.readLines("classpath:application-content-type.txt");
        for (String contentType : contentTypeList) {
            String[] array = contentType.split("=");
            CONTENT_TYPE.put(array[0], array[1]);
        }
    }

    /**
     * 获取Scheme
     *
     * @param url 链接
     * @return 结果
     */
    public static String getScheme(String url) {
        String scheme = "";
        try {
            URL u = new URL(url);
            scheme = u.getProtocol();
        } catch (Exception e) {
            log.error("getProtocol error.url={}", url, e);
        }
        return scheme;
    }

    /**
     * 获取Host
     *
     * @param url 链接
     * @return 结果
     */
    public static String getHost(String url) {
        String host = "";
        try {
            URL u = new URL(url);
            host = u.getHost();
        } catch (Exception e) {
            log.error("getHost error.url={}", url, e);
        }
        return host;
    }

    /**
     * 获取端口，如果没有写明端口号，也会返回
     *
     * @param url 链接
     * @return 结果
     */
    public static String getPort(String url) {
        String result = "";
        try {
            URL u = new URL(url);
            int port = getPort(u.getProtocol(), u.getPort());
            result = String.valueOf(port);
        } catch (Exception e) {
            log.error("getPort error.url={}", url, e);
        }
        return result;
    }

    /**
     * 获取基础url
     *
     * @param url 链接地址，例如https://m.gmw.cn/baijia/2021-03/11/1302158793.html?a=1&b=2&c=12#34
     * @return 结果，例如https://m.gmw.cn:80
     */
    public static String getBaseUrl(String url) {
        String scheme = getScheme(url);
        String host = getHost(url);

        // 获取端口
        String port = "";
        try {
            URL u = new URL(url);
            int intPort = u.getPort();
            if (intPort != -1) {
                port = String.valueOf(intPort);
            }
        } catch (MalformedURLException e) {
            log.error("getBaseUrl,url={}", url, e);
        }

        // 拼接地址，如果端口不为空，拼上端口号
        String result = scheme + "://" + host;
        if (!Strings.isNullOrEmpty(port)) {
            result = result + ":" + port;
        }

        return result;
    }

    /**
     * 获取端口，如果没有写明端口号，也会返回
     *
     * @param scheme scheme
     * @param port   端口
     * @return 结果
     */
    private static int getPort(@Nullable String scheme, int port) {
        if (port == -1) {
            if (HTTP_URL_PREFIX.equals(scheme) || WS_URL_PREFIX.equals(scheme)) {
                port = 80;
            } else if (HTTPS_URL_PREFIX.equals(scheme) || WSS_URL_PREFIX.equals(scheme)) {
                port = 443;
            }
        }
        return port;
    }

    /**
     * 对链接中的中文编码
     *
     * @param url 链接
     * @return 结果
     */
    public static String encode(String url) {
        return org.apache.catalina.util.URLEncoder.DEFAULT.encode(url, StandardCharsets.UTF_8);
    }

    /**
     * 判断一个链接是否是视频链接
     * 验证规则：http开头，mp4，ts，m3u8，flv，mp3，avi，rm，rmvb，mov，vob，asf，wmv，3gp，mkv，mpeg等格式
     *
     * @param url 链接地址
     * @return 结果
     */
    public static boolean existMedia(String url) {
        boolean result = false;

        // 判断是否可以访问
        try {
            ForestHeaderMap forestHeaderMap = ForestUtil.head(url);
            String contentType = forestHeaderMap.getValue("Content-Type");
            log.info("get media contentType,url={},contentType={}", url, contentType);

            // 如果没有Content-Type，返回false
            if (!Strings.isNullOrEmpty(contentType)) {
                for (String mediaContentType : MEDIA_CONTENT_TYPE_LIST) {
                    if (contentType.startsWith(mediaContentType) || contentType.equalsIgnoreCase(mediaContentType)) {
                        result = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            log.error("check media url error.url={}", url, e);
        }

        return result;
    }

    /**
     * 获取content type
     *
     * @param extension 拓展名,例如.png
     * @return 结果
     */
    public static String getContentType(String extension) {
        return CONTENT_TYPE.getOrDefault(extension.toLowerCase(), "application/octet-stream");
    }

    /**
     * 下载网络资源
     *
     * @param url      资源地址
     * @param filePath 文件路径，文件下载后存在本地磁盘上的全路径，包括文件名和类型
     */
    public static void download(String url, String filePath) {
        //判断文件和目录是否存在，不存在则创建
        File file = new File(filePath);
        if (file.exists()) {
            throw new CustomException(ResultEnum.FILE_ALREADY_EXIST);
        }
        File path = file.getParentFile();
        if (!path.exists() && path.mkdirs()) {
            log.info("下载目录不存在，创建目录，目录创建成功");
        }
        try {
            if (file.createNewFile()) {
                log.info("文件不存在，创建文件，创建成功");
            }
        } catch (IOException e) {
            log.info("文件不存在，创建文件，创建失败");
        }
        try (InputStream is = new URL(url).openConnection().getInputStream(); FileOutputStream fs = new FileOutputStream(filePath)) {
            //下载
            int byteRead;
            byte[] buffer = new byte[4 * 1024];
            while ((byteRead = is.read(buffer)) != -1) {
                fs.write(buffer, 0, byteRead);
            }
        } catch (Exception e) {
            throw new CustomException(1001, "下载文件异常");
        }
    }

    /**
     * 输出内容
     *
     * @param response 响应
     * @param value    内容
     */
    public static void write(HttpServletResponse response, String value) {
        PrintWriter printWriter = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            printWriter = response.getWriter();
            printWriter.println(value);
            printWriter.flush();
        } catch (Exception e) {
            log.error("write value error.value is {}", value, e);
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    public static void write(HttpServletResponse response, Object object) {
        String json = JsonUtil.toJson(object);
        write(response, json);
    }

    /**
     * 输出错误
     *
     * @param response 响应
     * @param code     编码
     * @param message  消息
     * @param data     数据
     */
    public static void write(HttpServletResponse response, Integer code, String message, Object data) {
        Result<Object> result = Result.wrapper(code, message, data);
        write(response, result);
    }

    /**
     * 获取请求的真实IP地址
     *
     * @param request request
     * @return the ip
     */
    public static String getInternetProtocolAddress(HttpServletRequest request) {
        String ip = "";
        String[] names = new String[]{"X-Real-IP", "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        for (String string : names) {
            if (WebUtil.isEmptyOrUnknown(ip)) {
                ip = request.getHeader(string);
            }
        }
        if (WebUtil.isEmptyOrUnknown(ip)) {
            ip = request.getRemoteAddr();
        }

        // x-forwarded-for获得的地址如果是多个，取第一个
        String[] ips = ip.split(",");
        if (ips.length > 1) {
            ip = ips[0];
        }

        // return
        log.info(ip);
        return ip;
    }

    /**
     * 判断ip地址是否为空或者未知
     *
     * @param ip ip地址
     * @return 结果
     */
    private static boolean isEmptyOrUnknown(String ip) {
        return ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip);
    }

    /**
     * get the location,use tao bao interface default
     *
     * @param ip ip
     * @return the location
     */
    public static String getAddress(String ip) {
        String result;
        try {
            //call remote interface
            Map<String, Object> params = Map.of("ip", ip);
            String json = Forest.get(TAOBAO_INTERFACE_URL).addQuery(params).executeAsString();

            GetIpAddressResult getIpAddressResult = JsonUtil.toObject(json, GetIpAddressResult.class);
            GetIpAddressData data = getIpAddressResult.getData();

            //return
            return data.getCountry() + " " + data.getRegion() + " " + data.getCity();
        } catch (Exception e) {
            result = "未知";
            return result;
        }
    }

    @Data
    static class GetIpAddressResult implements Serializable {

        private Integer code;

        private String msg;

        private GetIpAddressData data;

    }

    @Data
    static class GetIpAddressData implements Serializable {

        private String area;

        private String country;

        @JsonProperty("isp_id")
        private String ispId;

        private String queryIp;

        private String city;

        private String ip;

        private String isp;

        private String county;

        @JsonProperty("region_id")
        private String regionId;

        @JsonProperty("area_id")
        private String areaId;

        @JsonProperty("county_id")
        private String countyId;

        private String region;

        @JsonProperty("country_id")
        private String countryId;

        @JsonProperty("city_id")
        private String cityId;

    }

    /**
     * get the location
     *
     * @param request request
     * @return the location
     */
    public static String getAddress(HttpServletRequest request) {
        return getAddress(WebUtil.getInternetProtocolAddress(request));
    }

    /**
     * 支持跨域
     *
     * @param response 响应
     */
    public static void supportCross(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type,Accept, Access-Control-Request-Headers, Cookie, Access-Control-Request-Method,Cache-Control, User-Agent, Connection, Accept, u, ck, X-Cookie");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        response.addHeader("Expires", "Thu, 01 Jan 1970 00:00:01 GMT");
        response.addHeader("If-Modified-Since", "0");
    }

    /**
     * JavaScriptUtils中方法
     *
     * @param input 内容
     * @return 过滤掉特殊字符后的内容
     */
    public static String javaScriptEscape(String input) {
        return JavaScriptUtils.javaScriptEscape(input);
    }

    /**
     * 获取Body
     *
     * @param request 请求
     * @return 结果
     */
    public static String getUrl(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    /**
     * 获取param
     *
     * @param request 请求
     * @return 结果
     */
    public static String getParam(HttpServletRequest request) {
        Map<String, String> parameterMap = new HashMap<>(20);
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = request.getParameter(key);
            parameterMap.put(key, value);
        }
        return Joiner.on("&").withKeyValueSeparator("=").join(parameterMap);
    }

    /**
     * 获取Body
     *
     * @param request 请求
     * @return 结果
     */
    public static String getBody(HttpServletRequest request) {
        String body = "";
        try {
            Charset charset = Charset.forName(request.getCharacterEncoding());
            body = StreamUtils.copyToString(request.getInputStream(), charset);
        } catch (IOException e) {
            log.error("getBody exception.");
        }
        return body;
    }

}
