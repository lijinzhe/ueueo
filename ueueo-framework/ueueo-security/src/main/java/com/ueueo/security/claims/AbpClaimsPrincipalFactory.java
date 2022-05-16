package com.ueueo.security.claims;

import com.ueueo.claims.ClaimsIdentity;
import com.ueueo.principal.ClaimsPrincipal;
import com.ueueo.security.threading.SecurityAsyncTaskExecutor;
import lombok.Getter;

import java.util.concurrent.Future;

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
    public Future<ClaimsPrincipal> createAsync(ClaimsPrincipal existsClaimsPrincipal) {
        return SecurityAsyncTaskExecutor.INSTANCE.submit(() -> {
            ClaimsPrincipal claimsPrincipal = null;
            if (existsClaimsPrincipal != null) {
                claimsPrincipal = existsClaimsPrincipal;
            } else {
                claimsPrincipal = new ClaimsPrincipal(new ClaimsIdentity(AuthenticationType,
                        AbpClaimTypes.UserName,
                        AbpClaimTypes.Role));
                AbpClaimsPrincipalContributorContext context = new AbpClaimsPrincipalContributorContext(claimsPrincipal);
                for (Class<?> type : options.getContributors()) {
                    if (type.isAssignableFrom(IAbpClaimsPrincipalContributor.class)) {
                        IAbpClaimsPrincipalContributor contributor = null;
                        try {
                            //TODO 使用类创建对象，这块需要优化，ABP是使用类似BeanFactory的方式创建的
                            contributor = (IAbpClaimsPrincipalContributor) type.newInstance();
                            contributor.contributeAsync(context).get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return claimsPrincipal;
        });
    }
}
