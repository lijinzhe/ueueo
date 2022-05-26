package com.ueueo.auditing;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * TODO: Move ShouldSaveAudit & IsEntityHistoryEnabled and rename to IAuditingFactory
 *
 * @author Lee
 * @date 2022-05-26 11:47
 */
public interface IAuditingHelper {
    /**
     * @param methodInfo
     * @param defaultValue default false
     *
     * @return
     */
    boolean shouldSaveAudit(Method methodInfo, boolean defaultValue);

    /**
     * @param entityType
     * @param defaultValue default false
     *
     * @return
     */
    boolean isEntityHistoryEnabled(Class<?> entityType, boolean defaultValue);

    AuditLogInfo createAuditLogInfo();

    AuditLogActionInfo createAuditLogAction(AuditLogInfo auditLog, Class<?> type, Method method, Object[] arguments);

    AuditLogActionInfo createAuditLogAction(AuditLogInfo auditLog, Class<?> type, Method method, Map<String, Object> arguments);
}
