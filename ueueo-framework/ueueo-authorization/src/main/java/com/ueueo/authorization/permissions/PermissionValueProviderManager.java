package com.ueueo.authorization.permissions;

import org.springframework.beans.factory.BeanFactory;

import java.util.List;
import java.util.stream.Collectors;

public class PermissionValueProviderManager implements IPermissionValueProviderManager {
    private List<IPermissionValueProvider> permissionValueProviders;

    protected AbpPermissionOptions Options;

    public PermissionValueProviderManager(
            BeanFactory beanFactory,
            AbpPermissionOptions options) {
        Options = options;
        permissionValueProviders = options.getValueProviders()
                .stream().map(beanFactory::getBean)
                .collect(Collectors.toList());

    }

    @Override
    public List<IPermissionValueProvider> getValueProviders() {
        return permissionValueProviders;
    }
}
