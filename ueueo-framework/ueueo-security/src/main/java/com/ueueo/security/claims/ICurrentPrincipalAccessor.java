package com.ueueo.security.claims;

import com.ueueo.principal.ClaimsPrincipal;

/**
 * @author Lee
 * @date 2021-08-26 21:06
 */
public interface ICurrentPrincipalAccessor {
    ClaimsPrincipal getPrincipal();

    void change(ClaimsPrincipal principal);
}
