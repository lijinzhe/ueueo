package com.ueueo.security.claims;

import com.ueueo.claims.ClaimsIdentity;
import com.ueueo.principal.ClaimsPrincipal;
import lombok.Getter;
import org.springframework.beans.factory.BeanFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 21:03
 */
public class AbpClaimsPrincipalFactory implements IAbpClaimsPrincipalFactory {
    public static final String AuthenticationType = "Abp.Application";

    private BeanFactory beanFactory;
    @Getter
    private AbpClaimsPrincipalFactoryOptions options;

    public AbpClaimsPrincipalFactory(BeanFactory beanFactory, AbpClaimsPrincipalFactoryOptions options) {
        this.beanFactory = beanFactory;
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
            List<IAbpClaimsPrincipalContributor> contributors = options.getContributors().stream().map(cls -> beanFactory.getBean(cls)).collect(Collectors.toList());
            for (IAbpClaimsPrincipalContributor contributor : contributors) {
                contributor.contribute(context);
            }
        }
        return claimsPrincipal;
    }
}
