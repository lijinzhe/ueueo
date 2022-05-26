package com.ueueo.auditing;

import lombok.Data;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author Lee
 * @date 2022-05-26 11:35
 */
@Data
public class AbpAuditingOptions {

    //TODO: Consider to add an option to disable auditing for application service methods?

    /**
     * If this value is true, auditing will not throw an exceptions and it will log it when an error occurred while saving AuditLog.
     * Default: true.
     */
    private boolean hideErrors = true;

    /**
     * Default: true.
     */
    private boolean isEnabled = true;

    /**
     * The name of the application or service writing audit logs.
     * Default: null.
     */
    private String applicationName;

    /**
     * Default: true.
     */
    private boolean isEnabledForAnonymousUsers = true;

    /**
     * Audit log on exceptions.
     * Default: true.
     */
    private boolean alwaysLogOnException = true;

    private List<Function<AuditLogInfo, Future<Boolean>>> alwaysLogSelectors;

    private List<AuditLogContributor> contributors;

    private List<Class<?>> ignoredTypes;

    private IEntityHistorySelectorList entityHistorySelectorList;

    //TODO: Move this to asp.net core layer or convert it to a more dynamic strategy?
    public boolean isEnabledForGetRequests = false;

    public AbpAuditingOptions() {
        alwaysLogSelectors = new ArrayList<>();
        contributors = new ArrayList<>();
        ignoredTypes = new ArrayList<>();
        ignoredTypes.add(Stream.class);
        ignoredTypes.add(Expression.class);
        entityHistorySelectorList = new EntityHistorySelectorList();
    }
}
