package com.ueueo.authorization.permissions;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;

import java.util.List;

public class RequirePermissionsSimpleBatchStateCheckerModel<TState> {

    public TState state;

    public List<String> permissions;

    public boolean requiresAll = true;

    public RequirePermissionsSimpleBatchStateCheckerModel(TState state, List<String> permissions, Boolean requiresAll) {
        Assert.notNull(state, "State must not null!");
        Assert.isTrue(CollectionUtils.isNotEmpty(permissions), "Permissions must not empty!");

        this.state = state;
        this.permissions = permissions;
        if (requiresAll != null) {
            this.requiresAll = requiresAll;
        }
    }

    public TState getState() {
        return state;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public boolean isRequiresAll() {
        return requiresAll;
    }
}
