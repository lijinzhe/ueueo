package com.ueueo.modularity;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-24 13:54
 */
public interface IModuleContainer {
    List<IAbpModuleDescriptor> getModules();
}
