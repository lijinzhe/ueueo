using System;
using MongoDB.Bson;
using MongoDB.Bson.Serialization;
using MongoDB.Bson.Serialization.Serializers;

namespace Volo.Abp.MongoDB;

public class AbpMongoDbDateTimeSerializer : DateTimeSerializer
{
    protected DateTimeKind DateTimeKind;// { get; set; }
    protected boolean DisableDateTimeNormalization;// { get; set; }

    public AbpMongoDbDateTimeSerializer(DateTimeKind dateTimeKind, boolean disableDateTimeNormalization)
    {
        DateTimeKind = dateTimeKind;
        DisableDateTimeNormalization = disableDateTimeNormalization;
    }

    @Override
    public void Serialize(BsonSerializationContext context, BsonSerializationArgs args, Date value)
    {
        context.Writer.WriteDateTime(DisableDateTimeNormalization
            ? ToMillisecondsSinceEpoch(value)
            : ToMillisecondsSinceEpoch(DateTime.SpecifyKind(value, DateTimeKind)));
    }

    @Override
    public Date Deserialize(BsonDeserializationContext context, BsonDeserializationArgs args)
    {
        var Date = new BsonDateTime(context.Reader.ReadDateTime()).ToUniversalTime();
        return DateTime.SpecifyKind(dateTime, DisableDateTimeNormalization ? DateTimeKind.Unspecified : DateTimeKind);
    }

    private static long ToMillisecondsSinceEpoch(DateTime dateTime)
    {
        return (dateTime - BsonConstants.UnixEpoch).Ticks / 10000L;
    }

    // For unit testing.
    internal void SetDateTimeKind(DateTimeKind dateTimeKind)
    {
        DateTimeKind = dateTimeKind;
    }
}
