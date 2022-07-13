package com.ueueo.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定实体成员表示数据关系（如外键关系）。
 *
 * Used to mark an Entity member as an association
 *
 * https://docs.microsoft.com/zh-cn/dotnet/api/system.componentmodel.dataannotations.associationattribute?view=net-6.0
 *
 * @author Lee
 * @date 2022-05-23 11:14
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Association {

    /**
     * Gets the name of the association. For bi-directional associations, the name must
     * be the same on both sides of the association
     */
    String name() default "";

    /**
     * Gets a comma separated list of the property names of the key values
     * on this side of the association
     */
    String[] thisKeys() default {};

    /**
     * Gets a comma separated list of the property names of the key values
     * on the other side of the association
     */
    String[] otherKeys() default {};

    /**
     * Gets or sets a value indicating whether this association member represents
     * the foreign key side of an association
     */
    boolean isForeignKey() default false;

}
