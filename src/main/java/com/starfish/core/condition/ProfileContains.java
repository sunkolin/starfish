package com.starfish.core.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnProfileContainsCondition.class)
public @interface ProfileContains {

    String[] value();

}

