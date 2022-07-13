package com.ueueo.security.claims;

import com.ueueo.security.principal.ClaimsPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Lee
 * @date 2022-05-27 17:39
 */
public class SpringSecurityCurrentPrincipalAccessor extends CurrentPrincipalAccessorBase {

    @Override
    protected ClaimsPrincipal getClaimsPrincipal() {
        return (ClaimsPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
