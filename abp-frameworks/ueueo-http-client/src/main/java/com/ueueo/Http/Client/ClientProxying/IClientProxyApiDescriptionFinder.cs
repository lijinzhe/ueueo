using Volo.Abp.Http.Modeling;

namespace Volo.Abp.Http.Client.ClientProxying;

public interface IClientProxyApiDescriptionFinder
{
    ActionApiDescriptionModel FindAction(String methodName);

    ApplicationApiDescriptionModel GetApiDescription();
}
