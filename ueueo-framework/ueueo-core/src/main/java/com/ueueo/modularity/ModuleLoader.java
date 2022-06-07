package com.ueueo.modularity;

import com.ueueo.AbpException;
import com.ueueo.collections.ListExtensions;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2021-08-24 14:17
 */
public class ModuleLoader implements IModuleLoader {

    @Override
    public List<IAbpModuleDescriptor> loadModules(ApplicationContext applicationContext, Class<?> startupModuleType) {
        Objects.requireNonNull(applicationContext);
        Objects.requireNonNull(startupModuleType);

        List<IAbpModuleDescriptor> modules = getDescriptors(applicationContext, startupModuleType);
        modules = SortByDependency(modules, startupModuleType);
        return modules;
    }

    private List<IAbpModuleDescriptor> getDescriptors(ApplicationContext applicationContext, Class<?> startupModuleType) {
        List<AbpModuleDescriptor> modules = new ArrayList<>();

        fillModules(modules, applicationContext, startupModuleType);
        setDependencies(modules);

        return modules.stream().map(abpModuleDescriptor -> (IAbpModuleDescriptor) abpModuleDescriptor).collect(Collectors.toList());
    }

    protected void fillModules(List<AbpModuleDescriptor> modules, ApplicationContext applicationContext,
                               Class<?> startupModuleType) {
        //All modules starting from the startup module
        for (Class<?> moduleType : AbpModuleHelper.findAllModuleTypes(startupModuleType)) {
            modules.add(createModuleDescriptor(applicationContext, moduleType, false));
        }
    }

    protected void setDependencies(List<AbpModuleDescriptor> modules) {
        for (AbpModuleDescriptor module : modules) {
            setDependencies(modules, module);
        }
    }

    protected List<IAbpModuleDescriptor> SortByDependency(List<IAbpModuleDescriptor> modules, Class<?> startupModuleType) {
        List<IAbpModuleDescriptor> sortedModules = ListExtensions.sortByDependencies(modules, IAbpModuleDescriptor::getDependencies);
        ListExtensions.moveItem(sortedModules, m -> m.getType().equals(startupModuleType), modules.size() - 1);
        return sortedModules;
    }

    protected AbpModuleDescriptor createModuleDescriptor(ApplicationContext applicationContext, Class<?> moduleType, Boolean isLoadedAsPlugIn) {
        return new AbpModuleDescriptor(moduleType, createAndRegisterModule(applicationContext, moduleType), isLoadedAsPlugIn);
    }

    protected IAbpModule createAndRegisterModule(ApplicationContext applicationContext, Class<?> moduleType) {
        IAbpModule module = null;
        try {
            module = (IAbpModule) moduleType.newInstance();
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
            beanFactory.registerSingleton(moduleType.getName(), module);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return module;
    }

    protected void setDependencies(List<AbpModuleDescriptor> modules, AbpModuleDescriptor module) {
        for (Class<?> dependedModuleType : AbpModuleHelper.findDependedModuleTypes(module.getType())) {
            AbpModuleDescriptor dependedModule = modules.stream().filter(m -> m.getType().equals(dependedModuleType)).findFirst().orElse(null);
            if (dependedModule == null) {
                throw new AbpException("Could not find a depended module " + dependedModuleType.getName() + " for " + module.getType().getName());
            }
            module.addDependency(dependedModule);
        }
    }

}
