package com.ueueo.multitenancy;

import java.util.function.Consumer;

/**
 * @author Lee
 * @date 2022-05-14 17:28
 */
public class ActionTenantResolveContributor extends TenantResolveContributorBase {
    public static final String CONTRIBUTOR_NAME = "Action";

    private final Consumer<ITenantResolveContext> resolveAction;

    public ActionTenantResolveContributor(Consumer<ITenantResolveContext> resolveAction) {
        this.resolveAction = resolveAction;
    }

    @Override
    public String getName() {
        return CONTRIBUTOR_NAME;
    }

    @Override
    public void resolve(ITenantResolveContext context) {
        resolveAction.accept(context);
    }
}
