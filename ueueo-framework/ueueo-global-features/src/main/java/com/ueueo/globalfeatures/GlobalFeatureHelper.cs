using System;
using Volo.Abp.Reflection;

namespace Volo.Abp.GlobalFeatures;

public static class GlobalFeatureHelper
{
    public static boolean IsGlobalFeatureEnabled(Type type, out RequiresGlobalFeatureAttribute attribute)
    {
        attribute = ReflectionHelper.GetSingleAttributeOrDefault<RequiresGlobalFeatureAttribute>(type);
        return attribute == null || GlobalFeatureManager.Instance.IsEnabled(attribute.GetFeatureName());
    }
}
