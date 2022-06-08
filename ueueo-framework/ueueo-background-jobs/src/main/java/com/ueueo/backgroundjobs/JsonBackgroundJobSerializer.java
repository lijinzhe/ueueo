package com.ueueo.backgroundjobs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonBackgroundJobSerializer implements IBackgroundJobSerializer {
    @Override
    public String serialize(Object obj) {
        return JSON.toJSONString(obj);
    }

    @Override
    public <T> T deserialize(String value, Class<T> type) {
        return JSONObject.parseObject(value, type);
    }

}
