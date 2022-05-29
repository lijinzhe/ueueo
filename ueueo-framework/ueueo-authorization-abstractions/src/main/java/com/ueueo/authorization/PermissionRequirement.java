package com.ueueo.authorization;

import lombok.Data;

/**
 * @author Lee
 * @date 2022-05-29 14:15
 */
@Data
public class PermissionRequirement {
    private String permissionName;

    public PermissionRequirement(String permissionName) {
        this.permissionName = permissionName;
    }
}
