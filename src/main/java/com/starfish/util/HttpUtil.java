package com.starfish.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.starfish.enums.ResultEnum;
import com.starfish.exception.CustomException;
import com.starfish.model.Result;
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

    private static final HashMap<String, String> STREAM_TYPE = new HashMap<>();

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
            RestTemplate restTemplate = new RestTemplate();
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
        STREAM_TYPE.put("rtf", "text/rtf");
        STREAM_TYPE.put("mif", "application/vnd.mif");
        STREAM_TYPE.put("ppt", "application/vnd.ms-powerpoin");
        STREAM_TYPE.put("gtar", "application/x-gtar");
        STREAM_TYPE.put("latex", "application/x-latex");
        STREAM_TYPE.put("aif", "audio/x-aiff");
        STREAM_TYPE.put("ief", "image/ief");
        STREAM_TYPE.put("pgm", "image/x-portable-graymap");
        STREAM_TYPE.put("iges", "model/iges");
        STREAM_TYPE.put("mpe", "text/plain");
        STREAM_TYPE.put("movie", "video/x-sgi-movie");
        STREAM_TYPE.put("ez", "application/andrew-inset");
        STREAM_TYPE.put("smi", "application/smil");
        STREAM_TYPE.put("js", "application/x-javascript");
        STREAM_TYPE.put("nc", "application/x-netcdf");
        STREAM_TYPE.put("cdf", "application/x-netcdf");
        STREAM_TYPE.put("sv4crc", "application/x-sv4crc");
        STREAM_TYPE.put("tar", "application/x-tar");
        STREAM_TYPE.put("tcl", "application/x-tclc");
        STREAM_TYPE.put("roff", "application/x-troff");
        STREAM_TYPE.put("ustar", "application/x-ustar");
        STREAM_TYPE.put("zip", "application/zip");
        STREAM_TYPE.put("snd", "audio/general");
        STREAM_TYPE.put("xyz", "chemical/x-pdb");
        STREAM_TYPE.put("jpg", "image/jpeg");
        STREAM_TYPE.put("png", "image/png");
        STREAM_TYPE.put("tiff", "image/tiff");
        STREAM_TYPE.put("tif", "image/tiff");
        STREAM_TYPE.put("ppm", "image/x-portable-pixmap");
        STREAM_TYPE.put("rgb", "image/x-rgb");
        STREAM_TYPE.put("xpm", "model/vrml");
        STREAM_TYPE.put("wrl", "image/x-xpixmap");
        STREAM_TYPE.put("css", "text/css");
        STREAM_TYPE.put("sgml", "text/sgml");
        STREAM_TYPE.put("etx", "text/x-setext");
        STREAM_TYPE.put("xwd", "image/x-xwindowdump");
        STREAM_TYPE.put("ice", "x-conference/x-cooltalk");
        STREAM_TYPE.put("htm", "text/html");
        STREAM_TYPE.put("doc", "application/msword");
        STREAM_TYPE.put("xls", "application/vnd.ms-excel");
        STREAM_TYPE.put("oda", "application/oda");
        STREAM_TYPE.put("pdf", "application/pdf");
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
        STREAM_TYPE.put("mp3", "audio/mpeg");
        STREAM_TYPE.put("aiff", "audio/x-aiff");
        STREAM_TYPE.put("wav", "audio/x-wav");
        STREAM_TYPE.put("pdb", "chemical/x-pdb");
        STREAM_TYPE.put("gif", "image/gif");
        STREAM_TYPE.put("jpeg", "image/jpeg");
        STREAM_TYPE.put("ras", "image/x-cmu-raster");
        STREAM_TYPE.put("rtx", "text/richtext");
        STREAM_TYPE.put("mpg", "video/mpeg");
        STREAM_TYPE.put("mov", "video/quicktimef");
        STREAM_TYPE.put("avi", "video/x-msvideo");
        STREAM_TYPE.put("hqx", "application/mac-binhex40");
        STREAM_TYPE.put("cpt", "application/mac-compactpro");
        STREAM_TYPE.put("ps", "application/postscript");
        STREAM_TYPE.put("smil", "application/smil");
        STREAM_TYPE.put("bcpio", "application/x-bcpio");
        STREAM_TYPE.put("vcd", "application/x-cdlink");
        STREAM_TYPE.put("dcr", "application/x-director");
        STREAM_TYPE.put("pgn", "application/x-chess-pgn");
        STREAM_TYPE.put("gz", "application/zip");
        STREAM_TYPE.put("tgz", "application/zip");
        STREAM_TYPE.put("midi", "audio/midi");
        STREAM_TYPE.put("mpga", "audio/mpeg");
        STREAM_TYPE.put("rm", "audio/x-pn-realaudio");
        STREAM_TYPE.put("ra", "audio/x-realaudio");
        STREAM_TYPE.put("jpe", "image/jpeg");
        STREAM_TYPE.put("asc", "text/plain");
        STREAM_TYPE.put("xml", "text/xml");
        STREAM_TYPE.put("html", "text/html");
        STREAM_TYPE.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        STREAM_TYPE.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        STREAM_TYPE.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        STREAM_TYPE.put("mmap", "application/vnd.mindjet.mindmanager");
        STREAM_TYPE.put("mmp", "application/vnd.mindjet.mindmanager");
        STREAM_TYPE.put("mmpt", "application/vnd.mindjet.mindmanager");
        STREAM_TYPE.put("mmat", "application/vnd.mindjet.mindmanager");
        STREAM_TYPE.put("mmmp", "application/vnd.mindjet.mindmanager");
        STREAM_TYPE.put("mmas", "application/vnd.mindjet.mindmanager");
        STREAM_TYPE.put("xmind", "application/xmind");
        STREAM_TYPE.put("jar", "application/java-archive");
    }

}
