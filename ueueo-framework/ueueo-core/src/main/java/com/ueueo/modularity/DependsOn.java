package com.ueueo.modularity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to define dependencies of a type.
 *
 * @author Lee
 * @date 2021-08-24 11:53
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DependsOn {
    Class<?>[] dependedTypes() default {};
}
