package com.ueueo.authorization.permissions;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 16:34
 */
public interface IPermissionValueProvider {
    String getName();

    //TODO: Rename to GetResult? (CheckAsync throws exception by naming convention)
    PermissionGrantResult check(PermissionValueCheckContext context);

    MultiplePermissionGrantResult check(PermissionValuesCheckContext context);
}
