package com.ueueo.security.claims;

import com.ueueo.principal.ClaimsPrincipal;

/**
 * @author Lee
 * @date 2021-08-26 21:07
 */
public abstract class CurrentPrincipalAccessorBase implements ICurrentPrincipalAccessor {

    private ThreadLocal<ClaimsPrincipal> currentPrincipal = new InheritableThreadLocal<>();

    protected abstract ClaimsPrincipal getClaimsPrincipal();

    @Override
    public ClaimsPrincipal getPrincipal() {
        ClaimsPrincipal principal = currentPrincipal.get();
        if (principal == null) {
            principal = getClaimsPrincipal();
            setCurrent(principal);
        }
        return principal;
    }

    @Override
    public void change(ClaimsPrincipal principal) {
        setCurrent(principal);
    }

    private void setCurrent(ClaimsPrincipal principal) {
        currentPrincipal.set(principal);
    }
}
