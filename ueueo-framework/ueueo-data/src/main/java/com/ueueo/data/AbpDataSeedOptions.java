package com.ueueo.data;

import lombok.Getter;

/**
 * @author Lee
 * @date 2022-05-29 14:50
 */
public class AbpDataSeedOptions {
    @Getter
    private final DataSeedContributorList Contributors;

    public AbpDataSeedOptions() {
        Contributors = new DataSeedContributorList();
    }
}
