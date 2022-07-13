package com.ueueo.security.claims;

import com.ueueo.security.principal.ClaimsPrincipal;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 21:03
 */
public class ClaimsPrincipalFactory implements IClaimsPrincipalFactory {

    public static final String AUTHENTICATION_TYPE = "Abp.Application";

    private final List<IClaimsPrincipalContributor> contributors;

    public ClaimsPrincipalFactory() {
        this.contributors = new ArrayList<>();
    }

    public ClaimsPrincipalFactory addClaimsPrincipalContributor(IClaimsPrincipalContributor contributor) {
        this.contributors.add(contributor);
        return this;
    }

    @Override
    public ClaimsPrincipal create(ClaimsPrincipal existsClaimsPrincipal) {
        ClaimsPrincipal claimsPrincipal = null;
        if (existsClaimsPrincipal != null) {
            claimsPrincipal = existsClaimsPrincipal;
        } else {
            claimsPrincipal = new ClaimsPrincipal(new ClaimsIdentity(AUTHENTICATION_TYPE, ClaimTypes.UserName, ClaimTypes.Role));

            ClaimsPrincipalContributorContext context = new ClaimsPrincipalContributorContext(claimsPrincipal);

            for (IClaimsPrincipalContributor contributor : contributors) {
                contributor.contribute(context);
            }
        }
        return claimsPrincipal;
    }
}
