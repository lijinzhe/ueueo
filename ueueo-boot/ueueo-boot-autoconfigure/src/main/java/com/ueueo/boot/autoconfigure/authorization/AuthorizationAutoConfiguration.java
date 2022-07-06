package com.ueueo.boot.autoconfigure.authorization;

import com.ueueo.authorization.permissions.*;
import com.ueueo.multitenancy.ICurrentTenant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lee
 * @date 2022-05-18 14:42
 */
@Configuration
public class AuthorizationAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(IPermissionValueProviderManager.class)
    @ConditionalOnBean({IPermissionStore.class, ICurrentTenant.class})
    @ConditionalOnClass(PermissionValueProviderManager.class)
    public IPermissionValueProviderManager valueProviderManager(IPermissionStore permissionStore, ICurrentTenant currentTenant) {
        return new PermissionValueProviderManager(permissionStore, currentTenant);
    }

    @Bean
    @ConditionalOnMissingBean(IPermissionDefinitionManager.class)
    @ConditionalOnClass(PermissionDefinitionManager.class)
    public IPermissionDefinitionManager definitionManager() {
        return new PermissionDefinitionManager();
    }

}
