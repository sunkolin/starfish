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
 * @author sunny
 * @version 1.0.0
 * @since 2015-06-02
 */
@SuppressWarnings(value = "unused")
public class UserContext {

    /**
     * 用户键
     */
    public static final String USER_CONTEXT_KEY = "user_context_key";

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    @SuppressWarnings(value = "unchecked")
    public static <T> T getAttribute(String name) {
        return (T) (RequestContextHolder.getRequestAttributes()).getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }

    public static Long getUserId() {
        return getUser().getUserId();
    }

    public static String getUsername() {
        return getUser().getUserName();
    }

    public static String getNickname() {
        return getUser().getNickName();
    }

    private static User getUser() {
        return getAttribute(USER_CONTEXT_KEY);
    }

}
