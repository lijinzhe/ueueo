package com.ueueo.modularity;

import lombok.Data;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Lee
 * @date 2021-08-20 09:39
 */
@Data
public class ServiceConfigurationContext {

    private ApplicationContext applicationContext;

    private Map<String, Object> items;

    /**
     * Gets/sets arbitrary named objects those can be stored during
     * the service registration phase and shared between modules.
     *
     * This is a shortcut usage of the <see cref="Items"/> dictionary.
     * Returns null if given key is not found in the <see cref="Items"/> dictionary.
     *
     * @param applicationContext
     */
    public ServiceConfigurationContext(ApplicationContext applicationContext) {
        Objects.requireNonNull(applicationContext);
        this.applicationContext = applicationContext;
        this.items = new HashMap<>();
    }
}
