package com.ueueo.validation.stringvalues;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Lee
 * @date 2022-05-29 16:49
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringValueTypeAttribute {
    String name() default "";
}
