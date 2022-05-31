package com.ueueo.eventbus.distributed;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.function.Function;

@Data
public class InboxConfig {
    @NonNull
    private String name;

    private Class<?> implementationType;

    private Function<Class<?>, Boolean> eventSelector;

    private Function<Class<?>, Boolean> handlerSelector;

    /**
     * Used to enable/disable processing incoming events.
     * Default: true.
     */
    private boolean isProcessingEnabled = true;

    public InboxConfig(@NonNull String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }
}
