package com.starfish.context;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * UserRequestContext,在controller service dao中获取request response对象
 * 需要在web.xml中添加listener org.springframework.web.context.request.RequestContextListener
 *
 * @author suncolin
 * @version 1.0.0
 * @since 2015-06-02
 */
@SuppressWarnings("unused")
public class UserContext {

    /**
     * 用户键
     */
    public static final String USER_CONTEXT_KEY = "user_context";

    /**
     * T
     */
    public static final String T_CONTEXT_KEY = "t_context";

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAttribute(String name) {
        return (T) (RequestContextHolder.getRequestAttributes()).getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }

    public static Long getUserId() {
        return getUser().getUserId();
    }

    public static User getUser() {
        return getAttribute(USER_CONTEXT_KEY);
    }

    public static void setUser(User user) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.setAttribute(USER_CONTEXT_KEY, user);
    }

    public static <T> T get() {
        return getAttribute(T_CONTEXT_KEY);
    }

    public static <T> void set(T t) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.setAttribute(T_CONTEXT_KEY, t);
    }

}
