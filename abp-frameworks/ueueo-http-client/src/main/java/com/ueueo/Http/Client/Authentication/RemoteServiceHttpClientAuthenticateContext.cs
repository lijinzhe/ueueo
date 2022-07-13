using System.Net.Http;

namespace Volo.Abp.Http.Client.Authentication;

public class RemoteServiceHttpClientAuthenticateContext
{
    public HttpClient Client;//  { get; }

    public HttpRequestMessage Request;//  { get; }

    public RemoteServiceConfiguration RemoteService;//  { get; }

    public String RemoteServiceName;//  { get; }

    public RemoteServiceHttpClientAuthenticateContext(
        HttpClient client,
        HttpRequestMessage request,
        RemoteServiceConfiguration remoteService,
        String remoteServiceName)
    {
        Client = client;
        Request = request;
        RemoteService = remoteService;
        RemoteServiceName = remoteServiceName;
    }
}
