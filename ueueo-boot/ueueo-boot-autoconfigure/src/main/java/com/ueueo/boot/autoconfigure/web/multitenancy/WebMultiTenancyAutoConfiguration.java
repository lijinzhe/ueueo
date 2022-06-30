package com.ueueo.boot.autoconfigure.web.multitenancy;

import com.ueueo.multitenancy.AbpTenantResolveOptions;
import com.ueueo.multitenancy.AbpTenantResolveOptionsAware;
import com.ueueo.web.multitenancy.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lee
 * @date 2022-05-18 14:42
 */
@Configuration
@EnableConfigurationProperties(WebMultiTenancyConfigProperties.class)
@ConditionalOnClass(HttpTenantResolveContributorBase.class)
public class WebMultiTenancyAutoConfiguration implements AbpTenantResolveOptionsAware {

    private final WebMultiTenancyConfigProperties properties;

    public WebMultiTenancyAutoConfiguration(WebMultiTenancyConfigProperties properties) {
        this.properties = properties;
    }

    @Override
    public void setOptions(AbpTenantResolveOptions tenantResolveOptions) {
        tenantResolveOptions.getTenantResolvers().add(new DomainTenantResolveContributor(properties.getDomainRegex()));
        tenantResolveOptions.getTenantResolvers().add(new QueryStringTenantResolveContributor(properties.getTenantField()));
        //        options.getTenantResolvers().add(new FormTenantResolveContributor());
        tenantResolveOptions.getTenantResolvers().add(new RouteTenantResolveContributor(properties.getTenantField()));
        tenantResolveOptions.getTenantResolvers().add(new HeaderTenantResolveContributor(properties.getTenantField()));
        tenantResolveOptions.getTenantResolvers().add(new CookieTenantResolveContributor(properties.getTenantField()));
    }
}
