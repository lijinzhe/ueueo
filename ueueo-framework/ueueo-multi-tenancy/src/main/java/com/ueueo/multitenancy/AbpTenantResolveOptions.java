package com.ueueo.multitenancy;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-14 17:28
 */
@Getter
public class AbpTenantResolveOptions {
    private final List<ITenantResolveContributor> tenantResolvers;

    public AbpTenantResolveOptions() {
        this.tenantResolvers = new ArrayList<>();
        this.tenantResolvers.add(new CurrentUserTenantResolveContributor());
    }
}
