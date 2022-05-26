package com.ueueo.modularity;

import lombok.Data;

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

    private List<Class<?>> services;

    private Map<String, Object> items;

    /**
     * Gets/sets arbitrary named objects those can be stored during
     * the service registration phase and shared between modules.
     *
     * This is a shortcut usage of the <see cref="Items"/> dictionary.
     * Returns null if given key is not found in the <see cref="Items"/> dictionary.
     *
     * @param services
     */
    public ServiceConfigurationContext(List<Class<?>> services) {
        Objects.requireNonNull(services);
        this.services = services;
        this.items = new HashMap<>();
    }
}
