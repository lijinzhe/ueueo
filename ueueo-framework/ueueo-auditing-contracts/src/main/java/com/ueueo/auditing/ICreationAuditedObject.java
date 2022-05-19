package com.ueueo.auditing;

/**
 * This interface can be implemented to store creation information (who and when created).
 *
 * @author Lee
 * @date 2022-05-18 15:20
 */
public interface ICreationAuditedObject extends IHasCreationTime, IMayHaveCreator {

}
