package com.ueueo.ddd.domain.repositories;

import com.ueueo.ID;
import com.ueueo.ddd.domain.entities.IEntity;
import org.springframework.lang.NonNull;

import java.util.Collection;

/**
 * @author Lee
 * @date 2021-08-20 16:33
 */
public interface IBasicRepository<TEntity extends IEntity> extends IReadOnlyBasicRepository<TEntity> {
    /**
     * Inserts a new entity.
     *
     * @param entity   Inserted entity
     * @param autoSave Set true to automatically save changes to database.
     *                 This is useful for ORMs / database APIs those only save changes with an explicit method call,
     *                 but you need to immediately save changes to the database.
     *                 Default false.
     *
     * @return Inserted entity
     */
    TEntity insert(@NonNull TEntity entity, Boolean autoSave);

    /**
     * Inserts multiple new entities.
     *
     * @param entities Entities to be inserted.
     * @param autoSave Default false.
     */
    void insertMany(Collection<TEntity> entities, Boolean autoSave);

    /**
     * Updates a new entity.
     *
     * @param entity   Update entity
     * @param autoSave Default false.
     *
     * @return entity
     */
    TEntity update(TEntity entity, Boolean autoSave);

    /**
     * Updates multiple new entities.
     *
     * @param entities Entities to be updated.
     * @param autoSave Default false.
     */
    void updateMany(Collection<TEntity> entities, Boolean autoSave);

    /**
     * Deletes an entity.
     *
     * @param entity
     * @param autoSave
     */
    void delete(TEntity entity, Boolean autoSave);

    /**
     * Deletes an entity by primary key.
     *
     * @param id       Primary key of the entity
     * @param autoSave
     */
    void deleteById(ID id, Boolean autoSave);

    /**
     * Deletes multiple entities.
     *
     * @param entities Entities to be deleted.
     * @param autoSave
     */
    void deleteMany(Collection<TEntity> entities, Boolean autoSave);

    /**
     * Deletes multiple entities by primary keys.
     *
     * @param ids      Primary keys of the each entity.
     * @param autoSave Set true to automatically save changes to database.
     *                 This is useful for ORMs / database APIs those only save changes with an explicit method call,
     *                 but you need to immediately save changes to the database.
     */
    void deleteManyByIds(Collection<ID> ids, Boolean autoSave);

}
