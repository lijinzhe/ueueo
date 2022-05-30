package com.ueueo.authorization.permissions;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lee
 * @date 2022-05-29 14:03
 */
@Data
public class AbpPermissionOptions {
    private List<Class<? extends IPermissionDefinitionProvider>> definitionProviders;
    private List<Class<? extends IPermissionValueProvider>> valueProviders;

    public AbpPermissionOptions() {
        this.definitionProviders = new ArrayList<>();
        this.valueProviders = new ArrayList<>();
    }
}
