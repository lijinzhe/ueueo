package com.ueueo.multitenancy;

import org.springframework.beans.factory.Aware;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lee
 * @date 2022-05-14 17:28
 */
public class AbpTenantResolveOptions {

    private final List<ITenantResolveContributor> tenantResolvers;

    public AbpTenantResolveOptions() {
        this.tenantResolvers = new ArrayList<>();
        this.tenantResolvers.add(new CurrentUserTenantResolveContributor());
    }

    public List<ITenantResolveContributor> getTenantResolvers() {
        return tenantResolvers;
    }
}
