package com.ueueo.features;

import java.lang.annotation.*;

/**
 * @author Lee
 * @date 2022-05-17 16:52
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DisableFeatureCheckAttribute {
}
