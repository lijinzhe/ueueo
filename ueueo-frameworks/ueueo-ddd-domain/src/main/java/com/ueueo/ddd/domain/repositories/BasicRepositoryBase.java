package com.ueueo.ddd.domain.repositories;

import com.ueueo.ddd.domain.entities.IEntity;
import com.ueueo.uow.IUnitOfWorkEnabled;

import java.util.Collection;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-20 16:35
 */
public abstract class BasicRepositoryBase<TEntity extends IEntity<TKey>, TKey> implements IBasicRepository<TEntity, TKey>, IUnitOfWorkEnabled {
    protected BasicRepositoryBase() {}

    @Override
    public void insertMany(Collection<TEntity> entities, Boolean autoSave) {
        for (TEntity entity : entities) {
            insert(entity, autoSave);
        }
    }

    @Override
    public void updateMany(Collection<TEntity> entities, Boolean autoSave) {
        for (TEntity entity : entities) {
            update(entity, autoSave);
        }
    }

    @Override
    public void deleteMany(Collection<TEntity> entities, Boolean autoSave) {
        for (TEntity entity : entities) {
            delete(entity, autoSave);
        }
    }

    @Override
    public void deleteManyByIds(Collection<TKey> ids, Boolean autoSave) {
        for (TKey id : ids) {
            deleteById(id, autoSave);
        }
    }

    @Override
    public void deleteById(TKey id, Boolean autoSave) {
        TEntity entity = getById(id, false);
        if (entity != null) {
            delete(entity, autoSave);
        }
    }

}
