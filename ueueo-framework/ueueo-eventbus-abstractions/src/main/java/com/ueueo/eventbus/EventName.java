package com.ueueo.eventbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-16 09:56
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventName {

    String name() default "";
}
