package com.sunny.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.sunny.enums.ResultEnum;
import com.sunny.exception.CustomException;
import com.sunny.extension.spring.RestTemplateTool;
import com.sunny.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * HttpUtil
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-07-24
 */
@SuppressWarnings(value = "unused")
@Slf4j
public class HttpUtil {

    /**
     * 淘宝短网址
     */
    private static final String TAOBAO_INTERFACE_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    /**
     * 新浪短网址
     */
    private static final String SINA_INTERFACE_URL = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=";

    /**
     * 搜狐短网址，不是很好用
     */
    public static final String SOHU_INTERFACE_URL = "http://pv.sohu.com/cityjson?ie=utf-8&cip=";

    private static HashMap<String, String> streamType = new HashMap<>();

    static {
        initStreamType();
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
        // 判断是否可以访问
        boolean result = false;
        try {
            RestTemplate restTemplate = RestTemplateTool.buildRestTemplate();
            HttpHeaders httpHeaders = restTemplate.headForHeaders(url);
            String contentType = httpHeaders.getFirst("Content-Type");
            log.info("check media url result,response contentType={}", contentType);

            if (!Strings.isNullOrEmpty(contentType)) {
                if (contentType.startsWith("video")
                        || contentType.startsWith("audio")
                        || contentType.equalsIgnoreCase("application/octet-stream")
                        || contentType.equalsIgnoreCase("application/x-mpegURL")) {
                    result = true;
                }
            }
        } catch (Exception e) {
            log.error("check media url error.url={}", url, e);
            result = false;
        }
        return result;
    }

