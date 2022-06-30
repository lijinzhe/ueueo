package com.ueueo.multitenancy;

import com.ueueo.ID;
import com.ueueo.disposable.IDisposable;

/**
 * 当前租户接口
 *
 * @author Lee
 * @date 2022-05-13 20:35
 */
public interface ICurrentTenant {
    /**
     * 是否有效
     *
     * @return
     */
    Boolean getIsAvailable();

    /**
     * 租户ID
     *
     * @return
     */
    ID getId();

    /**
     * 租户名称
     *
     * @return
     */
    String getName();

    IDisposable change(ID id, String name);

    default MultiTenancySides getMultiTenancySide() {
        return getId() != null ? MultiTenancySides.Tenant : MultiTenancySides.Host;
    }
}
