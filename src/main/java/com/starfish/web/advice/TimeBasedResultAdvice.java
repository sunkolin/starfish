package com.starfish.web.advice;

import com.starfish.core.constant.Constant;
import com.starfish.core.model.TimeBasedResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * ResponseAdvice，修改controller返回的结果
 *
 * @author sunny
 * @version 1.0.0
 * @since 2019-07-03
 */
@ControllerAdvice
public class TimeBasedResultAdvice implements ResponseBodyAdvice<TimeBasedResult<Object>> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public TimeBasedResult<Object> beforeBodyWrite(TimeBasedResult<Object> body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
        HttpServletRequest httpServletRequest = servletServerHttpRequest.getServletRequest();

        Long startTime = (Long) httpServletRequest.getAttribute(Constant.REQUEST_START_TIME);
        Long endTime = System.currentTimeMillis();

        body.setRequestTime(startTime);
        body.setElapsedTime(endTime - startTime);

        return body;
    }

}