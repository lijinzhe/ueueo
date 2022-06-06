using System.Threading.Tasks;
using Volo.Abp.DependencyInjection;
using Volo.Abp.Http.Client.Authentication;
using Volo.Abp.IdentityModel;

namespace Volo.Abp.Http.Client.IdentityModel;

[Dependency(ReplaceServices = true)]
public class IdentityModelRemoteServiceHttpClientAuthenticator : IRemoteServiceHttpClientAuthenticator, ITransientDependency
{
    protected IIdentityModelAuthenticationService IdentityModelAuthenticationService;//  { get; }

    public IdentityModelRemoteServiceHttpClientAuthenticator(
        IIdentityModelAuthenticationService identityModelAuthenticationService)
    {
        IdentityModelAuthenticationService = identityModelAuthenticationService;
    }

    public   void Authenticate(RemoteServiceHttpClientAuthenticateContext context)
    {
        IdentityModelAuthenticationService.TryAuthenticateAsync(
            context.Client,
            context.RemoteService.GetIdentityClient() ?? context.RemoteServiceName
        );
    }
}
