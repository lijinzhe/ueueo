package com.ueueo.multitenancy;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-14 17:32
 */
public class TenantResolveContext implements ITenantResolveContext{

    private boolean handled;
    private String tenantIdOrName;

    @Override
    public String getTenantIdOrName() {
        return tenantIdOrName;
    }

    @Override
    public void setTenantIdOrName(String tenantIdOrName) {
        this.tenantIdOrName = tenantIdOrName;
    }

    @Override
    public boolean handled() {
        return handled;
    }

    @Override
    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public boolean hasResolvedTenantOrHost(){
        return handled || tenantIdOrName != null;
    }
}
