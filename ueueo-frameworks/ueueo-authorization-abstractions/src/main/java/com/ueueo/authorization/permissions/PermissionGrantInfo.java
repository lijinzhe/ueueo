package com.ueueo.authorization.permissions;

import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 19:56
 */
@Getter
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

}
