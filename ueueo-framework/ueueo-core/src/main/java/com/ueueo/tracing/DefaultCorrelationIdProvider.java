package com.ueueo.tracing;

import java.util.UUID;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-23 21:06
 */
public class DefaultCorrelationIdProvider implements ICorrelationIdProvider {

    @Override
    public String get() {
        return UUID.randomUUID().toString();
    }
}
