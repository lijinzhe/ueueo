package com.ueueo.multitenancy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Lee
 * @date 2022-05-14 17:31
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreMultiTenancy {

}
