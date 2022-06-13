package com.ueueo.data;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lee
 * @date 2022-05-29 14:50
 */
public class AbpDataSeedOptions {
    @Getter
    private final List<Class<? extends IDataSeedContributor>> Contributors;

    public AbpDataSeedOptions() {
        Contributors = new ArrayList<>();
    }
}
