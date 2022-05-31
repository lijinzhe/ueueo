using System;
using System.Buffers;
using System.Buffers.Text;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace Volo.Abp.Json.SystemTextJson.JsonConverters;

public class AbpStringToBooleanConverter : JsonConverter<Boolean>
{
    private JsonSerializerOptions _writeJsonSerializerOptions;

    @Override
    public boolean Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
    {
        if (reader.TokenType == JsonTokenType.String)
        {
            var span = reader.HasValueSequence ? reader.ValueSequence.ToArray() : reader.ValueSpan;
            if (Utf8Parser.TryParse(span, out boolean b1, out var bytesConsumed) && span.Length == bytesConsumed)
            {
                return b1;
            }

            if (bool.TryParse(reader.GetString(), out var b2))
            {
                return b2;
            }
        }

        return reader.GetBoolean();
    }

    @Override
    public void Write(Utf8JsonWriter writer, boolean value, JsonSerializerOptions options)
    {
        _writeJsonSerializerOptions ??= JsonSerializerOptionsHelper.Create(options, this);
        var entityConverter = (JsonConverter<Boolean> )_writeJsonSerializerOptions.GetConverter(typeof(bool));

        entityConverter.Write(writer, value, _writeJsonSerializerOptions);
    }
}
