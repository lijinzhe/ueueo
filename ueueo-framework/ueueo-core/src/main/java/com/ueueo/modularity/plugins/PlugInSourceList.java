package com.ueueo.modularity.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2021-08-24 13:57
 */
public class PlugInSourceList extends ArrayList<IPlugInSource> {

    public List<Class<?>> getAllModules() {
        return this.stream().flatMap(pluginSource -> PlugInSourceExtensions.getModulesWithAllDependencies(pluginSource).stream())
                .distinct()
                .collect(Collectors.toList());

    }
}
