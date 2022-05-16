package com.ueueo.multitenancy;

import org.springframework.lang.NonNull;

import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2022-05-13 21:30
 */
public interface ITenantResolver {
    /**
     * Tries to resolve current tenant using registered {@link ITenantResolveContributor} implementations.
     *
     * @return Tenant id, unique name or null (if could not resolve).
     */
    @NonNull
    Future<TenantResolveResult> resolveTenantIdOrNameAsync();
}
