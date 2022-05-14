package com.ueueo.multitenancy;

/**
 *
 * @author Lee
 * @date 2022-05-13 22:18
 */
public enum MultiTenancyDatabaseStyle {
    Shared(1),
    PerTenant(2),
    Hybrid(1 | 2);

    private int flag;

    MultiTenancyDatabaseStyle(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }
}
