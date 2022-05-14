package com.ueueo.settings;

import com.ueueo.core.AbpException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-18 21:37
 */
public class SettingProvider implements ISettingProvider {

    protected ISettingDefinitionManager settingDefinitionManager;
    protected ISettingEncryptionService settingEncryptionService;
    protected ISettingValueProviderManager settingValueProviderManager;

    public SettingProvider(ISettingDefinitionManager settingDefinitionManager,
                           ISettingEncryptionService settingEncryptionService,
                           ISettingValueProviderManager settingValueProviderManager) {
        this.settingDefinitionManager = settingDefinitionManager;
        this.settingEncryptionService = settingEncryptionService;
        this.settingValueProviderManager = settingValueProviderManager;
    }

    @Override
    public String getOrNull(String name)  throws AbpException {
        SettingDefinition setting = settingDefinitionManager.get(name);
        List<ISettingValueProvider> providers = settingValueProviderManager.providers();

        if (!setting.getProviders().isEmpty()) {
            providers = providers.stream().filter(p -> setting.getProviders().contains(p.name())).collect(Collectors.toList());
        }

        //TODO: How to implement setting.IsInherited?
        String value = getOrNullValueFromProvidersAsync(providers, setting);
        if (value != null && setting.getIsEncrypted()) {
            value = settingEncryptionService.decrypt(setting, value);
        }
        return value;
    }

    @Override
    public Collection<SettingValue> getAll(Collection<String> names) {
        Set<String> nameSet = new HashSet<>(names);
        Map<String, SettingValue> result = new HashMap<>();
        Collection<SettingDefinition> settingDefinitions = settingDefinitionManager.getAll()
                .stream().filter(def -> nameSet.contains(def.getName()))
                .collect(Collectors.toList());

        for (SettingDefinition definition : settingDefinitions) {
            result.put(definition.getName(), new SettingValue(definition.getName(), null));
        }

        for (ISettingValueProvider provider : settingValueProviderManager.providers()) {
            Collection<SettingValue> settingValues = provider.getAll(settingDefinitions.stream()
                    .filter(def -> def.getProviders().isEmpty() || def.getProviders().contains(provider.name()))
                    .collect(Collectors.toList())
            );
            Collection<SettingValue> NonNullValues = settingValues.stream().filter(val -> val.getValue() != null).collect(Collectors.toList());
            for (SettingValue settingValue : NonNullValues) {
                SettingDefinition settingDefinition = settingDefinitions.stream().filter(def -> def.getName().equals(settingValue.getName())).findFirst().orElse(null);
                if (settingDefinition != null) {
                    if (settingDefinition.getIsEncrypted()) {
                        settingValue.setValue(settingEncryptionService.decrypt(settingDefinition, settingValue.getValue()));
                    }

                    if (result.containsKey(settingValue.getName()) && result.get(settingValue.getName()) == null) {
                        result.put(settingValue.getName(), settingValue);
                    }
                }
            }
            settingDefinitions.removeIf(def -> NonNullValues.stream().anyMatch(val -> val.getName().equals(def.getName())));
        }
        return result.values();
    }

    @Override
    public Collection<SettingValue> getAll()  throws AbpException{
        List<SettingValue> settingValues = new ArrayList<>();
        Collection<SettingDefinition> settingDefinitions = settingDefinitionManager.getAll();
        for (SettingDefinition setting : settingDefinitions) {
            settingValues.add(new SettingValue(setting.getName(), getOrNull(setting.getName())));
        }
        return settingValues;
    }

    protected String getOrNullValueFromProvidersAsync(Collection<ISettingValueProvider> providers,
                                                      SettingDefinition setting) {
        for (ISettingValueProvider provider : providers) {
            String value = provider.getOrNull(setting);
            if (value != null) {
                return value;
            }
        }
        return null;
    }
}
