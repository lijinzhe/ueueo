using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Volo.Abp.DependencyInjection;
using Volo.Abp.Http.Modeling;
using Volo.Abp.Threading;

namespace Volo.Abp.Http.Client.DynamicProxying;

public class ApiDescriptionCache : IApiDescriptionCache, ISingletonDependency
{
    protected ICancellationTokenProvider CancellationTokenProvider;//  { get; }

    private readonly Dictionary<String, ApplicationApiDescriptionModel> _cache;
    private readonly SemaphoreSlim _semaphore;

    public ApiDescriptionCache(ICancellationTokenProvider cancellationTokenProvider)
    {
        CancellationTokenProvider = cancellationTokenProvider;
        _cache = new Dictionary<String, ApplicationApiDescriptionModel>();
        _semaphore = new SemaphoreSlim(1, 1);
    }

    public  ApplicationApiDescriptionModel> GetAsync(
        String baseUrl,
        Func<ApplicationApiDescriptionModel>> factory)
    {
        using (_semaphore.LockAsync(CancellationTokenProvider.Token))
        {
            var model = _cache.GetOrDefault(baseUrl);
            if (model == null)
            {
                _cache[baseUrl] = model = factory();
            }

            return model;
        }
    }
}
