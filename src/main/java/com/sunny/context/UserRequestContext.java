package com.sunny.context;

import com.sunny.constant.Constant;
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
public abstract class UserRequestContext {

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

    public static Long getId() {
        UserHolder userHolder = getAttribute(Constant.USER_HOLDER_KEY);
        return userHolder.getId();
    }

    public static String getNickname() {
        UserHolder userHolder = getAttribute(Constant.USER_HOLDER_KEY);
        return userHolder.getNickname();
    }

    public static String getUsername() {
        UserHolder userHolder = getAttribute(Constant.USER_HOLDER_KEY);
        return userHolder.getUsername();
    }

    public static String getRole() {
        UserHolder userHolder = getAttribute(Constant.USER_HOLDER_KEY);
        return userHolder.getRole();
    }

    public static Object getObject() {
        UserHolder userHolder = getAttribute(Constant.USER_HOLDER_KEY);
        return userHolder.getObject();
    }

}
