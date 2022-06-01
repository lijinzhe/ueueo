using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using JetBrains.Annotations;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Logging.Abstractions;

namespace Volo.Abp.Threading;

public class AmbientDataContextAmbientScopeProvider<T> : IAmbientScopeProvider<T>
{
    public ILogger<AmbientDataContextAmbientScopeProvider<T>> Logger;// { get; set; }

    private static readonly ConcurrentDictionary<String, ScopeItem> ScopeDictionary = new ConcurrentDictionary<String, ScopeItem>();

    private readonly IAmbientDataContext _dataContext;

    public AmbientDataContextAmbientScopeProvider(@NonNull IAmbientDataContext dataContext)
    {
        Objects.requireNonNull(dataContext, nameof(dataContext));

        _dataContext = dataContext;

        Logger = NullLogger<AmbientDataContextAmbientScopeProvider<T>>.Instance;
    }

    public T GetValue(String contextKey)
    {
        var item = GetCurrentItem(contextKey);
        if (item == null)
        {
            return default;
        }

        return item.Value;
    }

    public IDisposable BeginScope(String contextKey, T value)
    {
        var item = new ScopeItem(value, GetCurrentItem(contextKey));

        if (!ScopeDictionary.TryAdd(item.Id, item))
        {
            throw new AbpException("Can not add item! ScopeDictionary.TryAdd returns false!");
        }

        _dataContext.SetData(contextKey, item.Id);

        return new DisposeAction(() =>
        {
            ScopeDictionary.TryRemove(item.Id, out item);

            if (item.Outer == null)
            {
                _dataContext.SetData(contextKey, null);
                return;
            }

            _dataContext.SetData(contextKey, item.Outer.Id);
        });
    }

    private ScopeItem GetCurrentItem(String contextKey)
    {
        return _dataContext.GetData(contextKey) is String objKey ? ScopeDictionary.GetOrDefault(objKey) : null;
    }

    private class ScopeItem
    {
        public String Id;//  { get; }

        public ScopeItem Outer;//  { get; }

        public T Value;//  { get; }

        public ScopeItem(T value, ScopeItem outer = null)
        {
            Id = Guid.NewGuid().ToString();

            Value = value;
            Outer = outer;
        }
    }
}
