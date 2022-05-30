using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.Extensions.Caching.Distributed;

namespace Volo.Abp.Caching;

public interface ICacheSupportsMultipleItems
{
    byte[][] GetMany(
        IEnumerable<String> keys
    );

    Task<byte[][]> GetManyAsync(
        IEnumerable<String> keys,
        CancellationToken token = default
    );

    void SetMany(
        IEnumerable<KeyValuePair<String, byte[]>> items,
        DistributedCacheEntryOptions options
    );

    void SetManyAsync(
        IEnumerable<KeyValuePair<String, byte[]>> items,
        DistributedCacheEntryOptions options,
        CancellationToken token = default
    );

    void RefreshMany(
        IEnumerable<String> keys
    );

    void RefreshManyAsync(
        IEnumerable<String> keys,
        CancellationToken token = default
    );

    void RemoveMany(
        IEnumerable<String> keys
    );

    void RemoveManyAsync(
        IEnumerable<String> keys,
        CancellationToken token = default
    );
}
