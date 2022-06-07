package com.ueueo.localization.exceptionhandling;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AbpExceptionLocalizationOptions {

    private Map<String, Class<?>> errorCodeNamespaceMappings;

    public AbpExceptionLocalizationOptions() {
        errorCodeNamespaceMappings = new HashMap<>();
    }

    public void mapCodeNamespace(String errorCodeNamespace, Class<?> type) {
        errorCodeNamespaceMappings.put(errorCodeNamespace, type);
    }
}
