package com.starfish.core.annotation;

import java.lang.annotation.*;

/**
 * 权限
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-08-01
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Permission {

    String READ = "read";

    String WRITE = "write";

    String[] values() default {};

}
