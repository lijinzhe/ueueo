package com.ueueo.data.objectextending;

import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2022-05-23 13:56
 */
@Getter
public class ObjectExtensionInfo {
    @NonNull
    private Class<?> type;

    @NonNull
    protected ConcurrentHashMap<String, ObjectExtensionPropertyInfo> properties;

    @NonNull
    public ConcurrentHashMap<Object, Object> configuration;

    @NonNull
    public List<Consumer<ObjectExtensionValidationContext>> validators;

    public ObjectExtensionInfo(Class<?> type) {
        this.type = type;
        this.properties = new ConcurrentHashMap<>();
        this.configuration = new ConcurrentHashMap<>();
        this.validators = new ArrayList<>();
    }

    public boolean hasProperty(String propertyName) {
        return this.properties.containsKey(propertyName);
    }

    public ObjectExtensionInfo addOrUpdateProperty(@NonNull Class<?> propertyType,
                                                   @NonNull String propertyName,
                                                   Consumer<ObjectExtensionPropertyInfo> configureAction
    ) {
        Assert.notNull(propertyType, "propertyType must not null!");
        Assert.notNull(propertyName, "propertyName must not null!");
        ObjectExtensionPropertyInfo propertyInfo = this.properties.get(propertyName);
        if (propertyInfo == null) {
            propertyInfo = new ObjectExtensionPropertyInfo(this, propertyType, propertyName);
            this.properties.put(propertyName, propertyInfo);
        }
        if (configureAction != null) {
            configureAction.accept(propertyInfo);
        }
        return this;
    }

    public List<ObjectExtensionPropertyInfo> getProperties() {
        return new ArrayList<>(properties.values()).stream().sorted(Comparator.comparing(ObjectExtensionPropertyInfo::getName))
                .collect(Collectors.toList());
    }

    public ObjectExtensionPropertyInfo getPropertyOrNull(@NonNull String propertyName) {
        Objects.requireNonNull(propertyName);
        return properties.get(propertyName);
    }
}
