package com.ueueo.auditing;

/**
 * Adds navigation property (object reference) to <see cref="ICreationAuditedObject"/> interface.
 *
 * @author Lee
 * @date 2022-05-18 15:20
 */
public interface ICreationAuditedObject<TCreator> extends IMayHaveCreator<TCreator> {

}
