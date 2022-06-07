using System.Collections.Generic;
using JetBrains.Annotations;

namespace Volo.Abp.ObjectExtending.Modularity;

public class ModuleExtensionConfiguration
{
    [NotNull]
    public EntityExtensionConfigurationDictionary Entities;//  { get; }

    [NotNull]
    public Dictionary<String, Object> Configuration;//  { get; }

    public ModuleExtensionConfiguration()
    {
        Entities = new EntityExtensionConfigurationDictionary();
        Configuration = new Dictionary<String, Object>();
    }
}
