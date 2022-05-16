package com.ueueo.security.claims;

import com.ueueo.principal.ClaimsPrincipal;
import lombok.Getter;

/**
 * TODO
 *
 * @author Lee
 * @date 2021-08-26 21:01
 */
public class AbpClaimsPrincipalContributorContext {
    @Getter
    private final ClaimsPrincipal claimsPrincipal;

    public AbpClaimsPrincipalContributorContext(ClaimsPrincipal claimsPrincipal) {
        this.claimsPrincipal = claimsPrincipal;
    }
}
