package com.ueueo.uow;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to indicate that declaring method (or all methods of the class) is atomic and should be considered as a unit of work (UOW).
 * This attribute has no effect if there is already a unit of work before calling this method. It uses the ambient UOW in this case.
 *
 * @author Lee
 * @date 2022-05-29 15:11
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UnitOfWorkAttribute {

    /**
     * Is this UOW transactional?
     * Uses default value if not supplied.
     */
    boolean isTransactional() default false;

    /**
     * Timeout of UOW As milliseconds.
     * Uses default value if not supplied.
     */
    int timeout() default 0;

    /**
     * If this UOW is transactional, this option indicated the isolation level of the transaction.
     * Uses default value if not supplied.
     */
    IsolationLevel isolationLevel() default IsolationLevel.Unspecified;

    /**
     * Used to prevent starting a unit of work for the method.
     * If there is already a started unit of work, this property is ignored.
     * Default: false.
     */
    boolean isDisabled() default false;
}
