package com.ueueo.modularity;

import java.util.List;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-24 13:49
 */
public interface IAbpModuleDescriptor {

    Class<?> getType();

    IAbpModule getInstance();

    Boolean isLoadedAsPlugIn();

    List<IAbpModuleDescriptor> getDependencies();

    ClassLoader getClassLoader();
}
