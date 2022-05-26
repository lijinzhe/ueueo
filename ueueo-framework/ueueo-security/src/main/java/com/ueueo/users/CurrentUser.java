package com.ueueo.users;

import com.ueueo.ID;
import com.ueueo.claims.Claim;
import com.ueueo.security.claims.AbpClaimTypes;
import com.ueueo.security.claims.ICurrentPrincipalAccessor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2021-09-13 14:04
 */
@Data
public class CurrentUser implements ICurrentUser {

    private static final List<Claim> EmptyClaimsArray = Collections.emptyList();

    private final ICurrentPrincipalAccessor principalAccessor;

    public CurrentUser(ICurrentPrincipalAccessor principalAccessor) {
        this.principalAccessor = principalAccessor;
    }

    @Override
    public boolean isAuthenticated() {
        return getId() != null;
    }

    @Override
    public ID getId() {
        if (principalAccessor.getPrincipal() != null) {
            return principalAccessor.getPrincipal().findUserId();
        }
        return null;
    }

    @Override
    public String getUserName() {
        return CurrentUserExtensions.findClaimValue(this, AbpClaimTypes.UserName);
    }

    @Override
    public String getName() {
        return CurrentUserExtensions.findClaimValue(this, AbpClaimTypes.Name);
    }

    @Override
    public String getSurName() {
        return CurrentUserExtensions.findClaimValue(this, AbpClaimTypes.SurName);
    }

    @Override
    public String getPhoneNumber() {
        return CurrentUserExtensions.findClaimValue(this, AbpClaimTypes.PhoneNumber);
    }

    @Override
    public boolean getPhoneNumberVerified() {
        return "true".equalsIgnoreCase(CurrentUserExtensions.findClaimValue(this, AbpClaimTypes.PhoneNumberVerified));
    }

    @Override
    public String getEmail() {
        return CurrentUserExtensions.findClaimValue(this, AbpClaimTypes.Email);
    }

    @Override
    public boolean getEmailVerified() {
        return "true".equalsIgnoreCase(CurrentUserExtensions.findClaimValue(this, AbpClaimTypes.EmailVerified));
    }

    @Override
    public ID getTenantId() {
        if (principalAccessor.getPrincipal() != null) {
            return principalAccessor.getPrincipal().findTenantId();
        }
        return null;
    }

    @Override
    public List<String> getRoles() {
        return findClaims(AbpClaimTypes.Role).stream()
                .map(Claim::getValue)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Claim findClaim(String claimType) {
        return getAllClaims().stream()
                .filter(claim -> StringUtils.equalsIgnoreCase(claim.getType(), claimType)).findFirst()
                .orElse(null);
    }

    @Override
    public List<Claim> findClaims(String claimType) {
        return getAllClaims().stream()
                .filter(claim -> StringUtils.equalsIgnoreCase(claim.getType(), claimType))
                .collect(Collectors.toList());
    }

    @Override
    public List<Claim> getAllClaims() {
        if (principalAccessor.getPrincipal() != null) {
            return principalAccessor.getPrincipal().getClaims();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean isInRole(String roleName) {
        return findClaims(AbpClaimTypes.Role).stream().anyMatch(claim -> claim.getValue().equals(roleName));
    }

}
