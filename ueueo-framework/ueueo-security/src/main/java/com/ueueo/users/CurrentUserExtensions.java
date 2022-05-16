package com.ueueo.users;

import com.ueueo.claims.Claim;
import com.ueueo.security.claims.AbpClaimTypes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-14 11:09
 */
public class CurrentUserExtensions {
    @Nullable
    public static String findClaimValue(ICurrentUser currentUser, String claimType) {
        return Optional.ofNullable(currentUser.findClaim(claimType)).map(Claim::getValue).orElse(null);
    }

    public static Long getId(ICurrentUser currentUser) {
        return currentUser.getId();
    }

    public static Integer findImpersonatorTenantId(ICurrentUser currentUser) {
        String impersonatorTenantId = findClaimValue(currentUser, AbpClaimTypes.ImpersonatorTenantId);
        if (StringUtils.isBlank(impersonatorTenantId)) {
            return null;
        }
        try {
            return Integer.parseInt(impersonatorTenantId);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Long findImpersonatorUserId(ICurrentUser currentUser) {
        String impersonatorUserId = findClaimValue(currentUser, AbpClaimTypes.ImpersonatorUserId);
        if (StringUtils.isBlank(impersonatorUserId)) {
            return null;
        }
        try {
            return Long.parseLong(impersonatorUserId);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String findImpersonatorTenantName(ICurrentUser currentUser) {
        return findClaimValue(currentUser, AbpClaimTypes.ImpersonatorTenantName);
    }

    public static String findImpersonatorUserName(ICurrentUser currentUser) {
        return findClaimValue(currentUser, AbpClaimTypes.ImpersonatorUserName);
    }
}
