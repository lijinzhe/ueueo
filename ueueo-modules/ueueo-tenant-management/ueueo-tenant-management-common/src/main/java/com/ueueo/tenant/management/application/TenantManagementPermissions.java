package com.ueueo.tenant.management.application;

/**
 * @author Lee
 * @date 2022-05-20 11:20
 */
public final class TenantManagementPermissions {
    public static final String GROUP_NAME = "AbpTenantManagement";

    public static final class Tenants {
        public static final String DEFAULT = GROUP_NAME + ".Tenants";
        public static final String CREATE = DEFAULT + ".Create";
        public static final String UPDATE = DEFAULT + ".Update";
        public static final String DELETE = DEFAULT + ".Delete";
        public static final String MANAGE_FEATURES = DEFAULT + ".ManageFeatures";
        public static final String MANAGE_CONNECTION_STRINGS = DEFAULT + ".ManageConnectionStrings";
    }

}
