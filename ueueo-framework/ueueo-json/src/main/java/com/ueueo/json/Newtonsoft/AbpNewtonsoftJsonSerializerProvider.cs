using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using Newtonsoft.Json;
using Newtonsoft.Json.Serialization;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Json.Newtonsoft;

public class AbpNewtonsoftJsonSerializerProvider : IJsonSerializerProvider, ITransientDependency
{
    private static readonly CamelCaseExceptDictionaryKeysResolver SharedCamelCaseExceptDictionaryKeysResolver =
        new CamelCaseExceptDictionaryKeysResolver();

    protected List<JsonConverter> Converters;//  { get; }

    public AbpNewtonsoftJsonSerializerProvider(
        IOptions<AbpNewtonsoftJsonSerializerOptions> options,
        IServiceProvider serviceProvider)
    {
        Converters = options.Value
            .Converters
            .Select(c => (JsonConverter)serviceProvider.GetRequiredService(c))
            .ToList();
    }

    public boolean CanHandle(Type type)
    {
        return true;
    }

    public String Serialize(Object obj, boolean camelCase = true, boolean indented = false)
    {
        return JsonConvert.SerializeObject(obj, CreateSerializerSettings(camelCase, indented));
    }

    public T Deserialize<T>(String jsonString, boolean camelCase = true)
    {
        return JsonConvert.DeserializeObject<T>(jsonString, CreateSerializerSettings(camelCase));
    }

    public Object Deserialize(Type type, String jsonString, boolean camelCase = true)
    {
        return JsonConvert.DeserializeObject(jsonString, type, CreateSerializerSettings(camelCase));
    }

    protected   JsonSerializerSettings CreateSerializerSettings(boolean camelCase = true, boolean indented = false)
    {
        var settings = new JsonSerializerSettings();

        settings.Converters.InsertRange(0, Converters);

        if (camelCase)
        {
            settings.ContractResolver = SharedCamelCaseExceptDictionaryKeysResolver;
        }

        if (indented)
        {
            settings.Formatting = Formatting.Indented;
        }

        return settings;
    }

    private class CamelCaseExceptDictionaryKeysResolver : CamelCasePropertyNamesContractResolver
    {
        protected override JsonDictionaryContract CreateDictionaryContract(Type objectType)
        {
            var contract =super.CreateDictionaryContract(objectType);

            contract.DictionaryKeyResolver = propertyName => propertyName;

            return contract;
        }
    }
}
