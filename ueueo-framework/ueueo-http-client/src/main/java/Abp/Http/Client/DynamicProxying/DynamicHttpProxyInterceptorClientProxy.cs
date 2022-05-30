using System.Net.Http;
using System.Threading.Tasks;
using Volo.Abp.Http.Client.ClientProxying;

namespace Volo.Abp.Http.Client.DynamicProxying;

public class DynamicHttpProxyInterceptorClientProxy<TService> : ClientProxyBase<TService>
{
    public    Task<T> CallRequestAsync<T>(ClientProxyRequestContext requestContext)
    {
        returnsuper.RequestAsync<T>(requestContext);
    }

    public    Task<HttpContent> CallRequestAsync(ClientProxyRequestContext requestContext)
    {
        returnsuper.RequestAsync(requestContext);
    }
}
