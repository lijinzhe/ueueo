package com.ueueo.authorization.permissions;

import com.ueueo.simplestatechecking.IHasSimpleStateCheckers;
import com.ueueo.users.ICurrentUser;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.List;

public class PermissionSimpleStateCheckerExtensions {

    public static <TState extends IHasSimpleStateCheckers<TState>> TState requireAuthenticated(@NonNull TState state, ICurrentUser currentUser)
    {
        state.getStateCheckers().add(new RequireAuthenticatedSimpleStateChecker<>(currentUser));
        return state;
    }

    public static <TState extends IHasSimpleStateCheckers<TState>> TState requirePermissions(
            @NonNull TState state,
            List<String> permissions,
            IPermissionChecker permissionChecker) {
        requirePermissions(state, true, true, permissions,permissionChecker);
        return state;
    }

    public static <TState extends IHasSimpleStateCheckers<TState>> TState requirePermissions(
            @NonNull TState state,
            boolean requiresAll,
            List<String> permissions,
            IPermissionChecker permissionChecker) {
        requirePermissions(state, requiresAll, true, permissions,permissionChecker);
        return state;
    }

    public static <TState extends IHasSimpleStateCheckers<TState>> TState requirePermissions(
            @NonNull TState state,
            boolean requiresAll,
            boolean batchCheck,
            List<String> permissions,
            IPermissionChecker permissionChecker) {
        Assert.notNull(state, "state must not null!");
        Assert.notEmpty(permissions, "permissions must not empty!");

        if (batchCheck) {
            //TODO 使用线程缓存，不知道为什么
//            RequirePermissionsSimpleBatchStateChecker<TState>.Current.AddCheckModels (new RequirePermissionsSimpleBatchStateCheckerModel<TState>(state, permissions, requiresAll));
//            state.getStateCheckers().add( RequirePermissionsSimpleBatchStateChecker<TState>.Current);
            RequirePermissionsSimpleBatchStateChecker<TState> batchStateChecker = new RequirePermissionsSimpleBatchStateChecker<>(permissionChecker);
            batchStateChecker.addCheckModels(new RequirePermissionsSimpleBatchStateCheckerModel<>(state, permissions, requiresAll));
            state.getStateCheckers().add(batchStateChecker);
        } else {
            state.getStateCheckers().add(new RequirePermissionsSimpleStateChecker<>(permissionChecker,new RequirePermissionsSimpleBatchStateCheckerModel<TState>(state, permissions, requiresAll)));
        }

        return state;
    }
}
