package com.ueueo.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Can be added to a method to enable auto validation if validation is disabled for it's class.
 *
 * @author Lee
 * @date 2022-05-29 17:15
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableValidationAttribute {
}
