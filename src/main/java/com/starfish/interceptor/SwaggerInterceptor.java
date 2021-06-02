package com.starfish.interceptor;

import com.starfish.enumeration.ResultEnum;
import com.starfish.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SwaggerInterceptor
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-06-02
 */
@Slf4j
@Component
public class SwaggerInterceptor implements HandlerInterceptor {

    @Value("${application.swagger.enabled}")
    private boolean swaggerEnabled;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!swaggerEnabled) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION);
        }

        return true;
    }

}
