package com.ueueo.modularity.plugins;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-24 13:57
 */
public interface IPlugInSource {

    List<Class<?>> getModules();

}
