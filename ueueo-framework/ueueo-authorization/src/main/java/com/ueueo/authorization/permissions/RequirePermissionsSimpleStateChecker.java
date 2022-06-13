package com.ueueo.authorization.permissions;

import com.ueueo.simplestatechecking.IHasSimpleStateCheckers;
import com.ueueo.simplestatechecking.ISimpleStateChecker;
import com.ueueo.simplestatechecking.SimpleStateCheckerContext;

public class RequirePermissionsSimpleStateChecker<TState extends IHasSimpleStateCheckers<TState>> implements ISimpleStateChecker<TState> {

    private IPermissionChecker permissionChecker;

    private RequirePermissionsSimpleBatchStateCheckerModel<TState> model;

    public RequirePermissionsSimpleStateChecker(IPermissionChecker permissionChecker,
                                                RequirePermissionsSimpleBatchStateCheckerModel<TState> model) {
        this.permissionChecker = permissionChecker;
        this.model = model;
    }

    @Override
    public boolean isEnabled(SimpleStateCheckerContext<TState> context) {

        if (model.getPermissions().size() == 1) {
            return permissionChecker.isGranted(model.getPermissions().get(0));
        }

        MultiplePermissionGrantResult grantResult = permissionChecker.isGranted(model.getPermissions());

        return model.requiresAll
                ? grantResult.allGranted()
                : grantResult.getResult().entrySet().stream()
                .anyMatch(x -> model.getPermissions().contains(x.getKey()) && x.getValue().equals(PermissionGrantResult.Granted));
    }

}
