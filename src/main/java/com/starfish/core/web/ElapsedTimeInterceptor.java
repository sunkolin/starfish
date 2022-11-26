package com.starfish.core.web;

import com.starfish.core.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 耗时拦截器
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-04
 */
@SuppressWarnings("unused")
@Slf4j
public class ElapsedTimeInterceptor implements HandlerInterceptor, Ordered {

    /**
     * spend time header name
     */
    public static final String SPEND_TIME_HEADER_NAME = "spendTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute(Constant.REQUEST_START_TIME, startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        long endTime = System.currentTimeMillis();
        long spendTime = endTime - ((Long) request.getAttribute(Constant.REQUEST_START_TIME));
        log.info("request url is {},spend time is {}", request.getRequestURL(), spendTime);
        response.setHeader(SPEND_TIME_HEADER_NAME, String.valueOf(spendTime));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        // 解决代码风格问题，不可有空方法体
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

}
