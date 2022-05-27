package com.ueueo.modularity;

import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lee
 * @date 2021-08-24 14:20
 */
public class AbpModuleDescriptor implements IAbpModuleDescriptor {
    private final Class<?> type;
    private final IAbpModule instance;
    private final Boolean isLoadedAsPlugIn;
    private final List<IAbpModuleDescriptor> dependencies;
    private final ClassLoader classLoader;

    public AbpModuleDescriptor(@NonNull Class<?> type, @NonNull IAbpModule instance, Boolean isLoadedAsPlugIn) {
        if (!type.isAssignableFrom(instance.getClass())) {
            throw new IllegalArgumentException(String.format("Given module instance instance.GetType().AssemblyQualifiedName is not an instance of given module type: %s",
                    type.getName()));
        }
        this.type = type;
        this.classLoader = type.getClassLoader();
        this.instance = instance;
        this.isLoadedAsPlugIn = isLoadedAsPlugIn;
        this.dependencies = new ArrayList<>();
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public IAbpModule getInstance() {
        return instance;
    }

    @Override
    public Boolean isLoadedAsPlugIn() {
        return isLoadedAsPlugIn;
    }

    @Override
    public List<IAbpModuleDescriptor> getDependencies() {
        return dependencies;
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void addDependency(IAbpModuleDescriptor descriptor) {
        if (!dependencies.contains(descriptor)) {
            dependencies.add(descriptor);
        }
    }

    @Override
    public String toString() {
        return "AbpModuleDescriptor " + getType().getName();
    }
}
