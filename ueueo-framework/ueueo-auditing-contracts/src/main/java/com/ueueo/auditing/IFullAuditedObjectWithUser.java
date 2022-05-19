package com.ueueo.auditing;

/**
 * Adds user navigation properties to <see cref="IFullAuditedObject"/> interface for user.
 *
 * @param <TUser> Type of the user
 *
 * @author Lee
 * @date 2022-05-18 15:20
 */
public interface IFullAuditedObjectWithUser<TUser> extends IAuditedObjectWithUser<TUser>, IFullAuditedObject, IDeletionAuditedObjectWithUser<TUser> {

}
