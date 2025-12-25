package com.starfish.core.annotation;

import java.lang.annotation.*;

/**
 * RequireToken
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2025-12-02
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequireToken {

    boolean value() default true;

}
