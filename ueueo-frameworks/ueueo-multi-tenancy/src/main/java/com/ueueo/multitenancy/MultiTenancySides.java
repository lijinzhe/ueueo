package com.ueueo.multitenancy;

import com.ueueo.ID;
import com.ueueo.security.claims.ClaimsIdentity;
import com.ueueo.security.principal.ClaimsPrincipal;
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

    private final int flag;

    MultiTenancySides(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public static MultiTenancySides getMultiTenancySide(@NonNull ClaimsIdentity identity) {
        ID tenantId = identity.findTenantId();
        return tenantId != null
                ? MultiTenancySides.Tenant
                : MultiTenancySides.Host;
    }

    public static MultiTenancySides getMultiTenancySide(@NonNull ClaimsPrincipal principal) {
        ID tenantId = principal.findTenantId();
        return tenantId != null
                ? MultiTenancySides.Tenant
                : MultiTenancySides.Host;
    }

    public boolean hasFlag(MultiTenancySides tenancySides) {
        return (this.flag & tenancySides.flag) > 0;
    }
}
