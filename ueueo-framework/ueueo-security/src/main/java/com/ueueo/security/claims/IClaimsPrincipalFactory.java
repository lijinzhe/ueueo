package com.ueueo.security.claims;

import com.ueueo.security.principal.ClaimsPrincipal;

/**
 * @author Lee
 * @date 2021-08-26 21:02
 */
public interface IClaimsPrincipalFactory {

    ClaimsPrincipal create(ClaimsPrincipal existsClaimsPrincipal);

}
