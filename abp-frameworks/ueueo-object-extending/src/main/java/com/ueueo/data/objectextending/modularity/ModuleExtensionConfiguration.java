package com.ueueo.data.objectextending.modularity;

import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Getter
public class ModuleExtensionConfiguration {
    @NonNull
    private Map<String, EntityExtensionConfiguration> entities;

    @NonNull
    public Map<String, Object> configuration;

    public ModuleExtensionConfiguration() {
        entities = new HashMap<>();
        configuration = new HashMap<>();
    }

    public ModuleExtensionConfiguration configureEntity(String objectName, Consumer<EntityExtensionConfiguration> configureAction) {
        EntityExtensionConfiguration configuration = entities.computeIfAbsent(
                objectName,
                s -> new EntityExtensionConfiguration()
        );

        configureAction.accept(configuration);
        return this;
    }
}
