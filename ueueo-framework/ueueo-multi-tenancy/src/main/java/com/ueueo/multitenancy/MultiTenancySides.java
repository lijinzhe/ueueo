package com.ueueo.multitenancy;

/**
 * Represents sides in a multi tenancy application.
 *
 * @author Lee
 * @date 2022-05-13 22:18
 */
public enum MultiTenancySides {
    /** Tenant side. */
    Tenant(1),
    /** Host side. */
    Host(2),
    /** Both sides */
    Both(1 | 2);

    private int flag;

    MultiTenancySides(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }
}
