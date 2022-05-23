package com.ueueo.data.annotations;

/**
 * 指定属性中允许的数组或字符串数据的最小长度。
 *
 * @author Lee
 * @date 2022-05-23 11:13
 */
public @interface MinLength {

    /**
     * 数组或字符串数据的最小允许长度。
     *
     * @return
     */
    int value() default 0;

}
