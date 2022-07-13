package com.ueueo.eventbus.distributed;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.function.Function;

@Data
public class OutboxConfig {
    @NonNull
    private String name;

    private Class<?> implementationType;

    private Function<Class<?>, Boolean> selector;

    /**
     * Used to enable/disable sending events from outbox to the message broker.
     * Default: true.
     */
    private boolean isSendingEnabled = true;

    public OutboxConfig(@NonNull String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }
}
