package com.ueueo.modularity;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-24 14:20
 */
public class AbpModuleDescriptor implements IAbpModuleDescriptor {
    private Class<?> type;
    private IAbpModule instance;
    private Boolean isLoadedAsPlugIn;
    private List<IAbpModuleDescriptor> dependencies;

    public AbpModuleDescriptor(Class<?> type, IAbpModule instance, Boolean isLoadedAsPlugIn) {
        if (!type.isAssignableFrom(instance.getClass())) {
            throw new IllegalArgumentException("Given module instance ({instance.GetType().AssemblyQualifiedName}) is not an instance of given module type: {type.AssemblyQualifiedName}");
        }
        this.type = type;
        this.instance = instance;
        this.isLoadedAsPlugIn = isLoadedAsPlugIn;
        this.dependencies = new ArrayList<>();
    }

    @Override
    public Class<?> type() {
        return type;
    }

    @Override
    public IAbpModule instance() {
        return instance;
    }

    @Override
    public Boolean isLoadedAsPlugIn() {
        return isLoadedAsPlugIn;
    }

    @Override
    public List<IAbpModuleDescriptor> dependencies() {
        return dependencies;
    }

    public void addDependency(IAbpModuleDescriptor descriptor) {
        if (!dependencies.contains(descriptor)) {
            dependencies.add(descriptor);
        }
    }
}
