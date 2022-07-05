package com.ueueo.validation.annotations;

/**
 * 提供允许为实体分部类的类型和成员指定可本地化字符串的通用特性。
 *
 * @author Lee
 * @date 2022-05-23 11:27
 */
public @interface Display {
    /**
     * 获取或设置一个值，该值指示是否应自动生成用户界面以显示此字段。
     *
     * @return
     */
    boolean autoGenerateField() default false;

    /**
     * 获取或设置一个值，该值指示是否针对此字段自动显示筛选。
     *
     * @return
     */
    boolean autoGenerateFilter() default false;

    /**
     * 获取或设置一个值，该值用于在用户界面中进行显示。
     *
     * @return
     */
    String name() default "";

    /**
     * 获取或设置用于网格列标签的值。
     *
     * @return
     */
    String shortName() default "";

    /**
     * 获取或设置一个值，该值用于在用户界面中对字段进行分组。
     *
     * @return
     */
    String groupName() default "";

    /**
     * 获取或设置一个值，该值用于在用户界面中显示说明。
     *
     * @return
     */
    String description() default "";

    /**
     * 获取或设置列的排序权重。
     *
     * @return
     */
    int order() default 0;

}
