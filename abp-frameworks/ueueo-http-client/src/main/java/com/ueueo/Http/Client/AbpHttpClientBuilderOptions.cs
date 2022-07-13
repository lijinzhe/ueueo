using System;
using System.Collections.Generic;
using System.Net.Http;
using Microsoft.Extensions.DependencyInjection;

namespace Volo.Abp.Http.Client;

public class AbpHttpClientBuilderOptions
{
    public List<Action<String, IHttpClientBuilder>> ProxyClientBuildActions;//  { get; }

    internal HashSet<String> ConfiguredProxyClients;//  { get; }

    public List<Action<String, IServiceProvider, HttpClient>> ProxyClientActions;//  { get; }

    public AbpHttpClientBuilderOptions()
    {
        ProxyClientBuildActions = new List<Action<String, IHttpClientBuilder>>();
        ConfiguredProxyClients = new HashSet<String>();
        ProxyClientActions = new List<Action<String, IServiceProvider, HttpClient>>();
    }
}
