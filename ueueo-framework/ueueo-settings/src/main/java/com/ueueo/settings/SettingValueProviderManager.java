package com.ueueo.settings;

import org.springframework.beans.factory.BeanFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2021-08-19 21:38
 */
public class SettingValueProviderManager implements ISettingValueProviderManager {

    private List<ISettingValueProvider> providers;
    protected AbpSettingOptions options;

    @Override
    public List<ISettingValueProvider> getProviders() {
        return providers;
    }

    public SettingValueProviderManager(BeanFactory beanFactory, AbpSettingOptions options) {
        this.options = options;
        this.providers = options.getValueProviders().stream().map(beanFactory::getBean).collect(Collectors.toList());
    }
}
