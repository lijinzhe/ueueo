package com.ueueo.auditing;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Lee
 * @date 2022-05-26 14:20
 */
@Slf4j
public class SimpleLogAuditingStore implements IAuditingStore {

    @Override
    public void save(AuditLogInfo auditInfo) {
        log.info(auditInfo.toString());
    }
}
