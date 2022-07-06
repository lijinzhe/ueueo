package com.ueueo.multitenancy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lee
 * @date 2022-05-14 17:33
 */
public class TenantResolver implements ITenantResolver {

    private final List<ITenantResolveContributor> contributors;

    public TenantResolver() {
        this.contributors = new ArrayList<>();
        this.contributors.add(new CurrentUserTenantResolveContributor());
    }

    @Override
    public TenantResolveResult resolveTenantIdOrName() {
        TenantResolveResult result = new TenantResolveResult();
        TenantResolveContext context = new TenantResolveContext();
        for (ITenantResolveContributor tenantResolver : contributors) {
            tenantResolver.resolve(context);
            result.getAppliedResolvers().add(tenantResolver.getName());
            if (context.hasResolvedTenantOrHost()) {
                result.setTenantIdOrName(context.getTenantIdOrName());
            }
        }
        return result;
    }

    public List<ITenantResolveContributor> getContributors() {
        return contributors;
    }
}
