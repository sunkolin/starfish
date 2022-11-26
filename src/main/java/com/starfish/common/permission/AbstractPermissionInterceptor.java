package com.starfish.common.permission;

import com.starfish.core.annotation.Permission;
import com.starfish.core.enumeration.ResultEnum;
import com.starfish.core.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * AbstractPermissionInterceptor
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-29
 */
@Slf4j
public abstract class AbstractPermissionInterceptor implements HandlerInterceptor, Ordered {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Permission permission = AnnotationUtils.findAnnotation(method, Permission.class);

        // 没有注解，默认不验证权限，有权限注解才验证权限
        checkPermission(request, response, permission);

        return true;
    }

    /**
     * 验证权限
     *
     * @param permission 权限
     */
    public void checkPermission(HttpServletRequest request, HttpServletResponse response, Permission permission) {
        if (permission == null) {
            return;
        }

        String[] value = permission.value();
        List<String> values = Lists.newArrayList(value);

        // 验证读权限
        if (values.contains(Permission.READ)) {
            boolean hasReadPermissionResult = checkReadPermission(request, response);
            if (!hasReadPermissionResult) {
                throw new CustomException(ResultEnum.NO_READ_PERMISSION);
            }
        }

        // 验证写权限
        if (values.contains(Permission.WRITE)) {
            boolean hasWritePermissionResult = this.checkWritePermission(request, response);
            if (!hasWritePermissionResult) {
                throw new CustomException(ResultEnum.NO_WRITE_PERMISSION);
            }
        }
    }

    /**
     * 验证是否有读权限
     *
     * @param request  request
     * @param response response
     * @return 结果，有权限返回true，无权限返回false
     */
    public abstract boolean checkReadPermission(HttpServletRequest request, HttpServletResponse response);

    /**
     * 验证是否有写权限
     *
     * @param request  request
     * @param response response
     * @return 结果，有权限返回true，无权限返回false
     */
    public abstract boolean checkWritePermission(HttpServletRequest request, HttpServletResponse response);

    @Override
    public int getOrder() {
        return 1;
    }

}
