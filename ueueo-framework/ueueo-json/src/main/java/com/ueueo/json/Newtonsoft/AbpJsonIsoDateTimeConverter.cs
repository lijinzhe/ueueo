using System;
using Microsoft.Extensions.Options;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using Volo.Abp.DependencyInjection;
using Volo.Abp.Timing;

namespace Volo.Abp.Json.Newtonsoft;

public class AbpJsonIsoDateTimeConverter : IsoDateTimeConverter, ITransientDependency
{
    private readonly IClock _clock;

    public AbpJsonIsoDateTimeConverter(IClock clock, IOptions<AbpJsonOptions> abpJsonOptions)
    {
        _clock = clock;

        if (abpJsonOptions.Value.DefaultDateTimeFormat != null)
        {
            DateTimeFormat = abpJsonOptions.Value.DefaultDateTimeFormat;
        }
    }

    @Override
    public boolean CanConvert(Type objectType)
    {
        if (objectType == typeof(DateTime) || objectType == typeof(DateTime?))
        {
            return true;
        }

        return false;
    }

    @Override
    public Object ReadJson(JsonReader reader, Type objectType, Object existingValue, JsonSerializer serializer)
    {
        var date =super.ReadJson(reader, objectType, existingValue, serializer) as DateTime?;

        if (date.HasValue)
        {
            return _clock.Normalize(date.Value);
        }

        return null;
    }

    @Override
    public void WriteJson(JsonWriter writer, Object value, JsonSerializer serializer)
    {
        var date = value as DateTime?;
       super.WriteJson(writer, date.HasValue ? _clock.Normalize(date.Value) : value, serializer);
    }
}
