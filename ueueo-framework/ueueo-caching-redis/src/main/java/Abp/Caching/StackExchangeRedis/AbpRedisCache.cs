using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.Extensions.Caching.Distributed;
using Microsoft.Extensions.Caching.StackExchangeRedis;
using Microsoft.Extensions.Options;
using StackExchange.Redis;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Caching.StackExchangeRedis;

[DisableConventionalRegistration]
public class AbpRedisCache : RedisCache, ICacheSupportsMultipleItems
{
    protected static readonly String SetScript;
    protected static readonly String AbsoluteExpirationKey;
    protected static readonly String SlidingExpirationKey;
    protected static readonly String DataKey;
    protected static readonly long NotPresent;

    private static readonly FieldInfo RedisDatabaseField;
    private static readonly MethodInfo ConnectMethod;
    private static readonly MethodInfo ConnectAsyncMethod;
    private static readonly MethodInfo MapMetadataMethod;
    private static readonly MethodInfo GetAbsoluteExpirationMethod;
    private static readonly MethodInfo GetExpirationInSecondsMethod;

    protected IDatabase RedisDatabase => GetRedisDatabase();
    private IDatabase _redisDatabase;

    protected String Instance;//  { get; }

    static AbpRedisCache()
    {
        var type = typeof(RedisCache);

        RedisDatabaseField = type.GetField("_cache", BindingFlags.Instance | BindingFlags.NonPublic);

        ConnectMethod = type.GetMethod("Connect", BindingFlags.Instance | BindingFlags.NonPublic);

        ConnectAsyncMethod = type.GetMethod("ConnectAsync", BindingFlags.Instance | BindingFlags.NonPublic);

        MapMetadataMethod = type.GetMethod("MapMetadata", BindingFlags.Instance | BindingFlags.NonPublic);

        GetAbsoluteExpirationMethod =
            type.GetMethod("GetAbsoluteExpiration", BindingFlags.Static | BindingFlags.NonPublic);

        GetExpirationInSecondsMethod =
            type.GetMethod("GetExpirationInSeconds", BindingFlags.Static | BindingFlags.NonPublic);

        SetScript = type.GetField("SetScript", BindingFlags.Static | BindingFlags.NonPublic)?.GetValue(null)
            .ToString();

        AbsoluteExpirationKey = type.GetField("AbsoluteExpirationKey", BindingFlags.Static | BindingFlags.NonPublic)
            ?.GetValue(null).ToString();

        SlidingExpirationKey = type.GetField("SlidingExpirationKey", BindingFlags.Static | BindingFlags.NonPublic)
            ?.GetValue(null).ToString();

        DataKey = type.GetField("DataKey", BindingFlags.Static | BindingFlags.NonPublic)?.GetValue(null).ToString();

        // ReSharper disable once PossibleNullReferenceException
        NotPresent = type.GetField("NotPresent", BindingFlags.Static | BindingFlags.NonPublic).GetValue(null)
            .To<int>();
    }

    public AbpRedisCache(IOptions<RedisCacheOptions> optionsAccessor)
        : base(optionsAccessor)
    {
        Instance = optionsAccessor.Value.InstanceName ?? String.Empty;
    }

    protected   void Connect()
    {
        if (GetRedisDatabase() != null)
        {
            return;
        }

        ConnectMethod.Invoke(this, Array.Empty<Object>());
    }

    protected void ConnectAsync(CancellationToken token = default)
    {
        if (GetRedisDatabase() != null)
        {
            return Task.CompletedTask;
        }

        return (Task)ConnectAsyncMethod.Invoke(this, new Object[] { token });
    }

    public byte[][] GetMany(
        IEnumerable<String> keys)
    {
        keys = Objects.requireNonNull(keys, nameof(keys));

        return GetAndRefreshMany(keys, true);
    }

    public  Task<byte[][]> GetManyAsync(
        IEnumerable<String> keys,
        CancellationToken token = default)
    {
        keys = Objects.requireNonNull(keys, nameof(keys));

        return GetAndRefreshManyAsync(keys, true, token);
    }

    public void SetMany(
        IEnumerable<KeyValuePair<String, byte[]>> items,
        DistributedCacheEntryOptions options)
    {
        Connect();

        Task.WaitAll(PipelineSetMany(items, options));
    }

    public void SetManyAsync(
        IEnumerable<KeyValuePair<String, byte[]>> items,
        DistributedCacheEntryOptions options,
        CancellationToken token = default)
    {
        token.ThrowIfCancellationRequested();

        ConnectAsync(token);

        Task.WhenAll(PipelineSetMany(items, options));
    }

    public void RefreshMany(
        IEnumerable<String> keys)
    {
        keys = Objects.requireNonNull(keys, nameof(keys));

        GetAndRefreshMany(keys, false);
    }

    public void RefreshManyAsync(
        IEnumerable<String> keys,
        CancellationToken token = default)
    {
        keys = Objects.requireNonNull(keys, nameof(keys));

        GetAndRefreshManyAsync(keys, false, token);
    }

    public void RemoveMany(IEnumerable<String> keys)
    {
        keys = Objects.requireNonNull(keys, nameof(keys));

        Connect();

        RedisDatabase.KeyDelete(keys.Select(key => (RedisKey)(Instance + key)).ToArray());
    }

