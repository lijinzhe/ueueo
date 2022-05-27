package com.ueueo.boot.autoconfigure.auditing;

import com.ueueo.auditing.*;
import com.ueueo.clients.ICurrentClient;
import com.ueueo.multitenancy.ICurrentTenant;
import com.ueueo.threading.IAmbientScopeProvider;
import com.ueueo.tracing.ICorrelationIdProvider;
import com.ueueo.uow.IUnitOfWorkManager;
import com.ueueo.users.ICurrentUser;
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
@ConditionalOnClass(AuditingInterceptorRegistrar.class)
public class AuditingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(AbpAuditingOptions.class)
    public AbpAuditingOptions auditingOptions() {
        return new AbpAuditingOptions();
    }

    @Bean
    @ConditionalOnMissingBean(IAuditingStore.class)
    public IAuditingStore auditingStore() {
        return new SimpleLogAuditingStore();
    }

    @Bean
    @ConditionalOnBean({ICorrelationIdProvider.class, ICurrentUser.class, ICurrentTenant.class, ICurrentClient.class})
    @ConditionalOnMissingBean(IAuditingHelper.class)
    public IAuditingHelper auditingHelper(IAuditSerializer auditSerializer,
                                          AbpAuditingOptions options,
                                          ICurrentUser currentUser,
                                          ICurrentTenant currentTenant,
                                          ICurrentClient currentClient,
                                          IAuditingStore auditingStore,
                                          ICorrelationIdProvider correlationIdProvider) {
        return new AuditingHelper(auditSerializer, options, currentUser, currentTenant, currentClient, auditingStore, correlationIdProvider);
    }

    @Bean
    @ConditionalOnMissingBean(IAuditingManager.class)
    public IAuditingManager auditingManager(IAmbientScopeProvider<IAuditLogScope> ambientScopeProvider,
                                            IAuditingHelper auditingHelper,
                                            IAuditingStore auditingStore,
                                            AbpAuditingOptions options) {
        return new AuditingManager(ambientScopeProvider, auditingHelper, auditingStore, options);
    }

    @Bean
    @ConditionalOnBean({IUnitOfWorkManager.class, ICurrentUser.class})
    public AuditingInterceptorRegistrar auditingInterceptorRegistrar(IAuditingHelper auditingHelper,
                                                                     IAuditingManager auditingManager,
                                                                     AbpAuditingOptions auditingOptions,
                                                                     IUnitOfWorkManager unitOfWorkManager,
                                                                     ICurrentUser currentUser) {
        return new AuditingInterceptorRegistrar(auditingHelper, auditingManager, auditingOptions, unitOfWorkManager, currentUser);
    }
}
