package com.ueueo.ddd.domain.repositories;

import com.ueueo.ddd.domain.entities.IEntity;
import com.weiming.framework.querydsl.sql.model.SQLQuery;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-19 15:00
 */
public interface IRepositoryWithEntity<TEntity extends IEntity> extends IReadOnlyRepository<TEntity>, IBasicRepository<TEntity> {
    /**
     * Get a single entity
     *
     * @param query
     * @param includeDetails
     *
     * @return
     */
    TEntity get(SQLQuery query, Boolean includeDetails);

    /**
     * Deletes many entities by the given <paramref name="predicate"/>.
     *
     * @param query
     * @param autoSave
     */
    void delete(SQLQuery query, Boolean autoSave);

}
