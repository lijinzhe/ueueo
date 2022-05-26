package com.ueueo.modularity;

import com.weiming.framework.core.AbpException;
import com.weiming.framework.core.modularity.plugins.PlugInSourceList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-24 14:17
 */
public class ModuleLoader implements IModuleLoader {
    @Override
    public List<IAbpModuleDescriptor> loadModules(List<Class<?>> services, Class<?> startupModuleType, PlugInSourceList plugInSources) {
        Objects.requireNonNull(services);
        Objects.requireNonNull(startupModuleType);
        Objects.requireNonNull(plugInSources);
        List<IAbpModuleDescriptor> modules = getDescriptors(services, startupModuleType, plugInSources);
        modules = SortByDependency(modules, startupModuleType);
        return modules;
    }

    private List<IAbpModuleDescriptor> getDescriptors(
            List<Class<?>> services,
            Class<?> startupModuleType,
            PlugInSourceList plugInSources) {
        List<AbpModuleDescriptor> modules = new ArrayList<>();

        fillModules(modules, services, startupModuleType, plugInSources);
        setDependencies(modules);
        return modules.stream().map(abpModuleDescriptor -> (IAbpModuleDescriptor) abpModuleDescriptor).collect(Collectors.toList());
    }

    protected void fillModules(
            List<AbpModuleDescriptor> modules,
            List<Class<?>> services,
            Class<?> startupModuleType,
            PlugInSourceList plugInSources) {
        //        var logger = services.GetInitLogger < AbpApplicationBase > ();

        //All modules starting from the startup module
        for (Class<?> moduleType : AbpModuleHelper.findAllModuleTypes(startupModuleType)) {
            modules.add(createModuleDescriptor(services, moduleType, false));
        }

        //Plugin modules
        for (Class<?> moduleType : plugInSources.getAllModules()) {
            if (modules.stream().anyMatch(m -> m.type().equals(moduleType))) {
                continue;
            }
            modules.add(createModuleDescriptor(services, moduleType, true));
        }
    }

    protected void setDependencies(List<AbpModuleDescriptor> modules) {
        for (AbpModuleDescriptor module : modules) {
            SetDependencies(modules, module);
        }
    }

    protected List<IAbpModuleDescriptor> SortByDependency(List<IAbpModuleDescriptor> modules, Class<?> startupModuleType) {
        List<IAbpModuleDescriptor> sortedModules = null;
        //TODO by Lee on 2021-08-24 14:41 调整依赖顺序
        //                sortedModules = modules.SortByDependencies(m = > m.Dependencies);
        //        sortedModules.MoveItem(m = > m.Type == startupModuleType, modules.Count - 1);
        return sortedModules;
    }

    protected AbpModuleDescriptor createModuleDescriptor(List<Class<?>> services, Class<?> moduleType, Boolean isLoadedAsPlugIn) {
        return new AbpModuleDescriptor(moduleType, CreateAndRegisterModule(services, moduleType), isLoadedAsPlugIn);
    }

    protected IAbpModule CreateAndRegisterModule(List<Class<?>> services, Class<?> moduleType) {
        IAbpModule module = null;
        try {
            module = (IAbpModule) moduleType.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //TODO by Lee on 2021-08-24 14:40 注入到Spring Bean 容器中
        //        var module = (IAbpModule) Activator.CreateInstance(moduleType);
        //        services.AddSingleton(moduleType, module);
        return module;
    }

    protected void SetDependencies(List<AbpModuleDescriptor> modules, AbpModuleDescriptor module) {
        for (Class<?> dependedModuleType : AbpModuleHelper.findDependedModuleTypes(module.type())) {
            AbpModuleDescriptor dependedModule = modules.stream().filter(m -> m.type().equals(dependedModuleType)).findFirst().orElse(null);
            if (dependedModule == null) {
                throw new AbpException("Could not find a depended module " + dependedModuleType.getName() + " for " + module.type().getName());
            }
            module.addDependency(dependedModule);
        }
    }
}
