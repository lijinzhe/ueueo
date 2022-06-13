package com.ueueo.data;

import com.ueueo.ID;

/**
 *
 * @author Lee
 * @date 2022-05-29 14:58
 */
public class DataSeederExtensions {
    public static void seed(IDataSeeder seeder, ID tenantId) {
        seeder.seed(new DataSeedContext(tenantId));
    }
}
