package com.ueueo.ddd.domain.entities;

/**
 * Defines an entity with a single primary key with "Id" property.
 * TKey is Type of the primary key of the entity
 *
 * @author Lee
 * @date 2021-08-20 16:45
 */

public interface IEntity<TKey> {
    /**
     * Unique identifier for this entity.
     *
     * @return
     */
    TKey getId();

}
