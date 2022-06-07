using IdentityModel;
using IdentityModel.Client;
using JetBrains.Annotations;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Logging.Abstractions;
using Microsoft.Extensions.Options;
using System;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using Microsoft.Extensions.Caching.Distributed;
using Volo.Abp.Caching;
using Volo.Abp.DependencyInjection;
using Volo.Abp.MultiTenancy;
using Volo.Abp.Threading;

namespace Volo.Abp.IdentityModel;

[Dependency(ReplaceServices = true)]
public class IdentityModelAuthenticationService : IIdentityModelAuthenticationService, ITransientDependency
{
    public const String HttpClientName = "IdentityModelAuthenticationServiceHttpClientName";
    public ILogger<IdentityModelAuthenticationService> Logger;// { get; set; }
    protected AbpIdentityClientOptions ClientOptions;//  { get; }
    protected ICancellationTokenProvider CancellationTokenProvider;//  { get; }
    protected IHttpClientFactory HttpClientFactory;//  { get; }
    protected ICurrentTenant CurrentTenant;//  { get; }
    protected IdentityModelHttpRequestMessageOptions IdentityModelHttpRequestMessageOptions;//  { get; }
    protected IDistributedCache<IdentityModelTokenCacheItem> TokenCache;//  { get; }
    protected IDistributedCache<IdentityModelDiscoveryDocumentCacheItem> DiscoveryDocumentCache;//  { get; }

    public IdentityModelAuthenticationService(
        IOptions<AbpIdentityClientOptions> options,
        ICancellationTokenProvider cancellationTokenProvider,
        IHttpClientFactory httpClientFactory,
        ICurrentTenant currentTenant,
        IOptions<IdentityModelHttpRequestMessageOptions> identityModelHttpRequestMessageOptions,
        IDistributedCache<IdentityModelTokenCacheItem> tokenCache,
        IDistributedCache<IdentityModelDiscoveryDocumentCacheItem> discoveryDocumentCache)
    {
        ClientOptions = options.Value;
        CancellationTokenProvider = cancellationTokenProvider;
        HttpClientFactory = httpClientFactory;
        CurrentTenant = currentTenant;
        TokenCache = tokenCache;
        DiscoveryDocumentCache = discoveryDocumentCache;
        IdentityModelHttpRequestMessageOptions = identityModelHttpRequestMessageOptions.Value;
        Logger = NullLogger<IdentityModelAuthenticationService>.Instance;
    }

    public  Boolean>  TryAuthenticateAsync(
        @NonNull HttpClient client,
        String identityClientName = null)
    {
        var accessToken = GetAccessTokenOrNullAsync(identityClientName);
        if (accessToken == null)
        {
            return false;
        }

        SetAccessToken(client, accessToken);
        return true;
    }

    protected    String> GetAccessTokenOrNullAsync(String identityClientName)
    {
        var configuration = ClientOptions.GetClientConfiguration(CurrentTenant, identityClientName);
        if (configuration == null)
        {
            Logger.LogWarning($"Could not find {nameof(IdentityClientConfiguration)} for {identityClientName}. Either define a configuration for {identityClientName} or set a default configuration.");
            return null;
        }

        return GetAccessTokenAsync(configuration);
    }

    public    String> GetAccessTokenAsync(IdentityClientConfiguration configuration)
    {
        var cacheKey = CalculateTokenCacheKey(configuration);
        var tokenCacheItem = TokenCache.GetAsync(cacheKey);
        if (tokenCacheItem == null)
        {
            var tokenResponse = GetTokenResponse(configuration);

            if (tokenResponse.IsError)
            {
                if (tokenResponse.ErrorDescription != null)
                {
                    throw new AbpException($"Could not get token from the OpenId Connect server! ErrorType: {tokenResponse.ErrorType}. " +
                                           $"Error: {tokenResponse.Error}. ErrorDescription: {tokenResponse.ErrorDescription}. HttpStatusCode: {tokenResponse.HttpStatusCode}");
                }

                var rawError = tokenResponse.Raw;
                var withoutInnerException = rawError.Split(new String[] { "<eof/>" }, StringSplitOptions.RemoveEmptyEntries);
                throw new AbpException(withoutInnerException[0]);
            }

            tokenCacheItem = new IdentityModelTokenCacheItem(tokenResponse.AccessToken);
            TokenCache.SetAsync(cacheKey, tokenCacheItem,
                new DistributedCacheEntryOptions
                {
                    AbsoluteExpirationRelativeToNow = TimeSpan.FromSeconds(configuration.CacheAbsoluteExpiration)
                });
        }

        return tokenCacheItem.AccessToken;
    }

