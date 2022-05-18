package com.ueueo.security.claims;

import com.ueueo.claims.ClaimsIdentity;
import com.ueueo.principal.ClaimsPrincipal;
import lombok.Getter;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 21:03
 */
public class AbpClaimsPrincipalFactory implements IAbpClaimsPrincipalFactory {
    public static final String AuthenticationType = "Abp.Application";

    @Getter
    private AbpClaimsPrincipalFactoryOptions options;

    public AbpClaimsPrincipalFactory(AbpClaimsPrincipalFactoryOptions options) {
        this.options = options;
    }

    @Override
    public ClaimsPrincipal create(ClaimsPrincipal existsClaimsPrincipal) {
        ClaimsPrincipal claimsPrincipal = null;
        if (existsClaimsPrincipal != null) {
            claimsPrincipal = existsClaimsPrincipal;
        } else {
            claimsPrincipal = new ClaimsPrincipal(new ClaimsIdentity(AuthenticationType,
                    AbpClaimTypes.UserName,
                    AbpClaimTypes.Role));
            AbpClaimsPrincipalContributorContext context = new AbpClaimsPrincipalContributorContext(claimsPrincipal);
            for (IAbpClaimsPrincipalContributor contributor : options.getContributors()) {
                contributor.contribute(context);
            }
        }
        return claimsPrincipal;
    }
}
