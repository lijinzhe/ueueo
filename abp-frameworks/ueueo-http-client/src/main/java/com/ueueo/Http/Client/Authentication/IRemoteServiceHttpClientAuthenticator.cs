using System.Threading.Tasks;

namespace Volo.Abp.Http.Client.Authentication;

public interface IRemoteServiceHttpClientAuthenticator
{
    void Authenticate(RemoteServiceHttpClientAuthenticateContext context); //TODO: Rename to AuthenticateAsync
}