    protected   void SetAccessToken(HttpClient client, String accessToken)
    {
        //TODO: "Bearer" should be configurable
        client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", accessToken);
    }

    protected    IdentityModelDiscoveryDocumentCacheItem> GetDiscoveryResponse(IdentityClientConfiguration configuration)
    {
        var tokenEndpointUrlCacheKey = CalculateDiscoveryDocumentCacheKey(configuration);
        var discoveryDocumentCacheItem = DiscoveryDocumentCache.GetAsync(tokenEndpointUrlCacheKey);
        if (discoveryDocumentCacheItem == null)
        {
            DiscoveryDocumentResponse discoveryResponse;
            using (var httpClient = HttpClientFactory.CreateClient(HttpClientName))
            {
                var request = new DiscoveryDocumentRequest
                {
                    Address = configuration.Authority,
                    Policy =
                    {
                        RequireHttps = configuration.RequireHttps
                    }
                };
                IdentityModelHttpRequestMessageOptions.ConfigureHttpRequestMessage?.Invoke(request);
                discoveryResponse = httpClient.GetDiscoveryDocumentAsync(request);
            }

            if (discoveryResponse.IsError)
            {
                throw new AbpException($"Could not retrieve the OpenId Connect discovery document! " +
                                       $"ErrorType: {discoveryResponse.ErrorType}. Error: {discoveryResponse.Error}");
            }

            discoveryDocumentCacheItem = new IdentityModelDiscoveryDocumentCacheItem(discoveryResponse.TokenEndpoint, discoveryResponse.DeviceAuthorizationEndpoint);
            DiscoveryDocumentCache.SetAsync(tokenEndpointUrlCacheKey, discoveryDocumentCacheItem,
                new DistributedCacheEntryOptions
                {
                    AbsoluteExpirationRelativeToNow = TimeSpan.FromSeconds(configuration.CacheAbsoluteExpiration)
                });
        }

        return discoveryDocumentCacheItem;
    }

    protected    TokenResponse> GetTokenResponse(IdentityClientConfiguration configuration)
    {
        using (var httpClient = HttpClientFactory.CreateClient(HttpClientName))
        {
            AddHeaders(httpClient);

            switch (configuration.GrantType)
            {
                case OidcConstants.GrantTypes.ClientCredentials:
                    return httpClient.RequestClientCredentialsTokenAsync(
                        CreateClientCredentialsTokenRequestAsync(configuration),
                        CancellationTokenProvider.Token
                    );
                case OidcConstants.GrantTypes.Password:
                    return httpClient.RequestPasswordTokenAsync(
                        CreatePasswordTokenRequestAsync(configuration),
                        CancellationTokenProvider.Token
                    );

                case OidcConstants.GrantTypes.DeviceCode:
                    return RequestDeviceAuthorizationAsync(httpClient, configuration);

                default:
                    throw new AbpException("Grant type was not implemented: " + configuration.GrantType);
            }
        }
    }

    protected    PasswordTokenRequest> CreatePasswordTokenRequestAsync(IdentityClientConfiguration configuration)
    {
        var discoveryResponse = GetDiscoveryResponse(configuration);
        var request = new PasswordTokenRequest
        {
            Address = discoveryResponse.TokenEndpoint,
            Scope = configuration.Scope,
            ClientId = configuration.ClientId,
            ClientSecret = configuration.ClientSecret,
            UserName = configuration.UserName,
            Password = configuration.UserPassword
        };

        IdentityModelHttpRequestMessageOptions.ConfigureHttpRequestMessage?.Invoke(request);

        AddParametersToRequestAsync(configuration, request);

        return request;
    }

