using System;
using System.Collections.Generic;
using JetBrains.Annotations;

namespace Volo.Abp.GlobalFeatures;

public class GlobalFeatureManager
{
    public static GlobalFeatureManager Instance { get; protected set; } = new GlobalFeatureManager();

    /**
     * A common dictionary to store arbitrary configurations.
    */
    [NotNull]
    public Dictionary<Object, Object> Configuration;//  { get; }

    public GlobalModuleFeaturesDictionary Modules;//  { get; }

    protected HashSet<String> EnabledFeatures;//  { get; }

    protected internal GlobalFeatureManager()
    {
        EnabledFeatures = new HashSet<String>();
        Configuration = new Dictionary<Object, Object>();
        Modules = new GlobalModuleFeaturesDictionary(this);
    }

    public   boolean IsEnabled<TFeature>()
    {
        return IsEnabled(typeof(TFeature));
    }

    public   boolean IsEnabled(@Nonnull Type featureType)
    {
        return IsEnabled(GlobalFeatureNameAttribute.GetName(featureType));
    }

    public   boolean IsEnabled(String featureName)
    {
        return EnabledFeatures.Contains(featureName);
    }

    public   void Enable<TFeature>()
    {
        Enable(typeof(TFeature));
    }

    public   void Enable(@Nonnull Type featureType)
    {
        Enable(GlobalFeatureNameAttribute.GetName(featureType));
    }

    public   void Enable(String featureName)
    {
        EnabledFeatures.AddIfNotContains(featureName);
    }

    public   void Disable<TFeature>()
    {
        Disable(typeof(TFeature));
    }

    public   void Disable(@Nonnull Type featureType)
    {
        Disable(GlobalFeatureNameAttribute.GetName(featureType));
    }

    public   void Disable(String featureName)
    {
        EnabledFeatures.Remove(featureName);
    }

    public   IEnumerable<String> GetEnabledFeatureNames()
    {
        return EnabledFeatures;
    }
}
