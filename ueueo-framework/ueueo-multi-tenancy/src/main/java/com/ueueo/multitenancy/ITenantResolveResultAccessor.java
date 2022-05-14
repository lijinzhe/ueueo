package com.ueueo.multitenancy;

import org.springframework.lang.Nullable;

/**
 * @author Lee
 * @date 2022-05-13 21:35
 */
public interface ITenantResolveResultAccessor {

    @Nullable
    TenantResolveResult getResult();

    void setResult(@Nullable TenantResolveResult result);
}
