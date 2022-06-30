package com.ueueo.remote;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO: Can we move this to another package (with IRemoteService)?
 *
 * @author Lee
 * @date 2022-06-13 15:59
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RemoteServiceAttribute {

    /**
     * Default: true.
     */
    boolean enabled() default true;

    /**
     * Default: true.
     */
    boolean metadataEnabled() default true;

    /**
     * Group name of the remote service.
     * Group names of all services of a module expected to be the same.
     * This name is also used to distinguish the service endpoint of this group.
     */
    String name() default "";
}
