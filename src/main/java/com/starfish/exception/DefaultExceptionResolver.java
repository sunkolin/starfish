package com.starfish.exception;

import com.google.common.base.Joiner;
import com.starfish.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Slf4j
@ControllerAdvice
@Component
public class DefaultExceptionResolver {

    public static final int SYSTEM_EXCEPTION_CODE = 500;

    public static final String SYSTEM_EXCEPTION_MESSAGE = "系统异常";

    @ExceptionHandler
    @ResponseBody
    public Result<Object> resolveException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        // 拼接链接和参数
        Map<String, String> map = new HashMap<>(20);
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = request.getParameter(key);
            map.put(key, value);
        }
        String parameters = Joiner.on("&").withKeyValueSeparator("=").join(map);
        String url = request.getRequestURL() + "?" + parameters;

        // 处理异常成错误码返回
        Result<Object> result;
        if (ex instanceof CustomException) {
            CustomException ce = (CustomException) ex;
            result = new Result<>(ce);
            log.error("exception occur.status={},message={},url={}", result.getStatus(), result.getMessage(), url, ex);
        } else {
            result = new Result<>(SYSTEM_EXCEPTION_CODE, SYSTEM_EXCEPTION_MESSAGE);
            log.error("system exception occur.status={},message={},url={}", result.getStatus(), result.getMessage(), url, ex);
        }

        return result;
    }

}
