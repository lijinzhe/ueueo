using System.Net.Http;
using System.Threading.Tasks;
using JetBrains.Annotations;

namespace Volo.Abp.IdentityModel;

//TODO: Re-consider this interface!
public interface IIdentityModelAuthenticationService
{
    Boolean>  TryAuthenticateAsync(
        @NonNull HttpClient client,
        String identityClientName = null);

    String> GetAccessTokenAsync(
        IdentityClientConfiguration configuration);
}
