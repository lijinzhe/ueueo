package com.ueueo.localization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定继承哪些资源文件
 * 在加载国际化文件时，如果 includeBaseLocalizers=true，则会根据继承关系加载继承的国际化文件
 *
 * @author Lee
 * @date 2022-06-02 10:48
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InheritResourceAttribute {
    Class<?>[] resourceTypes() default {};

}
