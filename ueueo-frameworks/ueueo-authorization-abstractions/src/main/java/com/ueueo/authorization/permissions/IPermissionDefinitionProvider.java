package com.ueueo.authorization.permissions;

/**
 * @author Lee
 * @date 2021-08-26 16:27
 */
public interface IPermissionDefinitionProvider {
    void preDefine(IPermissionDefinitionContext context);

    void define(IPermissionDefinitionContext context);

    void postDefine(IPermissionDefinitionContext context);
}
