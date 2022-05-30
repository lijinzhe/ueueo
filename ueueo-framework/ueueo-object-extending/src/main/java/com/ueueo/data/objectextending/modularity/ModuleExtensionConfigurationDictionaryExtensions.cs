using System;
using System.Collections.Generic;
using JetBrains.Annotations;

namespace Volo.Abp.ObjectExtending.Modularity;

public static class ModuleExtensionConfigurationDictionaryExtensions
{
    public static ModuleExtensionConfigurationDictionary ConfigureModule<T>(
        @Nonnull this ModuleExtensionConfigurationDictionary configurationDictionary,
        @Nonnull String moduleName,
        @Nonnull Action<T> configureAction)
        where T : ModuleExtensionConfiguration, new()
    {
        Objects.requireNonNull(moduleName, nameof(moduleName));
        Objects.requireNonNull(configureAction, nameof(configureAction));

        configureAction(
            (T)configurationDictionary.GetOrAdd(
                moduleName,
                () => new T()
            )
        );

        return configurationDictionary;
    }
}
