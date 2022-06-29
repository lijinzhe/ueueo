package com.ueueo.multitenancy;

/**
 * @author Lee
 * @date 2022-05-14 17:28
 */
public class AsyncLocalCurrentTenantAccessor implements ICurrentTenantAccessor {

    public final static AsyncLocalCurrentTenantAccessor INSTANCE = new AsyncLocalCurrentTenantAccessor();

    private final ThreadLocal<BasicTenantInfo> currentScope;

    public AsyncLocalCurrentTenantAccessor() {
        this.currentScope = new InheritableThreadLocal<>();
    }

    @Override
    public BasicTenantInfo getCurrent() {
        return currentScope.get();
    }

    @Override
    public void setCurrent(BasicTenantInfo current) {
        currentScope.set(current);
    }
}
