package com.ueueo.modularity.plugins;

import com.ueueo.modularity.AbpModuleHelper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2021-08-24 14:00
 */
public class PlugInSourceExtensions {

    public static List<Class<?>> getModulesWithAllDependencies(IPlugInSource plugInSource) {
        Objects.requireNonNull(plugInSource);
        return plugInSource.getModules()
                .stream().flatMap(type -> AbpModuleHelper.findAllModuleTypes(type).stream())
                .distinct()
                .collect(Collectors.toList());
    }
}
