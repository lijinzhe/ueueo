package com.ueueo.multitenancy;

/**
 * @author Lee
 * @date 2022-05-13 21:29
 */
public interface ITenantResolveContributor {
    String getName();

    void resolve(ITenantResolveContext context);
}
