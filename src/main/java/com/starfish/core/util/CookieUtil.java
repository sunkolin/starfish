package com.starfish.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * CookieUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-05-08
 */
@SuppressWarnings("unused")
public class CookieUtil {

    /**
     * path
     */
    public static transient String PATH = "/";

    /**
     * EMPTY
     */
    public static transient String EMPTY = "";

    /**
     * 域名根据.分隔的长度
     */
    public static transient int DOMAIN_LENGTH = 2;

    /**
     * cookie默认过期时间
     */
    public static final int DEFAULT_MAX_AGE = 7 * 24 * 60 * 60 * 1000;

    /**
     * 域名后缀
     */
    private static final List<String> DOMAIN_SUFFIX = Arrays.asList(".com", ".cn", ".com.cn", ".net", ".org", ".org.cn", ".net.cn", ".me", ".gov.cn", ".biz", ".name", ".info", ".cc", ".so", ".space", ".travel", ".tv", ".mobi", ".asia", ".co", ".hk", ".tw", ".us", ".ph", ".cd", ".tel", ".pw", ".jp", ".xyz", ".la", ".tm", ".website", ".host", ".press", ".com.tw", ".wang", ".top", ".club", ".ren", ".pub", ".market", ".cool", ".company", ".city", ".email", ".ninja", ".bike", ".today", ".life", ".rocks", ".band", ".software", ".social", ".lawyer", ".engineer", ".我爱你", ".中国", ".公司", ".网络", ".集团", ".移动", ".在线", ".中文网");

    /**
     * 获取Cookie值
     *
     * @param request 请求
     * @param name    cookie 名称
     * @return cookie值
     */
    public static String getCookie(HttpServletRequest request, String name) {
        String result = EMPTY;
        Cookie[] cookieArray = request.getCookies();
        if (cookieArray != null) {
            for (Cookie cookie : cookieArray) {
                if (cookie.getName().equals(name)) {
                    result = cookie.getValue();
                }
            }
        }
        return result;
    }

    /**
     * 获取 Cookie
     *
     * @param request  请求
     * @param response 响应
     * @param name     cookie名称
     * @return cookie值
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        String result = "";
        Cookie[] cookieArray = request.getCookies();
        if (cookieArray != null) {
            for (Cookie cookie : cookieArray) {
                if (cookie.getName().equals(name)) {
                    result = cookie.getValue();
                }
            }
        }
        return result;
    }

    /**
     * 设置 Cookie
     *
     * @param request  请求
     * @param response 响应
     * @param domain   域名
     * @param maxAge   过期时间
     * @param name     名称
     * @param value    值
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String domain, int maxAge, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        if (isDomain(domain)) {
            cookie.setDomain(getDomain(domain));
        }
        cookie.setPath(PATH);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 设置 Cookie 默认过期时间 7 * 24 小时
     *
     * @param request  请求
     * @param response 响应
     * @param name     名称
     * @param value    值
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        setCookie(request, response, request.getServerName(), DEFAULT_MAX_AGE, name, value);
    }

    /**
     * 设置 Cookie 默认过期时间 7 * 24 小时
     *
     * @param request  请求
     * @param response 响应
     * @param domain   域名
     * @param name     名称
     * @param value    值
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String domain, String name, String value) {
        setCookie(request, response, domain, DEFAULT_MAX_AGE, name, value);
    }

    /**
     * 设置 Cookie
     *
     * @param request  请求
     * @param response 响应
     * @param maxAge   过期时间
     * @param name     名称
     * @param value    值
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, int maxAge, String name, String value) {
        setCookie(request, response, request.getServerName(), maxAge, name, value);
    }

    /**
     * 判断是否是域名
     *
     * @param domain 域名
     * @return 结果
     */
    public static boolean isDomain(String domain) {
        for (String d : DOMAIN_SUFFIX) {
            if (domain.contains(d)) {
                return true;
            }
        }

        return false;
    }

    public static String getDomain(String domain) {
        String[] splits = domain.split("\\.");
        if (splits.length >= DOMAIN_LENGTH) {
            return "." + splits[splits.length - 2] + "." + splits[splits.length - 1];
        }
        return domain;
    }

}
