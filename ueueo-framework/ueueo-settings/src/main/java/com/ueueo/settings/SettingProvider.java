package com.ueueo.settings;

import com.ueueo.SystemException;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
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
    public String getOrNull(String name) {
        SettingDefinition setting = settingDefinitionManager.get(name);
        List<ISettingValueProvider> providers = settingValueProviderManager.getProviders();

        if (!setting.getProviders().isEmpty()) {
            providers = providers.stream().filter(p -> setting.getProviders().contains(p.getName())).collect(Collectors.toList());
        }

        //TODO: How to implement setting.IsInherited?
        String value = getOrNullValueFromProviders(providers, setting);
        if (value != null && setting.isEncrypted()) {
            value = settingEncryptionService.decrypt(setting, value);
        }
        return value;
    }

    @Override
    public List<SettingValue> getAll(List<String> names) {
        Set<String> nameSet = new HashSet<>(names);
        Map<String, SettingValue> result = new HashMap<>();
        List<SettingDefinition> settingDefinitions = settingDefinitionManager.getAll()
                .stream().filter(def -> nameSet.contains(def.getName()))
                .collect(Collectors.toList());

        for (SettingDefinition definition : settingDefinitions) {
            result.put(definition.getName(), new SettingValue(definition.getName(), null));
        }

        for (ISettingValueProvider provider : settingValueProviderManager.getProviders()) {
            List<SettingValue> settingValues = provider.getAll(settingDefinitions.stream()
                    .filter(def -> def.getProviders().isEmpty() || def.getProviders().contains(provider.getName()))
                    .collect(Collectors.toList())
            );
            List<SettingValue> notNullValues = settingValues.stream().filter(val -> val.getValue() != null).collect(Collectors.toList());
            for (SettingValue settingValue : notNullValues) {
                SettingDefinition settingDefinition = settingDefinitions.stream().filter(def -> def.getName().equals(settingValue.getName())).findFirst().orElse(null);
                if (settingDefinition != null) {
                    if (settingDefinition.isEncrypted()) {
                        settingValue.setValue(settingEncryptionService.decrypt(settingDefinition, settingValue.getValue()));
                    }

                    if (result.containsKey(settingValue.getName()) && result.get(settingValue.getName()) == null) {
                        result.put(settingValue.getName(), settingValue);
                    }
                }
            }
            settingDefinitions.removeIf(def -> notNullValues.stream().anyMatch(val -> val.getName().equals(def.getName())));
        }
        return new ArrayList<>(result.values());
    }

    @Override
    public List<SettingValue> getAll() throws SystemException {
        List<SettingValue> settingValues = new ArrayList<>();
        List<SettingDefinition> settingDefinitions = settingDefinitionManager.getAll();
        for (SettingDefinition setting : settingDefinitions) {
            settingValues.add(new SettingValue(setting.getName(), getOrNull(setting.getName())));
        }
        return settingValues;
    }

    protected String getOrNullValueFromProviders(List<ISettingValueProvider> providers,
                                                 SettingDefinition setting) {
        for (ISettingValueProvider provider : providers) {
            String value = provider.getOrNull(setting);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    public boolean isTrue(String name) {
        Assert.notNull(name, "name must not null.");
        return name.equalsIgnoreCase(getOrNull(name));
    }

    public String get(String name, String defaultValue) {
        Assert.notNull(name, "name must not null.");
        String value = getOrNull(name);
        return value != null ? value : defaultValue;
    }

}
