package com.ueueo.security.claims;

import com.ueueo.security.threading.SecurityAsyncTaskExecutor;

import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2022-05-16 22:33
 */
public interface IAbpClaimsPrincipalContributor {

    void contribute(AbpClaimsPrincipalContributorContext context);

    default Future<?> contributeAsync(AbpClaimsPrincipalContributorContext context) {
        return SecurityAsyncTaskExecutor.INSTANCE.submit(() -> contribute(context));
    }
}
