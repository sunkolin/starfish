package com.starfish.core.web;

import com.google.common.base.Strings;
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
        String contentType = request.getContentType();
        if (!Strings.isNullOrEmpty(contentType) && contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            chain.doFilter(request, response);
        } else {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            chain.doFilter(new RequestWrapper(httpServletRequest), response);
        }
    }
}