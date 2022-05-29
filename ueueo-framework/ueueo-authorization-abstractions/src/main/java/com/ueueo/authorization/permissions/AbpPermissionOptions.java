package com.ueueo.authorization.permissions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lee
 * @date 2022-05-29 14:03
 */
public class AbpPermissionOptions {
    public List<Class<? extends IPermissionDefinitionProvider>> definitionProviders;
    public List<Class<? extends IPermissionValueProvider>> valueProviders;

    public AbpPermissionOptions() {
        this.definitionProviders = new ArrayList<>();
        this.valueProviders = new ArrayList<>();
    }
}
