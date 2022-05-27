package com.ueueo.modularity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lee
 * @date 2022-05-27 09:18
 */
public class AbpModuleLifecycleOptions {
    @Getter
    private final List<Class<? extends IModuleLifecycleContributor>> contributors;

    public AbpModuleLifecycleOptions() {
        contributors = new ArrayList<>();
    }
}
