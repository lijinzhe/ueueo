package com.ueueo.eventbus.distributed;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.function.Function;

@Data
public class OutboxConfig {
    @NonNull
    public String name;

    public Class<?> implementationType;

    public Function<Class<?>, Boolean> selector;

    /**
     * Used to enable/disable sending events from outbox to the message broker.
     * Default: true.
     */
    public boolean isSendingEnabled = true;

    public OutboxConfig(@NonNull String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }
}
