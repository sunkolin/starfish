package com.starfish.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.starfish.model.PageResult;
import com.starfish.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * ResponseWrapper
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-06-10
 */
@Slf4j
@ControllerAdvice
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // ResponseEntity对象不处理
        Class c = returnType.getMethod().getReturnType();
        return c != ResponseEntity.class && c != ModelAndView.class;
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
            Result<Object> result = new Result<>();
            result.setMessage("notice:body is null");
            return result;
        }
        if (body instanceof String) {
            response.getHeaders().set("Content-Type", "application/json;charset=utf-8");
            return JSON.toJSONString(new Result<>(body), SerializerFeature.WriteMapNullValue);
        }
        if (body instanceof ResponseEntity) {
            return body;
        }
        return new Result<>(body);
    }

}
