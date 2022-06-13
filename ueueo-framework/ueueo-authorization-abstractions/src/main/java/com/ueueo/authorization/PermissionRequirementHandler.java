package com.ueueo.authorization;

import com.ueueo.authorization.microsoft.AuthorizationHandler;
import com.ueueo.authorization.microsoft.AuthorizationHandlerContext;
import com.ueueo.authorization.permissions.IPermissionChecker;

public class PermissionRequirementHandler extends AuthorizationHandler<PermissionRequirement> {

    private final IPermissionChecker permissionChecker;

    public PermissionRequirementHandler(IPermissionChecker permissionChecker) {
        this.permissionChecker = permissionChecker;
    }

    @Override
    protected void handleRequirement(AuthorizationHandlerContext context, PermissionRequirement requirement) {
        if (permissionChecker.isGranted(context.getUser(), requirement.getPermissionName())) {
            context.succeed(requirement);
        }
    }

}
