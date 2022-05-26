package com.ueueo.modularity;

import com.ueueo.modularity.plugins.PlugInSourceList;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-24 13:55
 */
public interface IModuleLoader {
    List<IAbpModuleDescriptor> loadModules(List<Class<?>> services, Class<?> startupModuleType, PlugInSourceList plugInSources);
}
