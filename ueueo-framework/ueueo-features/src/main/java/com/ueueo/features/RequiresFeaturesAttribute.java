package com.ueueo.features;

import java.lang.annotation.*;

/**
 * This attribute can be used on a class/method to declare that given class/method is available
 * only if required feature(s) are enabled.
 *
 * @author Lee
 * @date 2022-05-17 16:56
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RequiresFeaturesAttribute {
    RequiresFeatureAttribute[] requiresFeatures() default {};

}
