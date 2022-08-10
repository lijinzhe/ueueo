package com.ueueo.ddd.domain.entities;

/**
 * Defines an aggregate root with a single primary key with "Id" property.
 *
 * @author Lee
 * @date 2021-08-20 16:51
 */
public interface IAggregateRoot<TKey> extends IEntity<TKey> {

}
