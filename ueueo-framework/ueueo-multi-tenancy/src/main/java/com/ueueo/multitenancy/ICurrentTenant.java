package com.ueueo.multitenancy;

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
    Integer getId();

    /**
     * 租户名称
     *
     * @return
     */
    String getName();

    void change(Integer id, String name);
}
