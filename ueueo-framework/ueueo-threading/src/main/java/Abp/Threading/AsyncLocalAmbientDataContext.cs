using System.Collections.Concurrent;
using System.Threading;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Threading;

public class AsyncLocalAmbientDataContext : IAmbientDataContext, ISingletonDependency
{
    private static readonly ConcurrentDictionary<String, AsyncLocal<Object>> AsyncLocalDictionary = new ConcurrentDictionary<String, AsyncLocal<Object>>();

    public void SetData(String key, Object value)
    {
        var asyncLocal = AsyncLocalDictionary.GetOrAdd(key, (k) => new AsyncLocal<Object>());
        asyncLocal.Value = value;
    }

    public Object GetData(String key)
    {
        var asyncLocal = AsyncLocalDictionary.GetOrAdd(key, (k) => new AsyncLocal<Object>());
        return asyncLocal.Value;
    }
}
