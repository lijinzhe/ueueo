package com.ueueo.multitenancy;

import com.ueueo.users.ICurrentUser;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-14 17:31
 */
public class CurrentUserTenantResolveContributor extends TenantResolveContributorBase {

    public static final String ContributorName = "CurrentUser";

    @Override
    public String getName() {
        return ContributorName;
    }

    @Override
    public void resolve(ITenantResolveContext context) {
        //TODO 获取当前登录用户
        ICurrentUser currentUser = null;
        if (currentUser != null && currentUser.getIsAuthenticated()) {
            context.setHandled(true);
            context.setTenantIdOrName(currentUser.getTenantId().toString());
        }
    }
}
