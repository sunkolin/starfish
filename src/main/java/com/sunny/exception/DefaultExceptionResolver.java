package com.sunny.exception;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * DefaultExceptionResolver
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-12-02
 */
@Component
public class DefaultExceptionResolver implements HandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Map<String, String> map = new HashMap<>();
        Enumeration parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement().toString();
            String value = request.getParameter(key);
            map.put(key, value);
        }
        String parameters = Joiner.on("&").withKeyValueSeparator("=").join(map);

        String url = request.getRequestURL() + "?" + parameters;


        if (ex instanceof CheckedException) {
            CheckedException checkedException = (CheckedException) ex;
            LOGGER.error("exception occur.url is {},description is {},code is {},message is {}", url, checkedException.getDescription(), checkedException.getCode(), checkedException.getMessage(), ex);
            return newInstance(checkedException.getCode(), checkedException.getMessage(), checkedException.getDescription());
        }

        if (ex instanceof CustomException) {
            CustomException customException = (CustomException) ex;
            LOGGER.error("exception occur.url is {},description is {},code is {},message is {}", url, customException.getDescription(), customException.getCode(), customException.getMessage(), ex);
            return newInstance(customException.getCode(), customException.getMessage(), customException.getDescription());
        }
        LOGGER.error("system exception occur.url is {}", url, ex);

        return newInstance(-1, "system exception", ex.getMessage());
    }

    private ModelAndView newInstance(Integer status, String message, String description) {
        ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("status", status);
        attributes.put("message", message);
        attributes.put("description", description);
        view.setAttributesMap(attributes);
        mv.setView(view);
        return mv;
    }

}
