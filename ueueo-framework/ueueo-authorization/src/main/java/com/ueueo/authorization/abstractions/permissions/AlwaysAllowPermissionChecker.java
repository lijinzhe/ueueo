package com.ueueo.authorization.abstractions.permissions;

import java.util.List;

/**
 * TODO ABP代码
 * Always allows for any permission.
 *
 * Use IServiceCollection.AddAlwaysAllowAuthorization() to replace
 * PermissionChecker with this class. This is useful for tests.
 *
 * @author Lee
 * @date 2021-08-26 14:57
 */
public class AlwaysAllowPermissionChecker implements IPermissionChecker {
    @Override
    public Boolean isGranted(String name) {
        return true;
    }

    @Override
    public MultiplePermissionGrantResult isGranted(List<String> names) {
        return new MultiplePermissionGrantResult(names, PermissionGrantResult.Granted);
    }
}
