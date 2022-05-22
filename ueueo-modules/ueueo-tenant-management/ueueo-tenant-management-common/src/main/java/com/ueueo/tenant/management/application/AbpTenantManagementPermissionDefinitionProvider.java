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
        PermissionGroupDefinition tenantManagementGroup = context.addGroup(TenantManagementPermissions.GroupName, L("Permission:TenantManagement"));
        PermissionDefinition tenantsPermission = tenantManagementGroup.addPermission(TenantManagementPermissions.Tenants.Default, L("Permission:TenantManagement"), MultiTenancySides.Host, null);
        tenantsPermission.addChild(TenantManagementPermissions.Tenants.Create, L("Permission:Create"), MultiTenancySides.Host, null);
        tenantsPermission.addChild(TenantManagementPermissions.Tenants.Update, L("Permission:Edit"), MultiTenancySides.Host, null);
        tenantsPermission.addChild(TenantManagementPermissions.Tenants.Delete, L("Permission:Delete"), MultiTenancySides.Host, null);
        tenantsPermission.addChild(TenantManagementPermissions.Tenants.ManageFeatures, L("Permission:ManageFeatures"), MultiTenancySides.Host, null);
        tenantsPermission.addChild(TenantManagementPermissions.Tenants.ManageConnectionStrings, L("Permission:ManageConnectionStrings"), MultiTenancySides.Host, null);
    }

    private LocalizableString L(String name) {
        return LocalizableString.create(AbpTenantManagementResource.class, name);
    }
}
