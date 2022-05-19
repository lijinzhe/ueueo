package com.ueueo.auditing;

/**
 * Extends <see cref="IDeletionAuditedObject"/> to add user navigation propery.
 *
 * @param <TUser> Type of the user
 *
 * @author Lee
 * @date 2022-05-18 15:20
 */
public interface IDeletionAuditedObjectWithUser<TUser> extends IDeletionAuditedObject {

    /**
     * Reference to the deleter user.
     *
     * @return
     */
    TUser getDeleter();

    void setDeleter(TUser user);
}
