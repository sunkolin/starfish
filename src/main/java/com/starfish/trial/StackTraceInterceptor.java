package com.starfish.trial;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 打印执行链拦截器
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2016-02-24
 */
@Slf4j
@Component
public class StackTraceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //解决代码风格问题，不可有空方法体
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        List<String> executionChainList = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            executionChainList.add(stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + "() ");
        }
        log.info("url is {},execution chain is {}", request.getRequestURI(), Joiner.on("→").join(executionChainList));
    }

}
