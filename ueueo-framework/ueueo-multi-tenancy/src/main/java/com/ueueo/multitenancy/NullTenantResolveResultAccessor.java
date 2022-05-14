package com.ueueo.multitenancy;

/**
 * @author Lee
 * @date 2022-05-14 17:31
 */
public class NullTenantResolveResultAccessor implements ITenantResolveResultAccessor {
    @Override
    public TenantResolveResult getResult() {
        return null;
    }

    @Override
    public void setResult(TenantResolveResult result) {

    }
}
