package com.ueueo.authorization.permissions;

/**
 * @author Lee
 * @date 2021-08-26 19:54
 */
public abstract class PermissionDefinitionProvider implements IPermissionDefinitionProvider {
    @Override
    public void preDefine(IPermissionDefinitionContext context) {

    }
    @Override
    public abstract void define(IPermissionDefinitionContext context);

    @Override
    public void postDefine(IPermissionDefinitionContext context) {

    }
}
