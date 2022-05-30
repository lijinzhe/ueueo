using System.Net.Http;
using System.Threading.Tasks;
using JetBrains.Annotations;

namespace Volo.Abp.IdentityModel;

//TODO: Re-consider this interface!
public interface IIdentityModelAuthenticationService
{
    Task<bool> TryAuthenticateAsync(
        @Nonnull HttpClient client,
        String identityClientName = null);

    Task<String> GetAccessTokenAsync(
        IdentityClientConfiguration configuration);
}
