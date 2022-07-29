package com.starfish.common.permission;

import com.starfish.core.annotation.Permission;
import com.starfish.core.annotation.ReadPermission;
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
 * PermissionInterceptor
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-29
 */
@Slf4j
public abstract class AbstractPermissionInterceptor implements HandlerInterceptor, Ordered {

    @Override
    @ReadPermission
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Permission permission = AnnotationUtils.findAnnotation(method, Permission.class);

        // 没有注解，默认不验证权限
        if (permission == null) {
            return true;
        }

        String[] value = permission.value();
        List<String> values = Lists.newArrayList(value);

        // 验证读权限
        if (values.contains(Permission.READ)) {
            boolean checkReadPermissionResult = checkReadPermission(request, response);
            if (!checkReadPermissionResult) {
                throw new CustomException(ResultEnum.NO_READ_PERMISSION);
            }
        }

        // 验证写权限
        if (values.contains(Permission.WRITE)) {
            boolean checkWritePermissionResult = this.checkWritePermission(request, response);
            if (!checkWritePermissionResult) {
                throw new CustomException(ResultEnum.NO_WRITE_PERMISSION);
            }
        }

        return true;
    }

    @Override
    public int getOrder() {
        return 1;
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

}
