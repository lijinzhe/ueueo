package com.ueueo.multitenancy;

import com.ueueo.multitenancy.threading.MultiTenancyAsyncTaskExecutor;

import java.util.concurrent.Future;

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
    public Future<TenantResolveResult> resolveTenantIdOrNameAsync() {
        return MultiTenancyAsyncTaskExecutor.INSTANCE.submit(() -> {
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
        });
    }
}
