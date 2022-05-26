package com.ueueo.users;

import com.ueueo.ID;
import com.ueueo.claims.Claim;
import com.ueueo.security.claims.AbpClaimTypes;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * @author Lee
 * @date 2022-05-26 19:52
 */
public class CurrentUserExtensions {
    public static ID findImpersonatorTenantId(ICurrentUser currentUser) {
        return findClaimIDValue(currentUser, AbpClaimTypes.ImpersonatorTenantId);
    }

    public static ID findImpersonatorUserId(ICurrentUser currentUser) {
        return findClaimIDValue(currentUser, AbpClaimTypes.ImpersonatorUserId);
    }

    public static String findImpersonatorTenantName(ICurrentUser currentUser) {
        return findClaimValue(currentUser, AbpClaimTypes.ImpersonatorTenantName);
    }

    public static String findImpersonatorUserName(ICurrentUser currentUser) {
        return findClaimValue(currentUser, AbpClaimTypes.ImpersonatorUserName);
    }

    @Nullable
    public static String findClaimValue(ICurrentUser currentUser, String claimType) {
        return Optional.ofNullable(currentUser.findClaim(claimType)).map(Claim::getValue).orElse(null);
    }

    @Nullable
    public static ID findClaimIDValue(ICurrentUser currentUser, String claimType) {
        return Optional.ofNullable(currentUser.findClaim(claimType)).map(claim -> ID.valueOf(claim.getValue())).orElse(null);
    }
}
