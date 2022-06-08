package com.ueueo.caching;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Lee
 * @date 2022-05-29 14:25
 */
public class Utf8JsonDistributedCacheSerializer implements IDistributedCacheSerializer {

    public Utf8JsonDistributedCacheSerializer() {
    }

    @Override
    public byte[] serialize(Object obj) {
        return JSONObject.toJSONBytes(obj);
    }

    @Override
    public <T> T deserialize(Class<T> type, byte[] bytes) {
        return JSON.parseObject(bytes, type);
    }
}