    public void RemoveManyAsync(IEnumerable<String> keys, CancellationToken token = default)
    {
        keys = Objects.requireNonNull(keys, nameof(keys));

        token.ThrowIfCancellationRequested();
        ConnectAsync(token);

        RedisDatabase.KeyDeleteAsync(keys.Select(key => (RedisKey)(Instance + key)).ToArray());
    }

    protected   byte[][] GetAndRefreshMany(
        IEnumerable<String> keys,
        boolean getData)
    {
        Connect();

        var keyArray = keys.Select(key => Instance + key).ToArray();
        RedisValue[][] results;

        if (getData)
        {
            results = RedisDatabase.HashMemberGetMany(keyArray, AbsoluteExpirationKey,
                SlidingExpirationKey, DataKey);
        }
        else
        {
            results = RedisDatabase.HashMemberGetMany(keyArray, AbsoluteExpirationKey,
                SlidingExpirationKey);
        }

        Task.WaitAll(PipelineRefreshManyAndOutData(keyArray, results, out var bytes));

        return bytes;
    }

    protected    Task<byte[][]> GetAndRefreshManyAsync(
        IEnumerable<String> keys,
        boolean getData,
        CancellationToken token = default)
    {
        token.ThrowIfCancellationRequested();

        ConnectAsync(token);

        var keyArray = keys.Select(key => Instance + key).ToArray();
        RedisValue[][] results;

        if (getData)
        {
            results = RedisDatabase.HashMemberGetManyAsync(keyArray, AbsoluteExpirationKey,
                SlidingExpirationKey, DataKey);
        }
        else
        {
            results = RedisDatabase.HashMemberGetManyAsync(keyArray, AbsoluteExpirationKey,
                SlidingExpirationKey);
        }

        Task.WhenAll(PipelineRefreshManyAndOutData(keyArray, results, out var bytes));

        return bytes;
    }

    protected   Task[] PipelineRefreshManyAndOutData(
        String[] keys,
        RedisValue[][] results,
        out byte[][] bytes)
    {
        bytes = new byte[keys.Length][];
        var tasks = new Task[keys.Length];

        for (var i = 0; i < keys.Length; i++)
        {
            if (results[i].Length >= 2)
            {
                MapMetadata(results[i], out DateTimeOffset? absExpr, out TimeSpan? sldExpr);

                if (sldExpr.HasValue)
                {
                    TimeSpan? expr;

                    if (absExpr.HasValue)
                    {
                        var relExpr = absExpr.Value - DateTimeOffset.Now;
                        expr = relExpr <= sldExpr.Value ? relExpr : sldExpr;
                    }
                    else
                    {
                        expr = sldExpr;
                    }

                    tasks[i] = RedisDatabase.KeyExpireAsync(keys[i], expr);
                }
                else
                {
                    tasks[i] = Task.CompletedTask;
                }
            }

            if (results[i].Length >= 3 && results[i][2].HasValue)
            {
                bytes[i] = results[i][2];
            }
            else
            {
                bytes[i] = null;
            }
        }

        return tasks;
    }

    protected   Task[] PipelineSetMany(
        IEnumerable<KeyValuePair<String, byte[]>> items,
        DistributedCacheEntryOptions options)
    {
        items = Objects.requireNonNull(items, nameof(items));
        options = Objects.requireNonNull(options, nameof(options));

        var itemArray = items.ToArray();
        var tasks = new Task[itemArray.Length];
        var creationTime = DateTimeOffset.UtcNow;
        var absoluteExpiration = GetAbsoluteExpiration(creationTime, options);

        for (var i = 0; i < itemArray.Length; i++)
        {
            tasks[i] = RedisDatabase.ScriptEvaluateAsync(SetScript, new RedisKey[] { Instance + itemArray[i].Key },
                new RedisValue[]
                {
                        absoluteExpiration?.Ticks ?? NotPresent,
                        options.SlidingExpiration?.Ticks ?? NotPresent,
                        GetExpirationInSeconds(creationTime, absoluteExpiration, options) ?? NotPresent,
                        itemArray[i].Value
                });
        }

        return tasks;
    }

    protected   void MapMetadata(
        RedisValue[] results,
        out DateTimeOffset? absoluteExpiration,
        out TimeSpan? slidingExpiration)
    {
        var parameters = new Object[] { results, null, null };
        MapMetadataMethod.Invoke(this, parameters);

        absoluteExpiration = (DateTimeOffset?)parameters[1];
        slidingExpiration = (TimeSpan?)parameters[2];
    }

    protected   long? GetExpirationInSeconds(
        DateTimeOffset creationTime,
        DateTimeOffset? absoluteExpiration,
        DistributedCacheEntryOptions options)
    {
        return (long?)GetExpirationInSecondsMethod.Invoke(null,
            new Object[] { creationTime, absoluteExpiration, options });
    }

    protected   DateTimeOffset? GetAbsoluteExpiration(
        DateTimeOffset creationTime,
        DistributedCacheEntryOptions options)
    {
        return (DateTimeOffset?)GetAbsoluteExpirationMethod.Invoke(null, new Object[] { creationTime, options });
    }

    private IDatabase GetRedisDatabase()
    {
        if (_redisDatabase == null)
        {
            _redisDatabase = RedisDatabaseField.GetValue(this) as IDatabase;
        }

        return _redisDatabase;
    }
}
