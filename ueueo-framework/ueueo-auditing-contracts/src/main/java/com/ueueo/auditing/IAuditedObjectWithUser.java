package com.ueueo.auditing;

/**
 * This interface can be implemented to add standard auditing properties to a class.
 *
 * @author Lee
 * @date 2022-05-18 15:20
 */
public interface IAuditedObjectWithUser<TUser> extends IAuditedObject, ICreationAuditedObjectWithUser<TUser>, IModificationAuditedObjectWithUser<TUser> {

}
