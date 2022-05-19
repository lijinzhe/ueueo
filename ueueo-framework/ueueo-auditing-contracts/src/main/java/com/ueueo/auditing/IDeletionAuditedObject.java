package com.ueueo.auditing;

import com.ueueo.ID;

/**
 * This interface can be implemented to store deletion information (who delete and when deleted).
 *
 * @author Lee
 * @date 2022-05-18 15:20
 */
public interface IDeletionAuditedObject extends IHasDeletionTime {
    /**
     * Id of the deleter user.
     *
     * @return
     */
    ID getDeleterId();

    void setDeleterId(ID deleterId);
}
