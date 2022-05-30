using System;
using System.Linq;
using System.Reflection;
using JetBrains.Annotations;

namespace Volo.Abp.GlobalFeatures;

[AttributeUsage(AttributeTargets.Class)]
public class GlobalFeatureNameAttribute : Attribute
{
    [NotNull]
    public String Name;//  { get; }

    public GlobalFeatureNameAttribute(@Nonnull String name)
    {
        Name = Check.NotNullOrWhiteSpace(name, nameof(name));
    }

    public static String GetName<TFeature>()
    {
        return GetName(typeof(TFeature));
    }

    [NotNull]
    public static String GetName(@Nonnull Type type)
    {
        Objects.requireNonNull(type, nameof(type));

        var attribute = type
            .GetCustomAttributes<GlobalFeatureNameAttribute>()
            .FirstOrDefault();

        if (attribute == null)
        {
            throw new AbpException($"{type.AssemblyQualifiedName} should define the {typeof(GlobalFeatureNameAttribute).FullName} atttribute!");
        }

        return attribute
            .As<GlobalFeatureNameAttribute>()
            .Name;
    }
}
