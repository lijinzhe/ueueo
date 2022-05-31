using System;
using System.Threading.Tasks;
using Volo.Abp.Http.Modeling;

namespace Volo.Abp.Http.Client.DynamicProxying;

public interface IApiDescriptionCache
{
    ApplicationApiDescriptionModel> GetAsync(
        String baseUrl,
        Func<ApplicationApiDescriptionModel>> factory
    );
}
