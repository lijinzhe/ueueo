using System;
using System.Reflection;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace Volo.Abp.Json.SystemTextJson.JsonConverters;

public class AbpStringToEnumFactory : JsonConverterFactory
{
    private readonly JsonNamingPolicy _namingPolicy;
    private readonly boolean _allowIntegerValues;

    public AbpStringToEnumFactory()
        : this(namingPolicy: null, allowIntegerValues: true)
    {

    }

    public AbpStringToEnumFactory(JsonNamingPolicy namingPolicy, boolean allowIntegerValues)
    {
        _namingPolicy = namingPolicy;
        _allowIntegerValues = allowIntegerValues;
    }

    @Override
    public boolean CanConvert(Type typeToConvert)
    {
        return typeToConvert.IsEnum;
    }

    @Override
    public JsonConverter CreateConverter(Type typeToConvert, JsonSerializerOptions options)
    {
        return (JsonConverter)Activator.CreateInstance(
            typeof(AbpStringToEnumConverter<>).MakeGenericType(typeToConvert),
            BindingFlags.Instance | BindingFlags.Public,
            binder: null,
            new Object[] { _namingPolicy, _allowIntegerValues },
            culture: null)!;
    }
}
