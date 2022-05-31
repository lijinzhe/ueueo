package com.ueueo.data.objectextending;

import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-23 13:54
 */
@Getter
public class ObjectExtensionManager {
    public static ObjectExtensionManager Instance = new ObjectExtensionManager();
    private ConcurrentHashMap<Object, Object> configuration;
    private ConcurrentHashMap<Type, ObjectExtensionInfo> objectsExtensions;

    protected ObjectExtensionManager() {
        this.objectsExtensions = new ConcurrentHashMap<>();
        this.configuration = new ConcurrentHashMap<>();
    }

    public ObjectExtensionManager addOrUpdate(Class<?>[] types, Consumer<ObjectExtensionInfo> configureAction) {
        for (Class<?> type : types) {
            this.addOrUpdate(type, configureAction);
        }
        return this;
    }

    public ObjectExtensionManager addOrUpdate(Class<?> type, Consumer<ObjectExtensionInfo> configureAction) {
        ObjectExtensionInfo extensionInfo = this.objectsExtensions.get(type);
        if (extensionInfo == null) {
            extensionInfo = new ObjectExtensionInfo(type);
            this.objectsExtensions.put(type, extensionInfo);
        }
        if (configureAction != null) {
            configureAction.accept(extensionInfo);
        }
        return this;
    }

    public ObjectExtensionInfo getOrNull(Type type) {
        return this.objectsExtensions.getOrDefault(type, null);
    }

    public List<ObjectExtensionInfo> getExtendedObjects() {
        return new ArrayList<>(this.objectsExtensions.values());
    }

    @NonNull
    public ObjectExtensionManager addOrUpdateProperty(
            @NonNull Class<?>[] objectTypes,
            @NonNull Class<?> propertyType,
            @NonNull String propertyName,
            @Nullable Consumer<ObjectExtensionPropertyInfo> configureAction) {
        Objects.requireNonNull(objectTypes);
        for (Class<?> objectType : objectTypes) {
            addOrUpdateProperty(
                    objectType,
                    propertyType,
                    propertyName,
                    configureAction
            );
        }
        return this;
    }

    @NonNull
    public ObjectExtensionManager addOrUpdateProperty(
            @NonNull Class<?> objectType,
            @NonNull Class<?> propertyType,
            @NonNull String propertyName,
            @Nullable Consumer<ObjectExtensionPropertyInfo> configureAction) {

        return addOrUpdate(objectType,
                options -> options.addOrUpdateProperty(propertyType, propertyName, configureAction)
        );
    }

    public ObjectExtensionPropertyInfo getPropertyOrNull(
            @NonNull Class<?> objectType,
            @NonNull String propertyName) {
        Objects.requireNonNull(objectType);
        Objects.requireNonNull(propertyName);

        return Optional.ofNullable(getOrNull(objectType))
                .map(info -> info.getPropertyOrNull(propertyName))
                .orElse(null);
    }

    public Collection<ObjectExtensionPropertyInfo> getProperties(
            @NonNull Class<?> objectType) {
        Objects.requireNonNull(objectType);

        ObjectExtensionInfo extensionInfo = getOrNull(objectType);
        if (extensionInfo == null) {
            return Collections.emptyList();
        }

        return extensionInfo.getProperties();
    }

}
