package com.ueueo.security.claims;

import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2022-05-16 22:33
 */
public interface IAbpClaimsPrincipalContributor {

    Future<?> contributeAsync(AbpClaimsPrincipalContributorContext context);
}
