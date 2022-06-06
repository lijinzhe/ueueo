using System;

namespace Volo.Abp.Http.Client.Proxying;

public class HttpClientProxyConfig
{
    public Type Type;//  { get; }

    public String RemoteServiceName;//  { get; }

    public HttpClientProxyConfig(Type type, String remoteServiceName)
    {
        Type = type;
        RemoteServiceName = remoteServiceName;
    }
}
