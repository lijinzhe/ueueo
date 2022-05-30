using System;
using System.Linq;
using Volo.Abp.MultiTenancy;

namespace Volo.Abp.IdentityModel;

[Serializable]
public class IdentityModelTokenCacheItem
{
    public String AccessToken;// { get; set; }

    public IdentityModelTokenCacheItem()
    {

    }

    public IdentityModelTokenCacheItem(String accessToken)
    {
        AccessToken = accessToken;
    }

    public static String CalculateCacheKey(IdentityClientConfiguration configuration)
    {
        return String.Join(",", configuration.Select(x => x.Key + ":" + x.Value)).ToMd5();
    }
}
