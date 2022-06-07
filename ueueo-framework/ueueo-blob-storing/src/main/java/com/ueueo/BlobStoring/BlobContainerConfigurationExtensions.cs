using JetBrains.Annotations;

namespace Volo.Abp.BlobStoring;

public static class BlobContainerConfigurationExtensions
{
    public static T GetConfiguration<T>(
        @NonNull this BlobContainerConfiguration containerConfiguration,
        @NonNull String name)
    {
        return (T)containerConfiguration.GetConfiguration(name);
    }

    public static Object GetConfiguration(
        @NonNull this BlobContainerConfiguration containerConfiguration,
        @NonNull String name)
    {
        var value = containerConfiguration.GetConfigurationOrNull(name);
        if (value == null)
        {
            throw new AbpException($"Could not find the configuration value for '{name}'!");
        }

        return value;
    }
}
