package com.ueueo.boot.autoconfigure.web.multitenancy;

import com.ueueo.multitenancy.TenantResolver;
import com.ueueo.web.multitenancy.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * @author Lee
 * @date 2022-05-18 14:42
 */
@Configuration
@EnableConfigurationProperties(WebMultiTenancyProperties.class)
@ConditionalOnClass(HttpTenantResolveContributorBase.class)
@ConditionalOnBean(TenantResolver.class)
public class WebMultiTenancyAutoConfiguration {

    private final WebMultiTenancyProperties properties;

    public WebMultiTenancyAutoConfiguration(
            WebMultiTenancyProperties properties,
            ObjectProvider<TenantResolver> tenantResolver) {
        this.properties = properties;
        Optional.ofNullable(tenantResolver.getIfAvailable()).ifPresent(this::configureTenantResolver);
    }

    private void configureTenantResolver(TenantResolver tenantResolver) {
        tenantResolver.getContributors().add(new DomainTenantResolveContributor(properties.getDomainRegex()));
        tenantResolver.getContributors().add(new QueryStringTenantResolveContributor(properties.getTenantField()));
        tenantResolver.getContributors().add(new RouteTenantResolveContributor(properties.getTenantField()));
        tenantResolver.getContributors().add(new HeaderTenantResolveContributor(properties.getTenantField()));
        tenantResolver.getContributors().add(new CookieTenantResolveContributor(properties.getTenantField()));
    }
}
