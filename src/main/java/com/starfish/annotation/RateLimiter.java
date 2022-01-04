package com.starfish.annotation;

import java.lang.annotation.*;

/**
 * RateLimiter
 *
 * @author suncolin
 * @version 1.0.0
 * @since 2021-06-09
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RateLimiter {

    boolean value() default true;

}
