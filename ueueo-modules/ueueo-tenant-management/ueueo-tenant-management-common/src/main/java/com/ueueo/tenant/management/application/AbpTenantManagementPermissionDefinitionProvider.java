package com.ueueo.tenant.management.application;

import com.ueueo.authorization.abstractions.permissions.IPermissionDefinitionContext;
import com.ueueo.authorization.abstractions.permissions.PermissionDefinition;
import com.ueueo.authorization.abstractions.permissions.PermissionDefinitionProvider;
import com.ueueo.authorization.abstractions.permissions.PermissionGroupDefinition;
import com.ueueo.localization.LocalizableString;
import com.ueueo.multitenancy.MultiTenancySides;
import com.ueueo.tenant.management.localization.AbpTenantManagementResource;

/**
 * @author Lee
 * @date 2022-05-20 11:19
 */
public class AbpTenantManagementPermissionDefinitionProvider extends PermissionDefinitionProvider {
    @Override
    public void define(IPermissionDefinitionContext context) {
        PermissionGroupDefinition tenantManagementGroup = context.addGroup(TenantManagementPermissions.GROUP_NAME, L("Permission:TenantManagement"));
        PermissionDefinition tenantsPermission = tenantManagementGroup.addPermission(TenantManagementPermissions.Tenants.DEFAULT, L("Permission:TenantManagement"), MultiTenancySides.Host, null);
        tenantsPermission.addChild(TenantManagementPermissions.Tenants.CREATE, L("Permission:Create"), MultiTenancySides.Host, null);
        tenantsPermission.addChild(TenantManagementPermissions.Tenants.UPDATE, L("Permission:Edit"), MultiTenancySides.Host, null);
        tenantsPermission.addChild(TenantManagementPermissions.Tenants.DELETE, L("Permission:Delete"), MultiTenancySides.Host, null);
        tenantsPermission.addChild(TenantManagementPermissions.Tenants.MANAGE_FEATURES, L("Permission:ManageFeatures"), MultiTenancySides.Host, null);
        tenantsPermission.addChild(TenantManagementPermissions.Tenants.MANAGE_CONNECTION_STRINGS, L("Permission:ManageConnectionStrings"), MultiTenancySides.Host, null);
    }

    private LocalizableString L(String name) {
        return LocalizableString.create(AbpTenantManagementResource.class, name);
    }
}
