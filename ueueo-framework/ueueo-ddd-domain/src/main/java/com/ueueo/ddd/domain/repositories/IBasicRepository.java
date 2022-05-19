package com.ueueo.ddd.domain.repositories;

import com.ueueo.ddd.domain.entities.IEntity;

import java.util.Collection;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-20 16:33
 */
public interface IBasicRepository<TEntity extends IEntity> extends IReadOnlyBasicRepository<TEntity> {
    void insert(TEntity entity);

    void insertMany(Collection<TEntity> entities);

    void update(TEntity entity);

    void updateMany(Collection<TEntity> entities);

    void delete(TEntity entity);

    void deleteMany(Collection<TEntity> entities);
}