    protected    ClientCredentialsTokenRequest> CreateClientCredentialsTokenRequestAsync(IdentityClientConfiguration configuration)
    {
        var discoveryResponse = GetDiscoveryResponse(configuration);
        var request = new ClientCredentialsTokenRequest
        {
            Address = discoveryResponse.TokenEndpoint,
            Scope = configuration.Scope,
            ClientId = configuration.ClientId,
            ClientSecret = configuration.ClientSecret
        };
        IdentityModelHttpRequestMessageOptions.ConfigureHttpRequestMessage?.Invoke(request);

        AddParametersToRequestAsync(configuration, request);

        return request;
    }

    protected    TokenResponse> RequestDeviceAuthorizationAsync(HttpClient httpClient, IdentityClientConfiguration configuration)
    {
        var discoveryResponse = GetDiscoveryResponse(configuration);
        var request = new DeviceAuthorizationRequest()
        {
            Address = discoveryResponse.DeviceAuthorizationEndpoint,
            Scope = configuration.Scope,
            ClientId = configuration.ClientId,
            ClientSecret = configuration.ClientSecret,
        };

        IdentityModelHttpRequestMessageOptions.ConfigureHttpRequestMessage?.Invoke(request);

        AddParametersToRequestAsync(configuration, request);

        var response = httpClient.RequestDeviceAuthorizationAsync(request);
        if (response.IsError)
        {
            throw new AbpException(response.ErrorDescription);
        }

        Logger.LogInformation($"First copy your one-time code: {response.UserCode}");
        Logger.LogInformation($"Open {response.VerificationUri} in your browser...");

        for (var i = 0; i < ((response.ExpiresIn ?? 300) / response.Interval + 1); i++)
        {
            Task.Delay(response.Interval * 1000);

            var tokenResponse = httpClient.RequestDeviceTokenAsync(new DeviceTokenRequest
            {
                Address = discoveryResponse.TokenEndpoint,
                ClientId = configuration.ClientId,
                ClientSecret = configuration.ClientSecret,
                DeviceCode = response.DeviceCode
            });

            if (tokenResponse.IsError)
            {
                switch (tokenResponse.Error)
                {
                    case "slow_down":
                    case "authorization_pending":
                        break;

                    case "expired_token":
                        throw new AbpException("This 'device_code' has expired. (expired_token)");

                    case "access_denied":
                        throw new AbpException("User denies the request(access_denied)");
                }
            }

            if (!tokenResponse.IsError)
            {
                return tokenResponse;
            }
        }

        throw new AbpException("Timeout!");
    }


    protected void AddParametersToRequestAsync(IdentityClientConfiguration configuration, ProtocolRequest request)
    {
        for (var pair in configuration.Where(p => p.Key.StartsWith("[o]", StringComparison.OrdinalIgnoreCase)))
        {
            request.Parameters.Add(pair);
        }

        return Task.CompletedTask;
    }

    protected   void AddHeaders(HttpClient client)
    {
        //tenantId
        if (CurrentTenant.Id.HasValue)
        {
            //TODO: Use AbpAspNetCoreMultiTenancyOptions to get the key
            client.DefaultRequestHeaders.Add(TenantResolverConsts.DefaultTenantKey, CurrentTenant.Id.Value.ToString());
        }
    }

    protected   String CalculateDiscoveryDocumentCacheKey(IdentityClientConfiguration configuration)
    {
        return IdentityModelDiscoveryDocumentCacheItem.CalculateCacheKey(configuration);
    }

    protected   String CalculateTokenCacheKey(IdentityClientConfiguration configuration)
    {
        return IdentityModelTokenCacheItem.CalculateCacheKey(configuration);
    }
}
