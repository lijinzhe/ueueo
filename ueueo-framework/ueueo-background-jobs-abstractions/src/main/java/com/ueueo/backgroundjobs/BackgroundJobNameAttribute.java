package com.ueueo.backgroundjobs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Lee
 * @date 2022-06-08 22:50
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BackgroundJobNameAttribute {
    String name() default "";
}
