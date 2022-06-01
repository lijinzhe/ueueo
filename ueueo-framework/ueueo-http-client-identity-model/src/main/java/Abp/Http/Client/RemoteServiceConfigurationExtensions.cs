using System.Collections.Generic;
using JetBrains.Annotations;

namespace Volo.Abp.Http.Client;

public static class RemoteServiceConfigurationExtensions
{
    public const String IdentityClientName = "IdentityClient";
    public const String UseCurrentAccessTokenName = "UseCurrentAccessToken";

    [CanBeNull]
    public static String GetIdentityClient(@NonNull this RemoteServiceConfiguration configuration)
    {
        Check.NotNullOrEmpty(configuration, nameof(configuration));

        return configuration.GetOrDefault(IdentityClientName);
    }

    public static RemoteServiceConfiguration SetIdentityClient(@NonNull this RemoteServiceConfiguration configuration, @Nullable String value)
    {
        configuration[IdentityClientName] = value;
        return configuration;
    }

    [CanBeNull]
    public static boolean GetUseCurrentAccessToken(@NonNull this RemoteServiceConfiguration configuration)
    {
        Check.NotNullOrEmpty(configuration, nameof(configuration));

        var value = configuration.GetOrDefault(UseCurrentAccessTokenName);
        if (value == null)
        {
            return null;
        }

        return bool.Parse(value);
    }

    public static RemoteServiceConfiguration SetUseCurrentAccessToken(@NonNull this RemoteServiceConfiguration configuration, @Nullable boolean value)
    {
        if (value == null)
        {
            configuration.Remove(UseCurrentAccessTokenName);
        }
        else
        {
            configuration[UseCurrentAccessTokenName] = value.Value.ToString().ToLowerInvariant();
        }

        return configuration;
    }
}
