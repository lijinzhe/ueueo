package com.ueueo.multitenancy;

/**
 * 多租户系统数据库模式
 *
 * @author Lee
 * @date 2022-05-13 22:18
 */
public enum MultiTenancyDatabaseStyle {
    /** 共享数据库模式 */
    Shared(1),
    /** 每个组糊独立数据库模式 */
    PerTenant(2),
    /** 混合模式 */
    Hybrid(1 | 2);

    private final int flag;

    MultiTenancyDatabaseStyle(int flag) {
        this.flag = flag;
    }

    public boolean hasFlag(MultiTenancyDatabaseStyle style) {
        return (this.flag & style.flag) > 0;
    }
}
