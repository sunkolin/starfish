package com.starfish.core.interceptor.login;

import com.starfish.core.annotation.RequireToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

/**
 * LoginInterceptor
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-29
 */
@Slf4j
public abstract class LoginInterceptor implements HandlerInterceptor, Ordered {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 默认需要token；
        boolean requireToken = true;
        RequireToken requireTokenOfMethod = AnnotationUtils.findAnnotation(method, RequireToken.class);
        if (requireTokenOfMethod != null) {
            if (!requireTokenOfMethod.value()) {
                requireToken = false;
            }
            return requireToken;
        }

        // 如果方法上没有RequireToken注解，看类上的RequireToken注解
        RequireToken requireTokenOfClass = AnnotationUtils.findAnnotation(handlerMethod.getClass(), RequireToken.class);
        if (requireTokenOfClass != null && !requireTokenOfClass.value()) {
            requireToken = false;
        }

        return requireToken;
    }

    /**
     * 验证是否有写权限
     *
     * @param request  request
     * @param response response
     * @return 结果，有权限返回true，无权限返回false
     */
    public abstract boolean checkWritePermission(HttpServletRequest request, HttpServletResponse response);

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

}
