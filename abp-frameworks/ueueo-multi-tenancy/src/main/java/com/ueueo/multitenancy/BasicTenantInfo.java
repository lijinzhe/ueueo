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
    private final ID tenantId;
    /**
     * Name of the tenant if {@link #tenantId} is not null.
     */
    @Nullable
    private final String name;

    public BasicTenantInfo(@Nullable ID tenantId, @Nullable String name) {
        this.tenantId = tenantId;
        this.name = name;
    }
}
