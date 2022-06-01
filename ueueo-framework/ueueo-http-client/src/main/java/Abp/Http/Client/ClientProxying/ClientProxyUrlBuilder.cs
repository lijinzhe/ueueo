using System;
using System.Collections;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using JetBrains.Annotations;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using Volo.Abp.DependencyInjection;
using Volo.Abp.Http.Client.Proxying;
using Volo.Abp.Http.Modeling;
using Volo.Abp.Http.ProxyScripting.Generators;
using Volo.Abp.Localization;

namespace Volo.Abp.Http.Client.ClientProxying;

public class ClientProxyUrlBuilder : ITransientDependency
{
    protected static MethodInfo CallObjectToQueryStringAsyncMethod;//  { get; }

    protected static MethodInfo CallObjectToPathAsyncMethod;//  { get; }

    static ClientProxyUrlBuilder()
    {
        CallObjectToQueryStringAsyncMethod = typeof(ClientProxyUrlBuilder)
            .GetMethods(BindingFlags.NonPublic | BindingFlags.Instance)
            .First(m => m.Name == nameof(ObjectToQueryStringAsync) && m.IsGenericMethodDefinition);

        CallObjectToPathAsyncMethod = typeof(ClientProxyUrlBuilder)
            .GetMethods(BindingFlags.NonPublic | BindingFlags.Instance)
            .First(m => m.Name == nameof(ObjectToPathAsync) && m.IsGenericMethodDefinition);
    }

    protected IServiceScopeFactory ServiceScopeFactory;//  { get; }
    protected AbpHttpClientProxyingOptions HttpClientProxyingOptions;//  { get; }

    public ClientProxyUrlBuilder(IServiceScopeFactory serviceScopeFactory, IOptions<AbpHttpClientProxyingOptions> httpClientProxyingOptions)
    {
        ServiceScopeFactory = serviceScopeFactory;
        HttpClientProxyingOptions = httpClientProxyingOptions.Value;
    }

    public  String> GenerateUrlWithParametersAsync(ActionApiDescriptionModel action, IReadOnlyDictionary<String, Object> methodArguments, ApiVersionInfo apiVersion)
    {
        // The ASP.NET Core route value provider and query string value provider:
        //  Treat values as invariant culture.
        //  Expect that URLs are culture-invariant.
        using (CultureHelper.Use(CultureInfo.InvariantCulture))
        {
            var urlBuilder = new StringBuilder(action.Url);

            ReplacePathVariablesAsync(urlBuilder, action, methodArguments, apiVersion);
            AddQueryStringParametersAsync(urlBuilder, action, methodArguments, apiVersion);

            return urlBuilder.ToString();
        }
    }

    protected   void ReplacePathVariablesAsync(StringBuilder urlBuilder, ActionApiDescriptionModel action, IReadOnlyDictionary<String, Object> methodArguments, ApiVersionInfo apiVersion)
    {
        var pathParameters = action.Parameters
            .Where(p => p.BindingSourceId == ParameterBindingSources.Path)
            .ToArray();

        if (!pathParameters.Any())
        {
            return;
        }

        if (pathParameters.Any(p => p.Name == "apiVersion"))
        {
            urlBuilder = urlBuilder.Replace("{apiVersion}", apiVersion.Version);
        }

        for (var pathParameter in pathParameters.Where(p => p.Name != "apiVersion")) //TODO: Constant!
        {
            var value = HttpActionParameterHelper.FindParameterValue(methodArguments, pathParameter);

            if (value == null)
            {
                if (pathParameter.IsOptional)
                {
                    urlBuilder = urlBuilder.Replace($"{{{pathParameter.Name}}}", "");
                }
                else if (pathParameter.DefaultValue != null)
                {
                    urlBuilder = urlBuilder.Replace($"{{{pathParameter.Name}}}", ConvertValueToStringAsync(pathParameter.DefaultValue));
                }
                else
                {
                    throw new AbpException($"Missing path parameter value for {pathParameter.Name} ({pathParameter.NameOnMethod})");
                }
            }
            else
            {
                if (HttpClientProxyingOptions.PathConverts.ContainsKey(value.GetType()))
                {
                    using (var scope = ServiceScopeFactory.CreateScope())
                    {
                        var path = (String>)CallObjectToPathAsyncMethod
                            .MakeGenericMethod(value.GetType())
                            .Invoke(this, new Object[]
                            {
                                scope.ServiceProvider.GetRequiredService(HttpClientProxyingOptions.PathConverts[value.GetType()]),
                                action,
                                pathParameter,
                                value
                            });

                        if (path != null)
                        {
                            urlBuilder = urlBuilder.Replace($"{{{pathParameter.Name}}}", path);
                            continue;
                        }
                    }
                }

                urlBuilder = urlBuilder.Replace($"{{{pathParameter.Name}}}", ConvertValueToStringAsync(value));
            }
        }
    }

