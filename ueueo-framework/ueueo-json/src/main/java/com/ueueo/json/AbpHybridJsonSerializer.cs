using System;
using System.Linq;
using JetBrains.Annotations;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Json;

public class AbpHybridJsonSerializer : IJsonSerializer, ITransientDependency
{
    protected AbpJsonOptions Options;//  { get; }

    protected IServiceScopeFactory ServiceScopeFactory;//  { get; }

    public AbpHybridJsonSerializer(IOptions<AbpJsonOptions> options, IServiceScopeFactory serviceScopeFactory)
    {
        Options = options.Value;
        ServiceScopeFactory = serviceScopeFactory;
    }

    public String Serialize(@Nullable Object obj, boolean camelCase = true, boolean indented = false)
    {
        using (var scope = ServiceScopeFactory.CreateScope())
        {
            var serializerProvider = GetSerializerProvider(scope.ServiceProvider, obj?.GetType());
            return serializerProvider.Serialize(obj, camelCase, indented);
        }
    }

    public T Deserialize<T>(@Nonnull String jsonString, boolean camelCase = true)
    {
        Objects.requireNonNull(jsonString, nameof(jsonString));

        using (var scope = ServiceScopeFactory.CreateScope())
        {
            var serializerProvider = GetSerializerProvider(scope.ServiceProvider, typeof(T));
            return serializerProvider.Deserialize<T>(jsonString, camelCase);
        }
    }

    public Object Deserialize(Type type, @Nonnull String jsonString, boolean camelCase = true)
    {
        Objects.requireNonNull(jsonString, nameof(jsonString));

        using (var scope = ServiceScopeFactory.CreateScope())
        {
            var serializerProvider = GetSerializerProvider(scope.ServiceProvider, type);
            return serializerProvider.Deserialize(type, jsonString, camelCase);
        }
    }

    protected   IJsonSerializerProvider GetSerializerProvider(IServiceProvider serviceProvider, @Nullable Type type)
    {
        for (var providerType in Options.Providers.Reverse())
        {
            var provider = serviceProvider.GetRequiredService(providerType) as IJsonSerializerProvider;
            if (provider.CanHandle(type))
            {
                return provider;
            }
        }

        throw new AbpException($"There is no IJsonSerializerProvider that can handle '{type.GetFullNameWithAssemblyName()}'!");
    }
}
