package com.starfish.core.context;

import com.starfish.core.exception.CustomException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * UserRequestContext,在controller service dao中获取request response对象
 * 需要在web.xml中添加listener org.springframework.web.context.request.RequestContextListener
 *
 * @author sunkolin
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

    /**
     * constructor
     */
    private UserContext() {
    }

    public static HttpServletRequest getRequest() {
        // RequestContextHolder.currentRequestAttributes()代替RequestContextHolder.getRequestAttributes()方法
        // 这两个方法区别是currentRequestAttributes()方法不会返回null，会直接报异常
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) requestAttributes);
        return servletRequestAttributes.getRequest();
    }

    public static HttpServletResponse getResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) requestAttributes);
        return servletRequestAttributes.getResponse();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAttribute(String name) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) requestAttributes);
        return (T) servletRequestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }

    public static Long getUserId() {
        User user = getUser();
        if (user == null) {
            throw new CustomException("getUserId exception.");
        }
        return user.getUserId();
    }

    public static User getUser() {
        return getAttribute(USER_CONTEXT_KEY);
    }

    public static void setUser(User user) {
        HttpServletRequest request = getRequest();
        request.setAttribute(USER_CONTEXT_KEY, user);
    }

    public static <T> T get() {
        return getAttribute(T_CONTEXT_KEY);
    }

    public static <T> void set(T t) {
        HttpServletRequest request = getRequest();
        request.setAttribute(T_CONTEXT_KEY, t);
    }

}