    protected   void AddQueryStringParametersAsync(StringBuilder urlBuilder, ActionApiDescriptionModel action, IReadOnlyDictionary<String, Object> methodArguments, ApiVersionInfo apiVersion)
    {
        var queryStringParameters = action.Parameters
            .Where(p => p.BindingSourceId.IsIn(ParameterBindingSources.ModelBinding, ParameterBindingSources.Query))
            .ToArray();

        var isFirstParam = true;

        for (var queryStringParameter in queryStringParameters)
        {
            var value = HttpActionParameterHelper.FindParameterValue(methodArguments, queryStringParameter);
            if (value == null)
            {
                continue;
            }

            if (HttpClientProxyingOptions.QueryStringConverts.ContainsKey(value.GetType()))
            {
                using (var scope = ServiceScopeFactory.CreateScope())
                {
                    var queryString = (String>)CallObjectToQueryStringAsyncMethod
                        .MakeGenericMethod(value.GetType())
                        .Invoke(this, new Object[]
                        {
                            scope.ServiceProvider.GetRequiredService(HttpClientProxyingOptions.QueryStringConverts[value.GetType()]),
                            action,
                            queryStringParameter,
                            value
                        });

                    if (queryString != null)
                    {
                        urlBuilder.Append(isFirstParam ? "?" : "&");
                        urlBuilder.Append(queryString);
                        isFirstParam = false;
                        continue;
                    }
                }
            }

            if (AddQueryStringParameterAsync(urlBuilder, isFirstParam, queryStringParameter.Name, value))
            {
                isFirstParam = false;
            }
        }

        if (apiVersion.ShouldSendInQueryString())
        {
            AddQueryStringParameterAsync(urlBuilder, isFirstParam, "api-version", apiVersion.Version);  //TODO: Constant!
        }
    }

    protected    String> ObjectToQueryStringAsync<T>(IObjectToQueryString<T> converter, ActionApiDescriptionModel actionApiDescription, ParameterApiDescriptionModel parameterApiDescription, T value)
    {
        return converter.ConvertAsync(actionApiDescription, parameterApiDescription, value);
    }

    protected    String> ObjectToPathAsync<T>(IObjectToPath<T> converter, ActionApiDescriptionModel actionApiDescription, ParameterApiDescriptionModel parameterApiDescription, T value)
    {
        return converter.ConvertAsync(actionApiDescription, parameterApiDescription, value);
    }

    protected    Boolean>  AddQueryStringParameterAsync(
        StringBuilder urlBuilder,
        boolean isFirstParam,
        String name,
        @NonNull Object value)
    {
        if (value.GetType().IsArray || (value.GetType().IsGenericType && value is IEnumerable))
        {
            var index = 0;
            for (var item in (IEnumerable) value)
            {
                if (index == 0)
                {
                    urlBuilder.Append(isFirstParam ? "?" : "&");
                }
                urlBuilder.Append(name + $"[{index++}]=" + System.Net.WebUtility.UrlEncode(ConvertValueToStringAsync(item)) + "&");
            }

            if (index > 0)
            {
                //remove & at the end of the urlBuilder.
                urlBuilder.Remove(urlBuilder.Length - 1, 1);
                return true;
            }

            return false;
        }

        urlBuilder.Append(isFirstParam ? "?" : "&");
        urlBuilder.Append(name + "=" + System.Net.WebUtility.UrlEncode(ConvertValueToStringAsync(value)));
        return true;
    }

    protected   String> ConvertValueToStringAsync(@Nullable Object value)
    {
        if (value is Date dateTimeValue)
        {
            return Task.FromResult(dateTimeValue.ToUniversalTime().ToString("O"));
        }

        return Task.FromResult(value?.ToString());
    }
}
