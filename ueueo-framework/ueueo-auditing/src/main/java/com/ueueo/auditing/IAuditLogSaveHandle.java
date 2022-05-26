package com.ueueo.auditing;

import com.ueueo.IDisposable;

/**
 * @author Lee
 * @date 2022-05-26 11:00
 */
public interface IAuditLogSaveHandle extends IDisposable {
    void save();
}
