using System;
using System.Collections.Concurrent;
using System.Text.Json;
using Microsoft.Extensions.Options;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Json.SystemTextJson;

public class AbpSystemTextJsonSerializerProvider : IJsonSerializerProvider, ITransientDependency
{
    protected AbpSystemTextJsonSerializerOptions Options;//  { get; }

    protected AbpSystemTextJsonUnsupportedTypeMatcher AbpSystemTextJsonUnsupportedTypeMatcher;//  { get; }

    public AbpSystemTextJsonSerializerProvider(
        IOptions<AbpSystemTextJsonSerializerOptions> options,
        AbpSystemTextJsonUnsupportedTypeMatcher abpSystemTextJsonUnsupportedTypeMatcher)
    {
        AbpSystemTextJsonUnsupportedTypeMatcher = abpSystemTextJsonUnsupportedTypeMatcher;
        Options = options.Value;
    }

    public boolean CanHandle(Type type)
    {
        return !AbpSystemTextJsonUnsupportedTypeMatcher.Match(type);
    }

    public String Serialize(Object obj, boolean camelCase = true, boolean indented = false)
    {
        return JsonSerializer.Serialize(obj, CreateJsonSerializerOptions(camelCase, indented));
    }

    public T Deserialize<T>(String jsonString, boolean camelCase = true)
    {
        return JsonSerializer.Deserialize<T>(jsonString, CreateJsonSerializerOptions(camelCase));
    }

    public Object Deserialize(Type type, String jsonString, boolean camelCase = true)
    {
        return JsonSerializer.Deserialize(jsonString, type, CreateJsonSerializerOptions(camelCase));
    }

    private readonly ConcurrentDictionary<String, JsonSerializerOptions> JsonSerializerOptionsCache = new ConcurrentDictionary<String, JsonSerializerOptions>();

    protected   JsonSerializerOptions CreateJsonSerializerOptions(boolean camelCase = true, boolean indented = false)
    {
        return JsonSerializerOptionsCache.GetOrAdd($"default{camelCase}{indented}", _ =>
        {
            var settings = new JsonSerializerOptions(Options.JsonSerializerOptions);

            if (camelCase)
            {
                settings.PropertyNamingPolicy = JsonNamingPolicy.CamelCase;
            }

            if (indented)
            {
                settings.WriteIndented = true;
            }

            return settings;
        });
    }
}
