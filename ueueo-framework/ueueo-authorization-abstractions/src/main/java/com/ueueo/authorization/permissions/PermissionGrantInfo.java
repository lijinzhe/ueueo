package com.ueueo.authorization.permissions;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 19:56
 */
public class PermissionGrantInfo {

    private String name;
    private Boolean isGranted;
    private String providerName;
    private String providerKey;

    public PermissionGrantInfo(@NonNull String name, Boolean isGranted, @Nullable String providerName, @Nullable String providerKey) {
        this.name = name;
        this.isGranted = isGranted;
        this.providerName = providerName;
        this.providerKey = providerKey;
    }

    public String getName() {
        return name;
    }

    public Boolean getGranted() {
        return isGranted;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getProviderKey() {
        return providerKey;
    }
}
