package com.ueueo.ddd.domain.repositories;

import com.ueueo.ddd.domain.entities.IEntity;
import com.ueueo.uow.IUnitOfWorkManagerAccessor;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-19 22:49
 */
public abstract class RepositoryBase<TEntity extends IEntity> extends BasicRepositoryBase<TEntity> implements IRepositoryWithEntity<TEntity>, IUnitOfWorkManagerAccessor {

}
