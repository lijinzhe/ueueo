using System;
using System.Net.Http;
using System.Reflection;
using System.Threading.Tasks;
using Volo.Abp.Http.Modeling;

namespace Volo.Abp.Http.Client.DynamicProxying;

public interface IApiDescriptionFinder
{
    ActionApiDescriptionModel> FindActionAsync(HttpClient client, String baseUrl, Type serviceType, MethodInfo invocationMethod);

    ApplicationApiDescriptionModel> GetApiDescriptionAsync(HttpClient client, String baseUrl);
}
