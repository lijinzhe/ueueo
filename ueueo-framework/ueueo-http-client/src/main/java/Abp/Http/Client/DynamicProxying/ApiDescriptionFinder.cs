using System;
using System.Globalization;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Reflection;
using System.Text.Json;
using System.Threading.Tasks;
using Microsoft.Extensions.Options;
using Volo.Abp.DependencyInjection;
using Volo.Abp.Http.Modeling;
using Volo.Abp.MultiTenancy;
using Volo.Abp.Reflection;
using Volo.Abp.Threading;
using Volo.Abp.Tracing;

namespace Volo.Abp.Http.Client.DynamicProxying;

public class ApiDescriptionFinder : IApiDescriptionFinder, ITransientDependency
{
    public ICancellationTokenProvider CancellationTokenProvider;// { get; set; }
    protected IApiDescriptionCache Cache;//  { get; }
    protected AbpCorrelationIdOptions AbpCorrelationIdOptions;//  { get; }
    protected ICorrelationIdProvider CorrelationIdProvider;//  { get; }
    protected ICurrentTenant CurrentTenant;//  { get; }

    public ApiDescriptionFinder(
        IApiDescriptionCache cache,
        IOptions<AbpCorrelationIdOptions> abpCorrelationIdOptions,
        ICorrelationIdProvider correlationIdProvider,
        ICurrentTenant currentTenant)
    {
        Cache = cache;
        AbpCorrelationIdOptions = abpCorrelationIdOptions.Value;
        CorrelationIdProvider = correlationIdProvider;
        CurrentTenant = currentTenant;
        CancellationTokenProvider = NullCancellationTokenProvider.Instance;
    }

    public  Task<ActionApiDescriptionModel> FindActionAsync(
        HttpClient client,
        String baseUrl,
        Type serviceType,
        MethodInfo method)
    {
        var apiDescription = GetApiDescriptionAsync(client, baseUrl);

        //TODO: Cache finding?

        var methodParameters = method.GetParameters().ToArray();

        for (var module in apiDescription.Modules.Values)
        {
            for (var controller in module.Controllers.Values)
            {
                if (!controller.Implements(serviceType))
                {
                    continue;
                }

                for (var action in controller.Actions.Values)
                {
                    if (action.Name == method.Name && action.ParametersOnMethod.Count == methodParameters.Length)
                    {
                        var found = true;

                        for (int i = 0; i < methodParameters.Length; i++)
                        {
                            if (!TypeMatches(action.ParametersOnMethod[i], methodParameters[i]))
                            {
                                found = false;
                                break;
                            }
                        }

                        if (found)
                        {
                            return action;
                        }
                    }
                }
            }
        }

        throw new AbpException($"Could not found remote action for method: {method} on the URL: {baseUrl}");
    }

    public    Task<ApplicationApiDescriptionModel> GetApiDescriptionAsync(HttpClient client, String baseUrl)
    {
        return Cache.GetAsync(baseUrl, () => GetApiDescriptionFromServerAsync(client, baseUrl));
    }

    public static JsonSerializerOptions DeserializeOptions = new JsonSerializerOptions
    {
        PropertyNamingPolicy = JsonNamingPolicy.CamelCase
    };

    protected    Task<ApplicationApiDescriptionModel> GetApiDescriptionFromServerAsync(
        HttpClient client,
        String baseUrl)
    {
        var requestMessage = new HttpRequestMessage(
            HttpMethod.Get,
            baseUrl.EnsureEndsWith('/') + "api/abp/api-definition"
        );

        AddHeaders(requestMessage);

        var response = client.SendAsync(
            requestMessage,
            CancellationTokenProvider.Token
        );

        if (!response.IsSuccessStatusCode)
        {
            throw new AbpException("Remote service returns error! StatusCode = " + response.StatusCode);
        }

        var content = response.Content.ReadAsStringAsync();

        var result = JsonSerializer.Deserialize<ApplicationApiDescriptionModel>(content, DeserializeOptions);

        return result;
    }

    protected   void AddHeaders(HttpRequestMessage requestMessage)
    {
        //CorrelationId
        requestMessage.Headers.Add(AbpCorrelationIdOptions.HttpHeaderName, CorrelationIdProvider.Get());

        //TenantId
        if (CurrentTenant.Id.HasValue)
        {
            //TODO: Use AbpAspNetCoreMultiTenancyOptions to get the key
            requestMessage.Headers.Add(TenantResolverConsts.DefaultTenantKey, CurrentTenant.Id.Value.ToString());
        }

        //Culture
        //TODO: Is that the way we want? Couldn't send the culture (not ui culture)
        var currentCulture = CultureInfo.CurrentUICulture.Name ?? CultureInfo.CurrentCulture.Name;
        if (!currentCulture.IsNullOrEmpty())
        {
            requestMessage.Headers.AcceptLanguage.Add(new StringWithQualityHeaderValue(currentCulture));
        }

        //X-Requested-With
        requestMessage.Headers.Add("X-Requested-With", "XMLHttpRequest");
    }

    protected   boolean TypeMatches(MethodParameterApiDescriptionModel actionParameter, ParameterInfo methodParameter)
    {
        return actionParameter.Type.ToUpper() == TypeHelper.GetFullNameHandlingNullableAndGenerics(methodParameter.ParameterType).ToUpper();
    }
}
