package com.ueueo.ddd.domain.repositories;

import com.ueueo.ddd.domain.entities.IEntity;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-19 22:32
 */
public interface IReadOnlyRepository<TEntity extends IEntity> extends IReadOnlyBasicRepository<TEntity> {

}
