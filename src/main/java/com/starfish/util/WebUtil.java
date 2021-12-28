package com.starfish.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dtflys.forest.Forest;
import com.dtflys.forest.http.ForestHeader;
import com.dtflys.forest.http.ForestHeaderMap;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.starfish.enumeration.ResultEnum;
import com.starfish.exception.CustomException;
import com.starfish.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
 * @author neacle
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

    private static final Map<String, String> STREAM_TYPE = new HashMap<>();

    /**
     * 多媒体内容的ContentType
     */
    private static final List<String> MEDIA_CONTENT_TYPE_LIST = Lists.newArrayList("application/x-mpegURL", "application/octet-stream", "video", "audio");

    static {
        initStreamType();
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

    public static String getContentType(String ext) {
        return STREAM_TYPE.getOrDefault(ext.toLowerCase(), "application/octet-stream");
    }

    /**
     * 下载网络资源
     *
     * @param url      资源地址
     * @param filePath 文件路径，文件下载后存在本地磁盘上的全路径，包括文件名和类型
     */
    public static void download(String url, String filePath) {
        FileOutputStream fs = null;
        try {
            //判断文件和目录是否存在，不存在则创建
            File file = new File(filePath);
            if (file.exists()) {
                throw new CustomException(ResultEnum.FILE_ALREADY_EXIST);
            }
            File path = file.getParentFile();
            if (!path.exists() && path.mkdirs()) {
                log.info("下载目录不存在，创建目录，目录创建成功");
            }
            if (file.createNewFile()) {
                log.info("文件不存在，创建文件，创建成功");
            }
            //下载
            InputStream is = new URL(url).openConnection().getInputStream();
            fs = new FileOutputStream(filePath);
            int byteRead;
            byte[] buffer = new byte[4 * 1024];
            while ((byteRead = is.read(buffer)) != -1) {
                fs.write(buffer, 0, byteRead);
            }
        } catch (IOException e) {
            throw new CustomException(1001, "下载文件异常");
        } finally {
            try {
                if (fs != null) {
                    fs.flush();
                    fs.close();
                }
            } catch (IOException e) {
                log.error("关闭流异常", e);
            }
        }
    }

    /**
     * 输出内容
     *
     * @param request  请求
     * @param response 响应
     * @param value    内容
     */
    public static void write(HttpServletRequest request, HttpServletResponse response, String value) {
        PrintWriter pw = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            pw = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
            pw.println(value);
            pw.flush();
        } catch (IOException e) {
            log.error("write value error.value is {}", value, e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 输出错误
     *
     * @param request  请求
     * @param response 响应
     * @param code     编码
     * @param message  消息
     * @param data     数据
     */
    public static void write(HttpServletRequest request, HttpServletResponse response, Integer code, String message, Object data) {
        PrintWriter pw = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            pw = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
            Result<Object> result = new Result<>();
            result.setStatus(code);
            result.setMessage(message);
            result.setBody(data);
            String value = JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
            pw.println(value);
            pw.flush();
        } catch (IOException e) {
            log.error("write value error.code is {},message is {},data is {},", code, message, data, e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
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
    private static Boolean isEmptyOrUnknown(String ip) {
        return ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip);
    }

    /**
     * get the location,use taobao interface default
     *
     * @param ip ip
     * @return the location
     */
    public static String getAddress(String ip) {
        try {
            //call remote interface of taobao
            Map<String, Object> params = ImmutableMap.of("ip", ip);
            String result = Forest.get(TAOBAO_INTERFACE_URL).addQuery(params).executeAsString();

            //process the result,the result format is { code: 0, data: { } }
            JSONObject data = JSON.parseObject(result).getJSONObject("data");

            result = data.getString("country") + " " + data.getString("region") + " " + data.getString("city");

            //return
            return result;
        } catch (Exception e) {
            //if catch exception,call remote interface of sina
            Map<String, Object> params = ImmutableMap.of("ip", ip);
            String result = Forest.get(TAOBAO_INTERFACE_URL).addQuery(params).executeAsString();

            //process the result,the result format is { ret: 1,  start: -1,  end: -1,  country: "中国",  province: "北京", city: "北京",district: "",  isp: "",  type: "",  desc: ""  }
            JSONObject jsonObject = JSON.parseObject(result);
            result = jsonObject.getString("country") + " " + jsonObject.getString("province") + " " + jsonObject.getString("city");

            //return
            return result;
        }
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
     * @param request  请求
     * @param response 响应
     */
    public static void supportCross(HttpServletRequest request, HttpServletResponse response) {
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
        if (input == null) {
            return null;
        }

        StringBuilder filtered = new StringBuilder(input.length());
        char prevChar = '\u0000';
        char c;
        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            if (c == '"') {
                filtered.append("\\\"");
            } else if (c == '\'') {
                filtered.append("\\'");
            } else if (c == '\\') {
                filtered.append("\\\\");
            } else if (c == '/') {
                filtered.append("\\/");
            } else if (c == '\t') {
                filtered.append("\\t");
            } else if (c == '\n') {
                if (prevChar != '\r') {
                    filtered.append("\\n");
                }
            } else if (c == '\r') {
                filtered.append("\\n");
            } else if (c == '\f') {
                filtered.append("\\f");
            } else if (c == '\b') {
                filtered.append("\\b");
            }
            // No '\v' in Java, use octal value for VT ascii char
            else if (c == '\013') {
                filtered.append("\\v");
            } else if (c == '<') {
                filtered.append("\\u003C");
            } else if (c == '>') {
                filtered.append("\\u003E");
            }
            // Unicode for PS (line terminator in ECMA-262)
            else if (c == '\u2028') {
                filtered.append("\\u2028");
            }
            // Unicode for LS (line terminator in ECMA-262)
            else if (c == '\u2029') {
                filtered.append("\\u2029");
            } else {
                filtered.append(c);
            }
            prevChar = c;

        }
        return filtered.toString();
    }

    /**
     * 初始化stream type
     */
    private static void initStreamType() {
        initFirstBatchStreamType();
        initSecondBatchStreamType();
    }

    /**
     * 初始化第一批
     */
    private static void initFirstBatchStreamType() {
        STREAM_TYPE.put(".ppt", "application/vnd.ms-powerpoin");
        STREAM_TYPE.put(".movie", "video/x-sgi-movie");
        STREAM_TYPE.put(".js", "application/x-javascript");
        STREAM_TYPE.put(".tar", "application/x-tar");
        STREAM_TYPE.put(".zip", "application/zip");
        STREAM_TYPE.put(".jpg", "image/jpg");
        STREAM_TYPE.put(".png", "image/png");
        STREAM_TYPE.put(".tif", "image/tiff");
        STREAM_TYPE.put(".rgb", "image/x-rgb");
        STREAM_TYPE.put(".css", "text/css");
        STREAM_TYPE.put(".htm", "text/html");
        STREAM_TYPE.put(".doc", "application/msword");
        STREAM_TYPE.put(".xls", "application/vnd.ms-excel");
        STREAM_TYPE.put(".pdf", "application/pdf");
        STREAM_TYPE.put(".mp3", "audio/mpeg");
        STREAM_TYPE.put(".wav", "audio/x-wav");
        STREAM_TYPE.put(".gif", "image/gif");
        STREAM_TYPE.put(".jpeg", "image/jpeg");
        STREAM_TYPE.put(".mpg", "video/mpeg");
        STREAM_TYPE.put(".mov", "video/quicktimef");
        STREAM_TYPE.put(".avi", "video/x-msvideo");
        STREAM_TYPE.put(".gz", "application/zip");
        STREAM_TYPE.put(".tgz", "application/zip");
        STREAM_TYPE.put(".midi", "audio/midi");
        STREAM_TYPE.put(".rm", "audio/x-pn-realaudio");
        STREAM_TYPE.put(".xml", "text/xml");
        STREAM_TYPE.put(".html", "text/html");
        STREAM_TYPE.put(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        STREAM_TYPE.put(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        STREAM_TYPE.put(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        STREAM_TYPE.put(".xmind", "application/xmind");
        STREAM_TYPE.put(".jar", "application/java-archive");
    }

    /**
     * 初始化第二批
     */
    private static void initSecondBatchStreamType() {
        STREAM_TYPE.put("rtf", "text/rtf");
        STREAM_TYPE.put("mif", "application/vnd.mif");
        STREAM_TYPE.put("gtar", "application/x-gtar");
        STREAM_TYPE.put("latex", "application/x-latex");
        STREAM_TYPE.put("aif", "audio/x-aiff");
        STREAM_TYPE.put("ief", "image/ief");
        STREAM_TYPE.put("pgm", "image/x-portable-graymap");
        STREAM_TYPE.put("iges", "model/iges");
        STREAM_TYPE.put("mpe", "text/plain");
        STREAM_TYPE.put("ez", "application/andrew-inset");
        STREAM_TYPE.put("smi", "application/smil");
        STREAM_TYPE.put("nc", "application/x-netcdf");
        STREAM_TYPE.put("cdf", "application/x-netcdf");
        STREAM_TYPE.put("sv4crc", "application/x-sv4crc");
        STREAM_TYPE.put("tcl", "application/x-tclc");
        STREAM_TYPE.put("roff", "application/x-troff");
        STREAM_TYPE.put("ustar", "application/x-ustar");
        STREAM_TYPE.put("snd", "audio/general");
        STREAM_TYPE.put("xyz", "chemical/x-pdb");
        STREAM_TYPE.put("tiff", "image/tiff");
        STREAM_TYPE.put("ppm", "image/x-portable-pixmap");
        STREAM_TYPE.put("xpm", "model/vrml");
        STREAM_TYPE.put("wrl", "image/x-xpixmap");
        STREAM_TYPE.put("sgml", "text/sgml");
        STREAM_TYPE.put("etx", "text/x-setext");
        STREAM_TYPE.put("xwd", "image/x-xwindowdump");
        STREAM_TYPE.put("ice", "x-conference/x-cooltalk");
        STREAM_TYPE.put("oda", "application/oda");
        STREAM_TYPE.put("ai", "application/postscript");
        STREAM_TYPE.put("eps", "application/postscript");
        STREAM_TYPE.put("csh", "application/x-csh");
        STREAM_TYPE.put("dxr", "application/x-director");
        STREAM_TYPE.put("hdf", "application/x-hdf");
        STREAM_TYPE.put("skp", "application/x-koan");
        STREAM_TYPE.put("skd", "application/x-koan");
        STREAM_TYPE.put("skt", "application/x-koan");
        STREAM_TYPE.put("tr", "application/x-troff");
        STREAM_TYPE.put("me", "application/x-troff-me");
        STREAM_TYPE.put("src", "application/x-wais-source");
        STREAM_TYPE.put("au", "audio/general");
        STREAM_TYPE.put("mid", "audio/midi");
        STREAM_TYPE.put("kar", "audio/midi");
        STREAM_TYPE.put("aiff", "audio/x-aiff");
        STREAM_TYPE.put("pdb", "chemical/x-pdb");
        STREAM_TYPE.put("ras", "image/x-cmu-raster");
        STREAM_TYPE.put("rtx", "text/richtext");
        STREAM_TYPE.put("hqx", "application/mac-binhex40");
        STREAM_TYPE.put("cpt", "application/mac-compactpro");
        STREAM_TYPE.put("ps", "application/postscript");
        STREAM_TYPE.put("smil", "application/smil");
        STREAM_TYPE.put("bcpio", "application/x-bcpio");
        STREAM_TYPE.put("vcd", "application/x-cdlink");
        STREAM_TYPE.put("dcr", "application/x-director");
        STREAM_TYPE.put("pgn", "application/x-chess-pgn");
        STREAM_TYPE.put("mpga", "audio/mpeg");
        STREAM_TYPE.put("ra", "audio/x-realaudio");
        STREAM_TYPE.put("jpe", "image/jpeg");
        STREAM_TYPE.put("asc", "text/plain");
        STREAM_TYPE.put("mmap", "application/vnd.mindjet.mindmanager");
        STREAM_TYPE.put("mmp", "application/vnd.mindjet.mindmanager");
        STREAM_TYPE.put("mmpt", "application/vnd.mindjet.mindmanager");
        STREAM_TYPE.put("mmat", "application/vnd.mindjet.mindmanager");
        STREAM_TYPE.put("mmmp", "application/vnd.mindjet.mindmanager");
        STREAM_TYPE.put("mmas", "application/vnd.mindjet.mindmanager");
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
