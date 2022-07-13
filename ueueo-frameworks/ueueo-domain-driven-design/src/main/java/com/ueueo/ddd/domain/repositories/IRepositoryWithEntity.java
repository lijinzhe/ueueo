package com.ueueo.ddd.domain.repositories;

import com.ueueo.ddd.domain.entities.IEntity;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-19 15:00
 */
public interface IRepositoryWithEntity<TEntity extends IEntity> extends IReadOnlyRepository<TEntity>, IBasicRepository<TEntity> {



}
