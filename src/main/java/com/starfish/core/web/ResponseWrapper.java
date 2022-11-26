package com.starfish.core.web;

import com.starfish.core.model.PageResult;
import com.starfish.core.model.Result;
import com.starfish.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;

/**
 * ResponseWrapper
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-10
 */
@Slf4j
@ControllerAdvice
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 返回值是ModelAndView或ResponseEntity时不处理
        Method method = returnType.getMethod();
        return method != null && method.getReturnType() != ResponseEntity.class && method.getReturnType() != ModelAndView.class;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Result) {
            return body;
        }
        if (body instanceof PageResult) {
            return body;
        }
        // 当返回类型是String时，用的是StringHttpMessageConverter转换器，无法转换为Json格式
        // 参考：https://www.cnblogs.com/caozx/p/13329468.html
        // 参考：https://blog.csdn.net/chuwengliang4642/article/details/101055473
        // 参考：https://github.com/mingyang66/spring-parent/tree/master/emily-spring-boot-autoconfigure/src/main/java/com/emily/infrastructure/autoconfigure/response/handler

        // 判断内容为null的情况
        if (body == null) {
            log.warn("notice:body is null");
            return Result.success();
        }
        if (body instanceof String) {
            response.getHeaders().set("Content-Type", "application/json;charset=utf-8");
            return JsonUtil.toJson(Result.success(body));
        }
        if (body instanceof ResponseEntity) {
            return body;
        }
        return Result.success(body);
    }

}
