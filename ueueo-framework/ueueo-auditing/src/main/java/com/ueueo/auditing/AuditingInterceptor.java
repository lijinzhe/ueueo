package com.ueueo.auditing;

import com.ueueo.aspects.AbpCrossCuttingConcerns;
import com.ueueo.dependencyinjection.system.IServiceScope;
import com.ueueo.dependencyinjection.system.IServiceScopeFactory;
import com.ueueo.dynamicproxy.AbpInterceptor;
import com.ueueo.dynamicproxy.IAbpMethodInvocation;
import com.ueueo.uow.IUnitOfWorkManager;
import com.ueueo.users.ICurrentUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.util.Assert;

import java.util.function.Function;

/**
 * @author Lee
 * @date 2022-05-26 17:37
 */
public class AuditingInterceptor extends AbpInterceptor {

    private final IServiceScopeFactory serviceScopeFactory;

    public AuditingInterceptor(IServiceScopeFactory serviceScopeFactory) {
        this.serviceScopeFactory = serviceScopeFactory;
    }

    @Override
    public void intercept(IAbpMethodInvocation invocation) {
        IServiceScope serviceScope = serviceScopeFactory.createScope();
        IAuditingHelper auditingHelper = serviceScope.getServiceProvider().getRequiredService(IAuditingHelper.class);
        AbpAuditingOptions auditingOptions = serviceScope.getServiceProvider().getRequiredService(AbpAuditingOptions.class);

        if (!shouldIntercept(invocation, auditingOptions, auditingHelper)) {
            invocation.proceed();
            return;
        }

        IAuditingManager auditingManager = serviceScope.getServiceProvider().getRequiredService(IAuditingManager.class);
        if (auditingManager.getCurrent() != null) {
            proceedByLogging(invocation, auditingHelper, auditingManager.getCurrent());
        } else {
            ICurrentUser currentUser = serviceScope.getServiceProvider().getRequiredService(ICurrentUser.class);
            IUnitOfWorkManager unitOfWorkManager = serviceScope.getServiceProvider().getRequiredService(IUnitOfWorkManager.class);
            processWithNewAuditingScope(invocation, auditingOptions, currentUser, auditingManager, auditingHelper, unitOfWorkManager);
        }
    }

    protected boolean shouldIntercept(IAbpMethodInvocation invocation, AbpAuditingOptions options, IAuditingHelper auditingHelper) {
        if (!options.isEnabled()) {
            return false;
        }
        if (AbpCrossCuttingConcerns.isApplied(invocation.getTargetObject(), AbpCrossCuttingConcerns.Auditing)) {
            return false;
        }
        return auditingHelper.shouldSaveAudit(invocation.getMethod(), false);
    }

    private static void proceedByLogging(IAbpMethodInvocation invocation, IAuditingHelper auditingHelper, IAuditLogScope auditLogScope) {
        AuditLogInfo auditLog = auditLogScope.getLog();
        AuditLogActionInfo auditLogAction = auditingHelper.createAuditLogAction(auditLog, invocation.getTargetObject().getClass(), invocation.getMethod(), invocation.getArguments());
        StopWatch stopwatch = StopWatch.createStarted();
        try {
            invocation.proceed();
        } catch (Exception ex) {
            auditLog.getExceptions().add(ex);
            throw ex;
        } finally {
            stopwatch.stop();
            auditLogAction.setExecutionDuration(stopwatch.getTime());
            auditLog.getActions().add(auditLogAction);
        }
    }

    private void processWithNewAuditingScope(IAbpMethodInvocation invocation, AbpAuditingOptions options, ICurrentUser currentUser, IAuditingManager auditingManager, IAuditingHelper auditingHelper, IUnitOfWorkManager unitOfWorkManager) {
        boolean hasError = false;
        IAuditLogSaveHandle saveHandle = auditingManager.beginScope();
        Assert.notNull(auditingManager.getCurrent(), "Current must not null!");
        try {
            proceedByLogging(invocation, auditingHelper, auditingManager.getCurrent());
            if (!auditingManager.getCurrent().getLog().getExceptions().isEmpty()) {
                hasError = true;
            }
        } catch (Exception e) {
            hasError = true;
            throw e;
        } finally {
            if (shouldWriteAuditLog(invocation, auditingManager.getCurrent().getLog(), options, currentUser, hasError)) {
                if (unitOfWorkManager.getCurrent() != null) {
                    try {
                        unitOfWorkManager.getCurrent().saveChanges();
                    } catch (Exception ex) {
                        if (!auditingManager.getCurrent().getLog().getExceptions().contains(ex)) {
                            auditingManager.getCurrent().getLog().getExceptions().add(ex);
                        }
                    }
                }

                saveHandle.save();
            }
        }

    }

    private boolean shouldWriteAuditLog(IAbpMethodInvocation invocation, AuditLogInfo auditLogInfo, AbpAuditingOptions options, ICurrentUser currentUser, boolean hasError) {
        for (Function<AuditLogInfo, Boolean> selector : options.getAlwaysLogSelectors()) {
            if (selector.apply(auditLogInfo)) {
                return true;
            }
        }
        if (options.isAlwaysLogOnException() && hasError) {
            return true;
        }
        if (!options.isEnabledForAnonymousUsers() && !currentUser.isAuthenticated()) {
            return false;
        }
        if (!options.isEnabledForGetRequests() && StringUtils.startsWithIgnoreCase(invocation.getMethod().getName(), "Get")) {
            return false;
        }
        return true;
    }
}
