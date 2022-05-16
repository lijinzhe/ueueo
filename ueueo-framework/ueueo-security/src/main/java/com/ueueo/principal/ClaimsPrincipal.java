package com.ueueo.principal;

import com.ueueo.claims.Claim;
import com.ueueo.claims.ClaimsIdentity;
import com.ueueo.security.claims.AbpClaimTypes;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-16 20:34
 */
public class ClaimsPrincipal implements IPrincipal {

    @Getter
    public static ClaimsPrincipal Current;
    @Getter
    @Setter
    private static Supplier<ClaimsPrincipal> ClaimsPrincipalSelector;
    @Getter
    @Setter
    private static Function<Iterable<ClaimsIdentity>, ClaimsIdentity> PrimaryIdentitySelector;
    @Getter
    private IIdentity identity;

    private IPrincipal principal;
    @Getter
    private Collection<ClaimsIdentity> identities;
    @Getter
    private Collection<Claim> claims = new ArrayList<>();

    public ClaimsPrincipal() {

    }

    public ClaimsPrincipal(Collection<ClaimsIdentity> identities) {
        this.identities = identities;
    }

    public ClaimsPrincipal(IIdentity identity) {
        this.identity = identity;
    }

    public ClaimsPrincipal(IPrincipal principal) {
        this.principal = principal;
        this.identity = principal.getIdentity();
    }

    public void addIdentities(Collection<ClaimsIdentity> identities) {
        this.identities.addAll(identities);
    }

    public void addIdentity(ClaimsIdentity identity) {
        this.identities.add(identity);
    }

    public ClaimsPrincipal addIdentityIfNotContains(@NonNull ClaimsIdentity identity) {
        Assert.notNull(identity, "identity must not null.");
        if (this.identities.stream().noneMatch(i -> StringUtils.equalsIgnoreCase(i.getAuthenticationType(), identity.getAuthenticationType()))) {
            addIdentity(identity);
        }
        return this;
    }

    @Override
    public ClaimsPrincipal clone() {
        //TODO 实现克隆方法
        return null;
    }

    public Collection<Claim> findAll(Predicate<Claim> match) {
        return claims.stream().filter(match).collect(Collectors.toList());
    }

    public Collection<Claim> findAll(String type) {
        return claims.stream().filter(claim -> claim.getType().equalsIgnoreCase(type)).collect(Collectors.toList());
    }

    public Claim findFirst(String type) {
        return claims.stream().filter(claim -> claim.getType().equalsIgnoreCase(type)).findFirst().orElse(null);
    }

    public Claim findFirst(Predicate<Claim> match) {
        return claims.stream().filter(match).findFirst().orElse(null);
    }

    public boolean hasClaim(Predicate<Claim> match) {
        return claims.stream().anyMatch(match);
    }

    public boolean hasClaim(String type, String value) {
        return claims.stream().anyMatch(claim -> StringUtils.equalsIgnoreCase(claim.getType(), type) && Objects.equals(claim.getValue(), value));
    }

    @Override
    public boolean isInRole(String role) {
        if (principal != null) {
            return principal.isInRole(role);
        }
        return false;
    }

    public Long findUserId() {
        return findLongValueFromClaim(AbpClaimTypes.UserId);
    }

    public Long findTenantId() {
        return findLongValueFromClaim(AbpClaimTypes.TenantId);
    }

    public String findClientId() {
        return findValueFromClaim(AbpClaimTypes.ClientId);
    }

    public Long findEditionId() {
        return findLongValueFromClaim(AbpClaimTypes.EditionId);
    }

    public Long findImpersonatorTenantId() {
        return findLongValueFromClaim(AbpClaimTypes.ImpersonatorTenantId);
    }

    public Long findImpersonatorUserId() {
        return findLongValueFromClaim(AbpClaimTypes.ImpersonatorUserId);
    }

    private String findValueFromClaim(String claimType) {
        return claims.stream()
                .filter(claim -> StringUtils.equalsIgnoreCase(claim.getType(), claimType)).findFirst()
                .map(Claim::getValue)
                .orElse(null);
    }

    private Long findLongValueFromClaim(String claimType) {
        return claims.stream()
                .filter(claim -> StringUtils.equalsIgnoreCase(claim.getType(), claimType)).findFirst()
                .map(claim -> {
                    try {
                        return Long.parseLong(claim.getValue());
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .orElse(null);
    }
}
