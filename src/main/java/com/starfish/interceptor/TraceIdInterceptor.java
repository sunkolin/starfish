package com.starfish.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TraceInterceptor
 *
 * @author sunny
 * @version 1.0.0
 * @since 2018-12-05
 */
@Slf4j
@Component
public class TraceIdInterceptor implements HandlerInterceptor {

    @Resource
    private Tracer sleuthTracer;

    @Resource
    private brave.Tracer tracer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            // brave方式获取traceId
//           String traceId = tracer.currentSpan().context().traceIdString();

            // 新的方式获取traceId
            String traceId = sleuthTracer.currentSpan().context().traceId();
            String spanId = sleuthTracer.currentSpan().context().spanId();
            // 设置Header
            response.setHeader("traceId", traceId);
        } catch (Exception e) {
            log.error("TraceIdInterceptor get trace id error.", e);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
