package com.starfish.core.annotation;

import java.lang.annotation.*;

/**
 * 读写权限
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-08-01
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Permission(value = {Permission.READ, Permission.WRITE})
public @interface WritePermission {

    String[] value() default {Permission.READ, Permission.WRITE};

}
