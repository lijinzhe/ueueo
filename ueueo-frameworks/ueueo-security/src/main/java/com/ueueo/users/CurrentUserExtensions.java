package com.ueueo.users;

import com.ueueo.ID;
import com.ueueo.security.claims.Claim;
import com.ueueo.security.claims.ClaimTypes;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * @author Lee
 * @date 2022-05-26 19:52
 */
public class CurrentUserExtensions {
    public static ID findImpersonatorTenantId(ICurrentUser currentUser) {
        return findClaimIDValue(currentUser, ClaimTypes.ImpersonatorTenantId);
    }

    public static ID findImpersonatorUserId(ICurrentUser currentUser) {
        return findClaimIDValue(currentUser, ClaimTypes.ImpersonatorUserId);
    }

    public static String findImpersonatorTenantKey(ICurrentUser currentUser) {
        return findClaimValue(currentUser, ClaimTypes.ImpersonatorTenantKey);
    }

    public static String findImpersonatorUserName(ICurrentUser currentUser) {
        return findClaimValue(currentUser, ClaimTypes.ImpersonatorUserName);
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
