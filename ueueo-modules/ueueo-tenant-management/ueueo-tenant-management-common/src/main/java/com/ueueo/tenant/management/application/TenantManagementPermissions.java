package com.ueueo.tenant.management.application;

/**
 * @author Lee
 * @date 2022-05-20 11:20
 */
public final class TenantManagementPermissions {
    public static final String GroupName = "AbpTenantManagement";

    public static final class Tenants {
        public static final String Default = GroupName + ".Tenants";
        public static final String Create = Default + ".Create";
        public static final String Update = Default + ".Update";
        public static final String Delete = Default + ".Delete";
        public static final String ManageFeatures = Default + ".ManageFeatures";
        public static final String ManageConnectionStrings = Default + ".ManageConnectionStrings";
    }

}
