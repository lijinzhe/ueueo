package com.ueueo.auditing;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;

/**
 * @author Lee
 * @date 2022-05-26 11:49
 */
public class JsonNetAuditSerializer implements IAuditSerializer {

    protected AbpAuditingOptions options;

    private static SerializeConfig sharedJsonSerializerSettings = SerializeConfig.getGlobalInstance();

    public JsonNetAuditSerializer(AbpAuditingOptions options) {
        this.options = options;
    }

    @Override
    public String serialize(Object obj) {
        return JSON.toJSONString(obj, sharedJsonSerializerSettings);
    }

}
