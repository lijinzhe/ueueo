package com.ueueo.data.objectextending;

import lombok.Getter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
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

    public ObjectExtensionManager addOrUpdate(Type[] types, Consumer<ObjectExtensionInfo> configureAction) {
        for (Type type : types) {
            this.addOrUpdate(type, configureAction);
        }
        return this;
    }

    public ObjectExtensionManager addOrUpdate(Type type, Consumer<ObjectExtensionInfo> configureAction) {
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
}
