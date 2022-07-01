package com.ueueo.security.claims;

import com.ueueo.disposable.DisposeAction;
import com.ueueo.disposable.IDisposable;
import com.ueueo.security.principal.ClaimsPrincipal;

/**
 * @author Lee
 * @date 2021-08-26 21:07
 */
public abstract class CurrentPrincipalAccessorBase implements ICurrentPrincipalAccessor {

    private final ThreadLocal<ClaimsPrincipal> currentPrincipal = new InheritableThreadLocal<>();

    protected abstract ClaimsPrincipal getClaimsPrincipal();

    @Override
    public ClaimsPrincipal getCurrentPrincipal() {
        ClaimsPrincipal principal = this.currentPrincipal.get();
        if (principal == null) {
            principal = getClaimsPrincipal();
            setCurrent(principal);
        }
        return principal;
    }

    @Override
    public IDisposable change(ClaimsPrincipal principal) {
        return setCurrent(principal);
    }

    private IDisposable setCurrent(ClaimsPrincipal principal) {
        ClaimsPrincipal parent = currentPrincipal.get() != null ? currentPrincipal.get() : getClaimsPrincipal();
        this.currentPrincipal.set(principal);
        return new DisposeAction(() -> this.currentPrincipal.set(parent));
    }
}
