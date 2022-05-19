package com.ueueo.ddd.domain.repositories;

import com.ueueo.ID;
import com.ueueo.ddd.domain.entities.IEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-20 16:34
 */
public interface IReadOnlyBasicRepository<TEntity extends IEntity> extends IRepository {

    /**
     * Gets a list of all the entities.
     *
     * @param includeDetails Set true to include all children of this entity
     *
     * @return Entity
     */
    @NonNull
    List<TEntity> getList(Boolean includeDetails);

    /**
     * Gets total count of all entities.
     *
     * @return
     */
    long getCount();

    @NonNull
    List<TEntity> getPagedList(int skipCount, int maxResultCount, String sorting, Boolean includeDetails);

    /**
     * Gets an entity with given primary key or null if not found.
     *
     * @param id             Primary key of the entity to get
     * @param includeDetails Set true to include all children of this entity
     *                       Default true
     *
     * @return Entity or null
     */
    @Nullable
    TEntity findById(ID id, Boolean includeDetails);
}
