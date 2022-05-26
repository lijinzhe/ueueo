package com.ueueo.auditing;

import com.ueueo.clients.ICurrentClient;
import com.ueueo.dependencyinjection.system.IServiceProvider;
import com.ueueo.multitenancy.ICurrentTenant;
import com.ueueo.tracing.ICorrelationIdProvider;
import com.ueueo.users.CurrentUserExtensions;
import com.ueueo.users.ICurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lee
 * @date 2022-05-26 18:03
 */
@Slf4j
public class AuditingHelper implements IAuditingHelper {

    protected IAuditingStore auditingStore;
    protected ICurrentUser currentUser;
    protected ICurrentTenant currentTenant;
    protected ICurrentClient currentClient;
    protected AbpAuditingOptions options;
    protected IAuditSerializer auditSerializer;
    protected IServiceProvider serviceProvider;
    protected ICorrelationIdProvider correlationIdProvider;

    public AuditingHelper(IAuditSerializer auditSerializer, AbpAuditingOptions options, ICurrentUser currentUser, ICurrentTenant currentTenant, ICurrentClient currentClient, IAuditingStore auditingStore, IServiceProvider serviceProvider, ICorrelationIdProvider correlationIdProvider) {
        this.options = options;
        this.auditSerializer = auditSerializer;
        this.currentUser = currentUser;
        this.currentTenant = currentTenant;
        this.currentClient = currentClient;
        this.auditingStore = auditingStore;

        this.serviceProvider = serviceProvider;
        this.correlationIdProvider = correlationIdProvider;
    }

    /**
     * @param methodInfo
     * @param defaultValue default false
     *
     * @return
     */
    @Override
    public boolean shouldSaveAudit(Method methodInfo, Boolean defaultValue) {
        if (methodInfo == null) {
            return false;
        }

        if (methodInfo.getModifiers() != Modifier.PUBLIC) {
            return false;
        }

        if (methodInfo.isAnnotationPresent(Audited.class)) {
            return true;
        }

        if (methodInfo.isAnnotationPresent(DisableAuditing.class)) {
            return false;
        }

        Class<?> classType = methodInfo.getDeclaringClass();

        Boolean shouldAudit = AuditingInterceptorRegistrar.shouldAuditTypeByDefaultOrNull(classType);
        if (shouldAudit != null) {
            return shouldAudit;
        }

        return defaultValue;
    }

    /**
     * @param entityType
     * @param defaultValue default false
     *
     * @return
     */
    @Override
    public boolean isEntityHistoryEnabled(Class<?> entityType, Boolean defaultValue) {
        if (entityType.getModifiers() != Modifier.PUBLIC) {
            return false;
        }

        if (options.getIgnoredTypes().stream().anyMatch(t -> t.isAssignableFrom(entityType))) {
            return false;
        }

        if (entityType.isAnnotationPresent(Audited.class)) {
            return true;
        }

        for (Field field : entityType.getFields()) {
            if (field.isAnnotationPresent(Audited.class)) {
                return true;
            }
        }

        if (entityType.isAnnotationPresent(DisableAuditing.class)) {
            return false;
        }
        if (options.getEntityHistorySelectors().stream().anyMatch(selector -> selector.getPredicate().apply(entityType))) {
            return true;
        }
        return defaultValue;
    }

    @Override
    public AuditLogInfo createAuditLogInfo() {
        AuditLogInfo auditInfo = new AuditLogInfo();
        auditInfo.setApplicationName(options.getApplicationName());
        auditInfo.setUserId(currentUser.getId());
        auditInfo.setUserName(currentUser.getUserName());
        auditInfo.setTenantId(currentTenant.getId());
        auditInfo.setTenantName(currentTenant.getName());
        auditInfo.setImpersonatorUserId(CurrentUserExtensions.findImpersonatorUserId(currentUser));
        auditInfo.setImpersonatorTenantId(CurrentUserExtensions.findImpersonatorTenantId(currentUser));
        auditInfo.setImpersonatorUserName(CurrentUserExtensions.findImpersonatorUserName(currentUser));
        auditInfo.setImpersonatorTenantName(CurrentUserExtensions.findImpersonatorTenantName(currentUser));
        auditInfo.setExecutionTime(new Date());
        auditInfo.setClientId(currentClient.getId());
        auditInfo.setCorrelationId(correlationIdProvider.get());
        executePreContributors(auditInfo);
        return auditInfo;
    }

    @Override
    public AuditLogActionInfo createAuditLogAction(AuditLogInfo auditLog, Class<?> type, Method method, Object[] arguments) {
        return createAuditLogAction(auditLog, type, method, createArgumentsDictionary(method, arguments));
    }

    @Override
    public AuditLogActionInfo createAuditLogAction(AuditLogInfo auditLog, Class<?> type, Method method, Map<String, Object> arguments) {
        AuditLogActionInfo actionInfo = new AuditLogActionInfo();
        actionInfo.setServiceName(type != null ? type.getName() : "");
        actionInfo.setMethodName(method.getName());
        actionInfo.setParameters(serializeConvertArguments(arguments));
        actionInfo.setExecutionTime(new Date());
        //TODO Execute contributors
        return actionInfo;
    }

    protected void executePreContributors(AuditLogInfo auditLogInfo) {

        AuditLogContributionContext context = new AuditLogContributionContext(serviceProvider, auditLogInfo);

        for (AuditLogContributor contributor : options.getContributors()) {
            try {
                contributor.preContribute(context);
            } catch (Exception ex) {
                log.warn(ex.getMessage(), ex);
            }
        }

    }

    protected String serializeConvertArguments(Map<String, Object> arguments) {
        try {
            if (MapUtils.isEmpty(arguments)) {
                return "{}";
            }

            Map<String, Object> dictionary = new HashMap<>();

            for (Map.Entry<String, Object> argument : arguments.entrySet()) {
                if (argument.getValue() != null && options.getIgnoredTypes().stream().anyMatch(t -> t.isInstance(argument.getValue()))) {
                    dictionary.put(argument.getKey(), null);
                } else {
                    dictionary.put(argument.getKey(), argument.getValue());
                }
            }

            return auditSerializer.serialize(dictionary);
        } catch (Exception ex) {
            log.warn(ex.getMessage(), ex);
            return "{}";
        }
    }

    protected Map<String, Object> createArgumentsDictionary(Method method, Object[] arguments) {
        Parameter[] parameters = method.getParameters();
        Map<String, Object> dictionary = new HashMap<>();

        for (int i = 0; i < parameters.length; i++) {
            dictionary.put(parameters[i].getName(), arguments[i]);
        }

        return dictionary;
    }

}
