package com.ueueo.multitenancy;

/**
 *
 * @author Lee
 * @date 2022-05-13 20:54
 */
public interface ICurrentTenantAccessor {
    BasicTenantInfo getCurrent();

    void setCurrent(BasicTenantInfo current);
}
