package com.ueueo.multitenancy;

import com.ueueo.multitenancy.threading.MultiTenancyAsyncTaskExecutor;

import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @author Lee
 * @date 2022-05-14 17:28
 */
public class ActionTenantResolveContributor extends TenantResolveContributorBase {
    public static final String ContributorName = "Action";

    private Consumer<ITenantResolveContext> resolveAction;

    public ActionTenantResolveContributor(Consumer<ITenantResolveContext> resolveAction) {
        this.resolveAction = resolveAction;
    }

    @Override
    public String getName() {
        return ContributorName;
    }

    @Override
    public Future<?> resolveAsync(ITenantResolveContext context) {
        return MultiTenancyAsyncTaskExecutor.INSTANCE.submit(() -> resolveAction.accept(context));
    }
}
