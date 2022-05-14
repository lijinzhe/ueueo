package com.ueueo.multitenancy;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lee
 * @date 2022-05-13 21:31
 */
public class TenantResolveResult {
    private String tenantIdOrName;
    private List<String> appliedResolvers;

    public TenantResolveResult() {
        this.appliedResolvers = new ArrayList<>();
    }

    public String getTenantIdOrName() {
        return tenantIdOrName;
    }

    public List<String> getAppliedResolvers() {
        return appliedResolvers;
    }

    public void setTenantIdOrName(String tenantIdOrName) {
        this.tenantIdOrName = tenantIdOrName;
    }
}
