package com.ueueo.security.claims;

import com.ueueo.principal.ClaimsPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Lee
 * @date 2022-05-27 17:39
 */
public class SpringSecurityCurrentPrincipalAccessor implements ICurrentPrincipalAccessor {

    @Override
    public ClaimsPrincipal getPrincipal() {
        return (ClaimsPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public void change(ClaimsPrincipal principal) {

    }

}
