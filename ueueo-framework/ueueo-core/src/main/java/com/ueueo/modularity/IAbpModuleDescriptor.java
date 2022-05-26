package com.ueueo.modularity;

import java.util.List;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-24 13:49
 */
public interface IAbpModuleDescriptor {

    Class<?> type();

    IAbpModule instance();

    Boolean isLoadedAsPlugIn();

    List<IAbpModuleDescriptor> dependencies();

}
