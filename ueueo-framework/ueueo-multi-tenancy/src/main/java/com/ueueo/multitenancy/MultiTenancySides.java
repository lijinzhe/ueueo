package com.ueueo.multitenancy;

import com.ueueo.claims.ClaimsIdentity;
import com.ueueo.principal.ClaimsPrincipal;
import org.springframework.lang.NonNull;

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

    public static MultiTenancySides getMultiTenancySide(@NonNull ClaimsIdentity identity) {
        Long tenantId = identity.findTenantId();
        return tenantId != null
                ? MultiTenancySides.Tenant
                : MultiTenancySides.Host;
    }

    public static MultiTenancySides getMultiTenancySide(@NonNull ClaimsPrincipal principal) {
        Long tenantId = principal.findTenantId();
        return tenantId != null
                ? MultiTenancySides.Tenant
                : MultiTenancySides.Host;
    }
}
