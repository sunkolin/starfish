package com.starfish.annotation;

import com.starfish.autoconfigure.datasource.DataSourceImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * RateLimiter
 *  限流 TODO
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-06-09
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
//@Import(DataSourceImportSelector.class)
public @interface RateLimiter {

    boolean value() default true;

}
