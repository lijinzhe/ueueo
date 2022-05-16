package com.ueueo.multitenancy;

import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2022-05-13 21:29
 */
public interface ITenantResolveContributor {
    String getName();

    Future<?> resolveAsync(ITenantResolveContext context);
}
