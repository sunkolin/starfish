package com.starfish.core.web;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * RequestWrapperFilter
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2024-08-23
 */
@Slf4j
public class RequestWrapperFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestWrapper requestWrapper = null;

        // 如果请求不是文件上传，使用RequestWrapper包装
        if (request instanceof HttpServletRequest httpServletRequest) {
            String contentType = httpServletRequest.getContentType() == null ? "" : httpServletRequest.getContentType();
            if (!contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
                requestWrapper = new RequestWrapper(httpServletRequest);
            }
        }

        if (requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

}