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
    private List<ITenantResolveContributor> tenantResolvers;

    public AbpTenantResolveOptions() {
        this.tenantResolvers = new ArrayList(){{
            add(new CurrentUserTenantResolveContributor());
        }};
    }
}
