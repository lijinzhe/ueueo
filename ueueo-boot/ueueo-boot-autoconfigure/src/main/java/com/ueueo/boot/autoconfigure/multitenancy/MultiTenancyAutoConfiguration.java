package com.ueueo.boot.autoconfigure.multitenancy;

import com.ueueo.multitenancy.*;
import com.ueueo.multitenancy.store.InMemoryTenantStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lee
 * @date 2022-05-18 14:42
 */
@Configuration
@ConditionalOnClass(CurrentTenant.class)
public class MultiTenancyAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ICurrentTenantAccessor.class)
    public ICurrentTenantAccessor currentTenantAccessor() {
        return AsyncLocalCurrentTenantAccessor.INSTANCE;
    }

    @Bean
    @ConditionalOnMissingBean(ICurrentTenant.class)
    public ICurrentTenant currentTenant(ICurrentTenantAccessor currentTenantAccessor) {
        return new CurrentTenant(currentTenantAccessor);
    }

    @Bean
    @ConditionalOnMissingBean(InMemoryTenantStore.class)
    public ITenantStore tenantStore() {
        return new InMemoryTenantStore();
    }

    @Bean
    @ConditionalOnMissingBean(ITenantResolver.class)
    public ITenantResolver tenantResolver() {
        return new TenantResolver();
    }
}
