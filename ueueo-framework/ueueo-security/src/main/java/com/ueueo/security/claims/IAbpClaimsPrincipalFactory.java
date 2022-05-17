package com.ueueo.security.claims;

import com.ueueo.principal.ClaimsPrincipal;
import com.ueueo.security.threading.SecurityAsyncTaskExecutor;

import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2021-08-26 21:02
 */
public interface IAbpClaimsPrincipalFactory {

    ClaimsPrincipal create(ClaimsPrincipal existsClaimsPrincipal);

    default Future<ClaimsPrincipal> createAsync(ClaimsPrincipal existsClaimsPrincipal) {
        return SecurityAsyncTaskExecutor.INSTANCE.submit(() -> create(existsClaimsPrincipal));
    }
}
