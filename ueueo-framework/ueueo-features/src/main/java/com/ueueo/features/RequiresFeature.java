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
public @interface RequiresFeature {
    /**
     * A list of features to be checked if they are enabled.
     *
     * @return
     */
    String[] features() default {};

    /**
     * If this property is set to true, all of the <see cref="Features"/> must be enabled.
     * If it's false, at least one of the <see cref="Features"/> must be enabled.
     * Default: false.
     *
     * @return
     */
    boolean requiresAll() default false;

}
