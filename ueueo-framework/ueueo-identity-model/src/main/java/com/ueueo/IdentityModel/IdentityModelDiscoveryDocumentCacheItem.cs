using System;
using Volo.Abp.MultiTenancy;

namespace Volo.Abp.IdentityModel;

[Serializable]
[IgnoreMultiTenancy]
public class IdentityModelDiscoveryDocumentCacheItem
{
    public String TokenEndpoint;// { get; set; }

    public String DeviceAuthorizationEndpoint;// { get; set; }

    public IdentityModelDiscoveryDocumentCacheItem()
    {

    }

    public IdentityModelDiscoveryDocumentCacheItem(String tokenEndpoint, String deviceAuthorizationEndpoint)
    {
        TokenEndpoint = tokenEndpoint;
        DeviceAuthorizationEndpoint = deviceAuthorizationEndpoint;
    }

    public static String CalculateCacheKey(IdentityClientConfiguration configuration)
    {
        return configuration.Authority.ToLower().ToMd5();
    }
}
