package com.ueueo.caching;

import com.ueueo.json.IJsonSerializer;

import java.nio.charset.StandardCharsets;

/**
 * @author Lee
 * @date 2022-05-29 14:25
 */
public class Utf8JsonDistributedCacheSerializer implements IDistributedCacheSerializer {

    protected IJsonSerializer jsonSerializer;

    public Utf8JsonDistributedCacheSerializer(IJsonSerializer jsonSerializer) {
        this.jsonSerializer = jsonSerializer;
    }

    @Override
    public byte[] serialize(Object obj) {
        return jsonSerializer.serialize(obj, true, false).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T deserialize(Class<T> type, byte[] bytes) {
        return jsonSerializer.deserialize(type, new String(bytes, StandardCharsets.UTF_8), true);
    }
}
