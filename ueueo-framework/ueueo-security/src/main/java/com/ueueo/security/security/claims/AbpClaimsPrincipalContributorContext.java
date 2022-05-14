package com.ueueo.security.security.claims;

import java.security.Principal;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 21:01
 */
public class AbpClaimsPrincipalContributorContext {
    private Principal principal;

    public Principal getPrincipal() {
        return principal;
    }

    public AbpClaimsPrincipalContributorContext(Principal principal) {
        this.principal = principal;
    }
}
