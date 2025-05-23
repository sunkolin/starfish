package com.starfish.core.exception;

import com.google.common.base.Joiner;
import com.starfish.core.model.Result;
import com.starfish.core.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * DefaultExceptionResolver
 * 需要使用ExceptionResolverAutoConfiguration扫描包，异常处理器才能对controller和拦截器同时生效
 * 直接在spring.factories中配置DefaultExceptionResolver时对拦截器不生效
 * 当前配置对于使用本jar的项目中的controller和interceptor有效，对于starfish中的interceptor无效
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-12-02
 */
@SuppressWarnings("unused")
@Slf4j
@ControllerAdvice(basePackages = {"com.starfish"})
public class DefaultExceptionResolver {

    public static final int SYSTEM_EXCEPTION_CODE = 500;

    public static final String SYSTEM_EXCEPTION_MESSAGE = "系统异常";

    @ExceptionHandler
    @ResponseBody
    public Result<Object> resolveException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        // 请求地址
        // request.getRequestURL() 返回全路径
        //request.getRequestURI() 返回除去host（域名或者ip）部分的路径
        String url = request.getRequestURL().toString();

        // 参数
        Map<String, String> map = new HashMap<>(20);
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = request.getParameter(key);
            map.put(key, value);
        }
        String param = Joiner.on("&").withKeyValueSeparator("=").join(map);

        // body
        String body = WebUtil.getBody(request);

        // 处理异常成错误码返回
        Result<Object> result;
        if (ex instanceof CustomException ce) {
            result =  Result.fail(ce.getCode(),ce.getMessage());
            log.error("GlobalExceptionResolver exception.code={},message={},url={},param={},body={}", result.getCode(), result.getMessage(), url, param, body, ex);
        } else {
            result = Result.fail(SYSTEM_EXCEPTION_CODE, SYSTEM_EXCEPTION_MESSAGE);
            log.error("GlobalExceptionResolver system exception.code={},message={},url={}", result.getCode(), result.getMessage(), url, ex);
        }

        return result;
    }

}
