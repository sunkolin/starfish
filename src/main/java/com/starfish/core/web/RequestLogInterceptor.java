package com.starfish.core.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * RequestLogInterceptor
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2024-08-22
 */
@Slf4j
@Component
public class RequestLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 出异常时此方法不会被执行
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在此方法中记录日志不管请求成功还是失败都会被记录，可以获得响应状态
        // 出异常时此方法也会被执行
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String params = request.getQueryString();
        String body = RequestWrapper.getBody(request);
        int status = response.getStatus();
        log.info("request log,url={},method={},params={},body={},status={}", url, method, params, body, status);

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
