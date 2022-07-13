package com.ueueo.users;

import com.ueueo.ID;
import com.ueueo.security.claims.Claim;
import com.ueueo.security.claims.ClaimTypes;
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
        if (principalAccessor.getCurrentPrincipal() != null) {
            return principalAccessor.getCurrentPrincipal().findUserId();
        }
        return null;
    }

    @Override
    public String getUserName() {
        return CurrentUserExtensions.findClaimValue(this, ClaimTypes.UserName);
    }

    @Override
    public String getFullName() {
        return CurrentUserExtensions.findClaimValue(this, ClaimTypes.Name);
    }

    @Override
    public String getSurname() {
        return CurrentUserExtensions.findClaimValue(this, ClaimTypes.Surname);
    }

    @Override
    public String getPhoneNumber() {
        return CurrentUserExtensions.findClaimValue(this, ClaimTypes.PhoneNumber);
    }

    @Override
    public boolean getPhoneNumberVerified() {
        return "true".equalsIgnoreCase(CurrentUserExtensions.findClaimValue(this, ClaimTypes.PhoneNumberVerified));
    }

    @Override
    public String getEmail() {
        return CurrentUserExtensions.findClaimValue(this, ClaimTypes.Email);
    }

    @Override
    public boolean getEmailVerified() {
        return "true".equalsIgnoreCase(CurrentUserExtensions.findClaimValue(this, ClaimTypes.EmailVerified));
    }

    @Override
    public ID getTenantId() {
        if (principalAccessor.getCurrentPrincipal() != null) {
            return principalAccessor.getCurrentPrincipal().findTenantId();
        }
        return null;
    }

    @Override
    public List<String> getRoles() {
        return findClaims(ClaimTypes.Role).stream()
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
        if (principalAccessor.getCurrentPrincipal() != null) {
            return principalAccessor.getCurrentPrincipal().getClaims();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean isInRole(String roleName) {
        return findClaims(ClaimTypes.Role).stream().anyMatch(claim -> claim.getValue().equals(roleName));
    }


}
