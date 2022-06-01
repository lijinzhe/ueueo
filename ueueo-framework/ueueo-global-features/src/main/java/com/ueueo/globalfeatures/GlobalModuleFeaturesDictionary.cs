using System.Collections.Generic;
using JetBrains.Annotations;

namespace Volo.Abp.GlobalFeatures;

public class GlobalModuleFeaturesDictionary : Dictionary<String, GlobalModuleFeatures>
{
    public GlobalFeatureManager FeatureManager;//  { get; }

    public GlobalModuleFeaturesDictionary(
        @NonNull GlobalFeatureManager featureManager)
    {
        FeatureManager = Objects.requireNonNull(featureManager, nameof(featureManager));
    }
}
