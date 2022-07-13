package com.ueueo.auditing;

/**
 *
 * @author Lee
 * @date 2022-05-26 11:34
 */
public interface IAuditingStore {
    void save(AuditLogInfo auditInfo);
}
