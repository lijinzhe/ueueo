package com.ueueo.eventbus;

import com.ueueo.ID;

/**
 * An event data object (or event transfer object) can implement this interface
 * to indicate that this event may be related to a tenant.
 *
 * If an event data class is always related to a tenant, then directly implement the
 * <see cref="IsMultiTenant"/> interface instead of this one.
 *
 * This interface is typically implemented by generic event handlers where the generic
 * parameter may implement <see cref="IsMultiTenant"/> or not.
 *
 * @author Lee
 * @date 2022-05-20 14:24
 */
public interface IEventDataMayHaveTenantId {
    /**
     * Returns true if this event data has a Tenant Id information.
     * If so, it should set the <paramref name="tenantId"/> our parameter.
     * Otherwise, the <paramref name="tenantId"/> our parameter value should not be informative
     * (it will be null as expected, but doesn't indicate a tenant with null tenant id).
     *
     * @return
     */
    boolean isMultiTenant();

    /**
     * The tenant id that is set if this method returns true.
     *
     * @return
     */
    ID getTenantId();
}
