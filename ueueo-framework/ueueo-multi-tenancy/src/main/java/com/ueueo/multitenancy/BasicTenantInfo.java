package com.ueueo.multitenancy;

import com.ueueo.ID;
import lombok.Getter;
import org.springframework.lang.Nullable;

/**
 * @author Lee
 * @date 2022-05-13 20:49
 */
@Getter
public class BasicTenantInfo {
    /**
     * Null indicates the host.
     * Not null value for a tenant.
     */
    @Nullable
    private ID tenantId;
    /**
     * Name of the tenant if {@link #tenantId} is not null.
     */
    @Nullable
    private String name;

    public BasicTenantInfo(ID tenantId, String name) {
        this.tenantId = tenantId;
        this.name = name;
    }
}
