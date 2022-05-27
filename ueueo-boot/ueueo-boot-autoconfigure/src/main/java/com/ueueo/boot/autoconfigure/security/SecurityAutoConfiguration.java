package com.ueueo.boot.autoconfigure.security;

import com.ueueo.clients.CurrentClient;
import com.ueueo.clients.ICurrentClient;
import com.ueueo.security.claims.ICurrentPrincipalAccessor;
import com.ueueo.security.claims.SpringSecurityCurrentPrincipalAccessor;
import com.ueueo.users.CurrentUser;
import com.ueueo.users.ICurrentUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Lee
 * @date 2022-05-18 14:42
 */
@Configuration
@ConditionalOnClass(CurrentUser.class)
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

}
