using System;
using Volo.Abp.DependencyInjection;
using Volo.Abp.Json;

namespace Volo.Abp.BackgroundJobs;

public class JsonBackgroundJobSerializer : IBackgroundJobSerializer, ITransientDependency
{
    private readonly IJsonSerializer _jsonSerializer;

    public JsonBackgroundJobSerializer(IJsonSerializer jsonSerializer)
    {
        _jsonSerializer = jsonSerializer;
    }

    public String Serialize(Object obj)
    {
        return _jsonSerializer.Serialize(obj);
    }

    public Object Deserialize(String value, Type type)
    {
        return _jsonSerializer.Deserialize(type, value);
    }

    public T Deserialize<T>(String value)
    {
        return _jsonSerializer.Deserialize<T>(value);
    }
}
