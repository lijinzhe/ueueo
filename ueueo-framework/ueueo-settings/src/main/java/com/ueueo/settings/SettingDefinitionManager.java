package com.ueueo.settings;

import com.ueueo.AbpException;
import lombok.Getter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2021-08-18 21:04
 */
@Getter
public class SettingDefinitionManager implements ISettingDefinitionManager {

    protected Map<String, SettingDefinition> settingDefinitions;

    protected AbpSettingOptions options;

    private BeanFactory beanFactory;

    public SettingDefinitionManager(AbpSettingOptions options, BeanFactory beanFactory) {
        this.options = options;
        this.beanFactory = beanFactory;
        this.settingDefinitions = new HashMap<>(createSettingDefinitions());
    }

    @Override
    public SettingDefinition get(@NonNull String name) {
        Objects.requireNonNull(name, "name must not null.");
        SettingDefinition setting = getOrNull(name);
        if (setting == null) {
            throw new AbpException("Undefined setting: " + name);
        }
        return setting;
    }

    @Override
    public List<SettingDefinition> getAll() {
        return new ArrayList<>(settingDefinitions.values());
    }

    @Override
    public SettingDefinition getOrNull(String name) {
        return settingDefinitions.get(name);
    }

    protected Map<String, SettingDefinition> createSettingDefinitions() {
        Map<String, SettingDefinition> settings = new HashMap<>();
        List<ISettingDefinitionProvider> providers = options.getDefinitionProviders().stream()
                .map(cls -> beanFactory.getBean(cls))
                .collect(Collectors.toList());
        for (ISettingDefinitionProvider provider : providers) {
            provider.define(new SettingDefinitionContext(settings));
        }
        return settings;
    }
}
