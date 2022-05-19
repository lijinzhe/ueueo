package com.ueueo.ddd.domain.repositories;

import com.ueueo.ddd.domain.entities.IEntity;
import com.weiming.framework.querydsl.sql.model.SQLQuery;

import java.util.List;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-19 22:32
 */
public interface IReadOnlyRepository<TEntity extends IEntity> extends IReadOnlyBasicRepository<TEntity> {

    /**
     * Gets a list of entities
     *
     * @param query
     * @param includeDetails
     *
     * @return
     */
    List<TEntity> getList(SQLQuery query, Boolean includeDetails);
}
