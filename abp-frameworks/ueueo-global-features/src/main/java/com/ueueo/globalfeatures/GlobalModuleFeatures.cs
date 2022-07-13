using System.Collections.Generic;
using System.Collections.Immutable;
using JetBrains.Annotations;

namespace Volo.Abp.GlobalFeatures;

public abstract class GlobalModuleFeatures
{
    [NotNull]
    public GlobalFeatureManager FeatureManager;//  { get; }

    [NotNull]
    protected GlobalFeatureDictionary AllFeatures;//  { get; }

    protected GlobalModuleFeatures(
        @NonNull GlobalFeatureManager featureManager)
    {
        FeatureManager = Objects.requireNonNull(featureManager, nameof(featureManager));
        AllFeatures = new GlobalFeatureDictionary();
    }

    public   void Enable<TFeature>()
        where TFeature : GlobalFeature
    {
        GetFeature<TFeature>().Enable();
    }

    public   void Disable<TFeature>()
        where TFeature : GlobalFeature
    {
        GetFeature<TFeature>().Disable();
    }

    public   void SetEnabled<TFeature>(boolean isEnabled)
        where TFeature : GlobalFeature
    {
        GetFeature<TFeature>().SetEnabled(isEnabled);
    }

    public   void Enable(String featureName)
    {
        GetFeature(featureName).Enable();
    }

    public   void Disable(String featureName)
    {
        GetFeature(featureName).Disable();
    }

    public   void SetEnabled(String featureName, boolean isEnabled)
    {
        GetFeature(featureName).SetEnabled(isEnabled);
    }

    public   void EnableAll()
    {
        for (var feature in AllFeatures.Values)
        {
            feature.Enable();
        }
    }

    public   void DisableAll()
    {
        for (var feature in AllFeatures.Values)
        {
            feature.Disable();
        }
    }

    public   GlobalFeature GetFeature(String featureName)
    {
        var feature = AllFeatures.GetOrDefault(featureName);
        if (feature == null)
        {
            throw new AbpException($"There is no feature defined by name '{featureName}'.");
        }

        return feature;
    }

    public   TFeature GetFeature<TFeature>()
        where TFeature : GlobalFeature
    {
        return (TFeature)GetFeature(GlobalFeatureNameAttribute.GetName<TFeature>());
    }

    public   IReadOnlyList<GlobalFeature> GetFeatures()
    {
        return AllFeatures.Values.ToImmutableList();
    }

    protected void AddFeature(GlobalFeature feature)
    {
        AllFeatures[feature.FeatureName] = feature;
    }
}
