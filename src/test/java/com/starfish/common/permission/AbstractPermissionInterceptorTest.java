package com.starfish.common.permission;

import com.starfish.core.interceptor.permission.AbstractPermissionInterceptor;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * AbstractPermissionInterceptor
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-29
 */
@Slf4j
public abstract class AbstractPermissionInterceptorTest extends AbstractPermissionInterceptor {

    @Override
    public boolean checkReadPermission(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    public boolean checkWritePermission(HttpServletRequest request, HttpServletResponse response) {
        return false;
    }

}
