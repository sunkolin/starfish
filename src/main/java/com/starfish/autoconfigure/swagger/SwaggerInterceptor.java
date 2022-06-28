package com.starfish.autoconfigure.swagger;

import com.starfish.enumeration.ResultEnum;
import com.starfish.util.WebUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SwaggerInterceptor
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-02
 */
@Slf4j
@Data
public class SwaggerInterceptor implements HandlerInterceptor {

//    @Value("${application.swagger.enabled}")
    private Boolean enabled;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (enabled == null || !enabled) {
            log.error("swagger未开启.swaggerEnabled={}", enabled);
            // 此处抛异常在DefaultExceptionResolver中无法捕获，改为write方式
//            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION);
            WebUtil.write(request, response, ResultEnum.SYSTEM_EXCEPTION.getCode(), ResultEnum.SYSTEM_EXCEPTION.getMessage(), null);
        }

        return true;
    }

}
