package com.ueueo.security.claims;

import com.ueueo.principal.ClaimsPrincipal;

/**
 * @author Lee
 * @date 2021-08-26 21:02
 */
public interface IAbpClaimsPrincipalFactory {

    ClaimsPrincipal create(ClaimsPrincipal existsClaimsPrincipal);

}
