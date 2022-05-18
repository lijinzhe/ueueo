package com.ueueo.security.claims;

/**
 * @author Lee
 * @date 2022-05-16 22:33
 */
public interface IAbpClaimsPrincipalContributor {

    void contribute(AbpClaimsPrincipalContributorContext context);

}
