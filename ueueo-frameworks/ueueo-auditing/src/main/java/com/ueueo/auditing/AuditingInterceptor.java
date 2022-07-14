package com.ueueo.auditing;

import com.ueueo.aspects.CrossCuttingConcerns;
import com.ueueo.uow.IUnitOfWorkManager;
import com.ueueo.users.ICurrentUser;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.util.Assert;

import java.util.function.Function;

/**
 * @author Lee
 * @date 2022-05-26 17:37
 */
public class AuditingInterceptor implements MethodInterceptor {

    public static final String AUDITING = "AbpAuditing";

    private final IAuditingHelper auditingHelper;
    private final IAuditingManager auditingManager;
    private final AbpAuditingOptions auditingOptions;
    private final IUnitOfWorkManager unitOfWorkManager;
    private final ICurrentUser currentUser;

    public AuditingInterceptor(IAuditingHelper auditingHelper,
                               IAuditingManager auditingManager,
                               AbpAuditingOptions auditingOptions,
                               IUnitOfWorkManager unitOfWorkManager,
                               ICurrentUser currentUser
    ) {
        this.auditingHelper = auditingHelper;
        this.auditingManager = auditingManager;
        this.auditingOptions = auditingOptions;
        this.unitOfWorkManager = unitOfWorkManager;
        this.currentUser = currentUser;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (!shouldIntercept(invocation, auditingOptions, auditingHelper)) {
            return invocation.proceed();
        }

        if (auditingManager.getCurrent() != null) {
            return proceedByLogging(invocation);
        } else {
            return processWithNewAuditingScope(invocation);
        }
    }

    protected boolean shouldIntercept(MethodInvocation invocation, AbpAuditingOptions options, IAuditingHelper auditingHelper) {
        if (!options.isEnabled()) {
            return false;
        }
        Object targetObject = invocation.getThis();
        if (targetObject != null && CrossCuttingConcerns.isApplied(targetObject, AUDITING)) {
            return false;
        }
        return auditingHelper.shouldSaveAudit(invocation.getMethod(), false);
    }

    private Object proceedByLogging(MethodInvocation invocation) throws Throwable {
        AuditLogInfo auditLog = auditingManager.getCurrent().getLog();
        AuditLogActionInfo auditLogAction = auditingHelper.createAuditLogAction(auditLog, invocation.getMethod().getDeclaringClass(), invocation.getMethod(), invocation.getArguments());
        StopWatch stopwatch = StopWatch.createStarted();
        try {
            return invocation.proceed();
        } catch (Throwable ex) {
            auditLog.getExceptions().add(ex);
            throw ex;
        } finally {
            stopwatch.stop();
            auditLogAction.setExecutionDuration(stopwatch.getTime());
            auditLog.getActions().add(auditLogAction);
        }
    }

    private Object processWithNewAuditingScope(MethodInvocation invocation) throws Throwable {
        boolean hasError = false;
        IAuditLogSaveHandle saveHandle = auditingManager.beginScope();
        Assert.notNull(auditingManager.getCurrent(), "Current must not null!");
        try {
            Object proceedResult = proceedByLogging(invocation);
            if (!auditingManager.getCurrent().getLog().getExceptions().isEmpty()) {
                hasError = true;
            }
            return proceedResult;
        } catch (Throwable e) {
            hasError = true;
            throw e;
        } finally {
            if (shouldWriteAuditLog(invocation, auditingManager.getCurrent().getLog(), auditingOptions, currentUser, hasError)) {
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

    private boolean shouldWriteAuditLog(MethodInvocation invocation, AuditLogInfo auditLogInfo, AbpAuditingOptions options, ICurrentUser currentUser, boolean hasError) {
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
