package com.ueueo.boot.autoconfigure.security;

import com.ueueo.clients.CurrentClient;
import com.ueueo.clients.ICurrentClient;
import com.ueueo.security.claims.ClaimsPrincipalFactory;
import com.ueueo.security.claims.IClaimsPrincipalFactory;
import com.ueueo.security.claims.ICurrentPrincipalAccessor;
import com.ueueo.security.claims.SpringSecurityCurrentPrincipalAccessor;
import com.ueueo.securitylog.DefaultSecurityLogManager;
import com.ueueo.securitylog.ISecurityLogManager;
import com.ueueo.securitylog.ISecurityLogStore;
import com.ueueo.securitylog.SimpleSecurityLogStore;
import com.ueueo.users.CurrentUser;
import com.ueueo.users.ICurrentUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Lee
 * @date 2022-05-18 14:42
 */
@Configuration
@ConditionalOnClass(ICurrentUser.class)
@EnableConfigurationProperties(SecurityLogProperties.class)
public class SecurityAutoConfiguration {

    @Bean
    @ConditionalOnClass(SecurityContextHolder.class)
    @ConditionalOnMissingBean(ICurrentPrincipalAccessor.class)
    public ICurrentPrincipalAccessor currentPrincipalAccessor() {
        return new SpringSecurityCurrentPrincipalAccessor();
    }

    @Bean
    @ConditionalOnMissingBean(ICurrentUser.class)
    public ICurrentUser currentUser(ICurrentPrincipalAccessor principalAccessor) {
        return new CurrentUser(principalAccessor);
    }

    @Bean
    @ConditionalOnMissingBean(ICurrentClient.class)
    public ICurrentClient currentClient(ICurrentPrincipalAccessor principalAccessor) {
        return new CurrentClient(principalAccessor);
    }

    @Bean
    @ConditionalOnMissingBean(IClaimsPrincipalFactory.class)
    public IClaimsPrincipalFactory claimsPrincipalFactory() {
        ClaimsPrincipalFactory claimsPrincipalFactory = new ClaimsPrincipalFactory();
        //可以添加自定义的Contributor，参考Authorization文档
        //        claimsPrincipalFactory.addClaimsPrincipalContributor(null)
        return claimsPrincipalFactory;
    }

    @Bean
    @ConditionalOnMissingBean(ISecurityLogStore.class)
    public ISecurityLogStore securityLogStore() {
        return new SimpleSecurityLogStore();
    }

    @Bean
    @ConditionalOnMissingBean(ISecurityLogManager.class)
    public ISecurityLogManager securityLogManager(ISecurityLogStore securityLogStore, SecurityLogProperties properties) {
        DefaultSecurityLogManager securityLogManager = new DefaultSecurityLogManager(securityLogStore);
        securityLogManager.setEnable(properties.isEnable());
        securityLogManager.setApplicationName(properties.getApplicationName());
        return securityLogManager;
    }
}
