package com.ueueo.multitenancy;

/**
 * @author Lee
 * @date 2022-05-14 17:33
 */
public class TenantResolver implements ITenantResolver {

    private AbpTenantResolveOptions options;

    public TenantResolver(AbpTenantResolveOptions options) {
        this.options = options;
    }

    @Override
    public TenantResolveResult resolveTenantIdOrName() {
        TenantResolveResult result = new TenantResolveResult();
        TenantResolveContext context = new TenantResolveContext();
        for (ITenantResolveContributor tenantResolver : options.getTenantResolvers()) {
            tenantResolver.resolveAsync(context);
            result.getAppliedResolvers().add(tenantResolver.getName());
            if (context.hasResolvedTenantOrHost()) {
                result.setTenantIdOrName(context.getTenantIdOrName());
            }
        }
        return result;
    }
}
