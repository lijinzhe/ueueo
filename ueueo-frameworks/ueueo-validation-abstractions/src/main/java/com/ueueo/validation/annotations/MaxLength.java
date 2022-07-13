package com.ueueo.validation.annotations;

/**
 * 指定属性中允许的数组或字符串数据的最大长度。
 *
 * @author Lee
 * @date 2022-05-23 11:13
 */
public @interface MaxLength {

    /**
     * 数组或字符串数据的最大允许长度。
     *
     * @return
     */
    int value() default 0;

}
