package com.ueueo.auditing;

/**
 * This interface adds <see cref="IDeletionAuditedObject"/> to <see cref="IAuditedObject"/>.
 *
 * @author Lee
 * @date 2022-05-18 15:20
 */
public interface IFullAuditedObject extends IAuditedObject, IDeletionAuditedObject {

}
