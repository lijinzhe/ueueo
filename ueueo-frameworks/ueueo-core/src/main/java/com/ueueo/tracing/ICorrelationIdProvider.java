package com.ueueo.tracing;

import org.springframework.lang.NonNull;

/**
 * @author Lee
 * @date 2022-05-23 21:06
 */
public interface ICorrelationIdProvider {
    /**
     * 获取 Correlation ID
     *
     * @return
     */
    @NonNull
    String get();
}
