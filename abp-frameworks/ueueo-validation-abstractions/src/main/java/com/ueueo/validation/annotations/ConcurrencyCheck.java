package com.ueueo.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定属性参与乐观并发检查。
 * This attribute is used to mark the members of a Type that participate in optimistic concurrency checks.
 * @author Lee
 * @date 2022-05-23 15:22
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConcurrencyCheck {
}
