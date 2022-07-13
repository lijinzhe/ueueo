package com.ueueo.security.claims;

/**
 * @author Lee
 * @date 2022-05-16 22:33
 */
public interface IClaimsPrincipalContributor {

    void contribute(ClaimsPrincipalContributorContext context);

}
