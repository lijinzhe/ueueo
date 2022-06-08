package com.ueueo.blobstoring;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BlobContainerConfigurations {

    private BlobContainerConfiguration defaultConfiguration;

    private Map<String, BlobContainerConfiguration> containers;

    public BlobContainerConfigurations() {
        containers = new HashMap<>();
        containers.put(DefaultContainer.Name, new BlobContainerConfiguration(null));
        defaultConfiguration = getConfiguration(DefaultContainer.class);
    }

    public BlobContainerConfigurations configure(Class<?> containerType,
                                                 Consumer<BlobContainerConfiguration> configureAction) {
        BlobContainerNameAttribute attribute = containerType.getAnnotation(BlobContainerNameAttribute.class);
        return configure(attribute.name(), configureAction);
    }

    public BlobContainerConfigurations configure(
            @NonNull String name,
            @NonNull Consumer<BlobContainerConfiguration> configureAction) {
        Assert.hasLength(name, "name must not empty!");
        Objects.requireNonNull(configureAction);

        configureAction.accept(
                containers.computeIfAbsent(
                        name,
                        s -> new BlobContainerConfiguration(defaultConfiguration)
                )
        );

        return this;
    }

    public BlobContainerConfigurations configureDefault(Consumer<BlobContainerConfiguration> configureAction) {
        configureAction.accept(defaultConfiguration);
        return this;
    }

    public BlobContainerConfigurations configureAll(BiConsumer<String, BlobContainerConfiguration> configureAction) {
        for (Map.Entry<String, BlobContainerConfiguration> container : containers.entrySet()) {
            configureAction.accept(container.getKey(), container.getValue());
        }

        return this;
    }

    @NonNull
    public BlobContainerConfiguration getConfiguration(Class<?> containerType) {
        BlobContainerNameAttribute attribute = containerType.getAnnotation(BlobContainerNameAttribute.class);
        return getConfiguration(attribute.name());
    }

    @NonNull
    public BlobContainerConfiguration getConfiguration(@NonNull String name) {
        Assert.hasLength(name, "name must not empty!");

        return containers.get(name) != null ? containers.get(name) : defaultConfiguration;
    }
}