    public static String getStreamType(String ext) {
        return streamType.getOrDefault(ext.toLowerCase(), "application/octet-stream");
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
            pw = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
            Result<Object> result = new Result<>();
            result.setStatus(code);
            result.setMessage(message);
            result.setBody(data);
            String value = JSON.toJSONString(result);
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
     * get the true ip address
     *
     * @param request request
     * @return the ip
     */
    public static String getInternetProtocolAddress(HttpServletRequest request) {
        String ip = "";
        String[] names = new String[]{"X-Real-IP", "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        for (String string : names) {
            if (HttpUtil.isEmptyOrUnknown(ip)) {
                ip = request.getHeader(string);
            }
        }
        if (HttpUtil.isEmptyOrUnknown(ip)) {
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
    public static String getLocation(String ip) {
        RestTemplate template = new RestTemplate();
        try {
            //call remote interface of sina
            String result = template.getForObject(TAOBAO_INTERFACE_URL + ip, String.class);

            //process the result,the result format is { code: 0, data: { } }
            JSONObject data = JSON.parseObject(result).getJSONObject("data");

            result = data.getString("country") + "##" + data.getString("region") + "##" + data.getString("city");

            //return
            return result;
        } catch (Exception e) {
            //if catch exception,call remote interface of sina
            String result = template.getForObject(SINA_INTERFACE_URL + ip, String.class);

            //process the result,the result format is { ret: 1,  start: -1,  end: -1,  country: "中国",  province: "北京", city: "北京",district: "",  isp: "",  type: "",  desc: ""  }
            JSONObject jsonObject = JSON.parseObject(result);
            result = jsonObject.getString("country") + "##" + jsonObject.getString("province") + "##" + jsonObject.getString("city");

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
    public static String getLocation(HttpServletRequest request) {
        return getLocation(HttpUtil.getInternetProtocolAddress(request));
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
     * 初始化stream type
     */
    private static void initStreamType() {
        streamType.put("rtf", "text/rtf");
        streamType.put("mif", "application/vnd.mif");
        streamType.put("ppt", "application/vnd.ms-powerpoin");
        streamType.put("gtar", "application/x-gtar");
        streamType.put("latex", "application/x-latex");
        streamType.put("aif", "audio/x-aiff");
        streamType.put("ief", "image/ief");
        streamType.put("pgm", "image/x-portable-graymap");
        streamType.put("iges", "model/iges");
        streamType.put("mpe", "text/plain");
        streamType.put("movie", "video/x-sgi-movie");
        streamType.put("ez", "application/andrew-inset");
        streamType.put("smi", "application/smil");
        streamType.put("js", "application/x-javascript");
        streamType.put("nc", "application/x-netcdf");
        streamType.put("cdf", "application/x-netcdf");
        streamType.put("sv4crc", "application/x-sv4crc");
        streamType.put("tar", "application/x-tar");
        streamType.put("tcl", "application/x-tclc");
        streamType.put("roff", "application/x-troff");
        streamType.put("ustar", "application/x-ustar");
        streamType.put("zip", "application/zip");
        streamType.put("snd", "audio/general");
        streamType.put("xyz", "chemical/x-pdb");
        streamType.put("jpg", "image/jpeg");
        streamType.put("png", "image/png");
        streamType.put("tiff", "image/tiff");
        streamType.put("tif", "image/tiff");
        streamType.put("ppm", "image/x-portable-pixmap");
        streamType.put("rgb", "image/x-rgb");
        streamType.put("xpm", "model/vrml");
        streamType.put("wrl", "image/x-xpixmap");
        streamType.put("css", "text/css");
        streamType.put("sgml", "text/sgml");
        streamType.put("etx", "text/x-setext");
        streamType.put("xwd", "image/x-xwindowdump");
        streamType.put("ice", "x-conference/x-cooltalk");
        streamType.put("htm", "text/html");
        streamType.put("doc", "application/msword");
        streamType.put("xls", "application/vnd.ms-excel");
        streamType.put("oda", "application/oda");
        streamType.put("pdf", "application/pdf");
        streamType.put("ai", "application/postscript");
        streamType.put("eps", "application/postscript");
        streamType.put("csh", "application/x-csh");
        streamType.put("dxr", "application/x-director");
        streamType.put("hdf", "application/x-hdf");
        streamType.put("skp", "application/x-koan");
        streamType.put("skd", "application/x-koan");
        streamType.put("skt", "application/x-koan");
        streamType.put("tr", "application/x-troff");
        streamType.put("me", "application/x-troff-me");
        streamType.put("src", "application/x-wais-source");
        streamType.put("au", "audio/general");
        streamType.put("mid", "audio/midi");
        streamType.put("kar", "audio/midi");
        streamType.put("mp3", "audio/mpeg");
        streamType.put("aiff", "audio/x-aiff");
        streamType.put("wav", "audio/x-wav");
        streamType.put("pdb", "chemical/x-pdb");
        streamType.put("gif", "image/gif");
        streamType.put("jpeg", "image/jpeg");
        streamType.put("ras", "image/x-cmu-raster");
        streamType.put("rtx", "text/richtext");
        streamType.put("mpg", "video/mpeg");
        streamType.put("mov", "video/quicktimef");
        streamType.put("avi", "video/x-msvideo");
        streamType.put("hqx", "application/mac-binhex40");
        streamType.put("cpt", "application/mac-compactpro");
        streamType.put("ps", "application/postscript");
        streamType.put("smil", "application/smil");
        streamType.put("bcpio", "application/x-bcpio");
        streamType.put("vcd", "application/x-cdlink");
        streamType.put("dcr", "application/x-director");
        streamType.put("pgn", "application/x-chess-pgn");
        streamType.put("gz", "application/zip");
        streamType.put("tgz", "application/zip");
        streamType.put("midi", "audio/midi");
        streamType.put("mpga", "audio/mpeg");
        streamType.put("rm", "audio/x-pn-realaudio");
        streamType.put("ra", "audio/x-realaudio");
        streamType.put("jpe", "image/jpeg");
        streamType.put("asc", "text/plain");
        streamType.put("xml", "text/xml");
        streamType.put("html", "text/html");
        streamType.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        streamType.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        streamType.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        streamType.put("mmap", "application/vnd.mindjet.mindmanager");
        streamType.put("mmp", "application/vnd.mindjet.mindmanager");
        streamType.put("mmpt", "application/vnd.mindjet.mindmanager");
        streamType.put("mmat", "application/vnd.mindjet.mindmanager");
        streamType.put("mmmp", "application/vnd.mindjet.mindmanager");
        streamType.put("mmas", "application/vnd.mindjet.mindmanager");
        streamType.put("xmind", "application/xmind");
        streamType.put("jar", "application/java-archive");
    }

}
