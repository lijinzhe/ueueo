using System.Collections.Generic;

namespace Volo.Abp.IdentityModel;

public class IdentityClientConfigurationDictionary : Dictionary<String, IdentityClientConfiguration>
{
    public const String DefaultName = "Default";

    public IdentityClientConfiguration Default {
        get => this.GetOrDefault(DefaultName);
        set => this[DefaultName] = value;
    }
}
