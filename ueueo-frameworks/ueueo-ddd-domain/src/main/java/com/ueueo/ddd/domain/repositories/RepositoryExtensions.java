package com.ueueo.ddd.domain.repositories;

import com.ueueo.ddd.domain.entities.IEntity;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-25 21:32
 */
public class RepositoryExtensions {
    /**
     * Get a single entity by the given <paramref name="predicate"/>.
     *
     * @param predicate      A condition to find the entity
     * @param includeDetails Set true to include all children of this entity
     *
     * @return It returns null if there is no entity with the given <paramref name="predicate"/>.
     * It throws <see cref="InvalidOperationException"/> if there are multiple entities with the given <paramref name="predicate"/>.
     */
    public static <TEntity extends IEntity<TKey>,TKey> TEntity get(IReadOnlyRepository<TEntity,TKey> repository,
                                                        Predicate<TEntity> predicate,
                                                        Boolean includeDetails) {
        return repository.getList(includeDetails).stream().filter(predicate).findFirst().orElse(null);
    }

    /**
     * Gets a list of entities by the given <paramref name="predicate"/>.
     *
     * @param predicate      A condition to filter the entities
     * @param includeDetails
     *
     * @return
     */
    public static <TEntity extends IEntity<TKey>,TKey> List<TEntity> getList(IReadOnlyRepository<TEntity,TKey> repository,
                                                                  Predicate<TEntity> predicate,
                                                                  Boolean includeDetails) {
        return repository.getList(includeDetails).stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * Deletes many entities by the given <paramref name="predicate"/>.
     * Please note: This may cause major performance problems if there are too many entities returned for a
     * given predicate and the database provider doesn't have a way to efficiently delete many entities.
     *
     * @param predicate A condition to filter entities
     * @param autoSave
     */
    public static <TEntity extends IEntity<TKey>,TKey> void deleteMany(IBasicRepository<TEntity,TKey> repository, Predicate<TEntity> predicate, Boolean autoSave) {
        repository.getList(false).stream().filter(predicate)
                .collect(Collectors.toList())
                .forEach(entity -> repository.delete(entity, autoSave));
    }

    public static <TEntity extends IEntity<TKey>,TKey> boolean contains(@NonNull IReadOnlyRepository<TEntity,TKey> repository, @NonNull TEntity item) {
        return false;
    }

    public static <TEntity extends IEntity<TKey>,TKey> boolean any(@NonNull IReadOnlyRepository<TEntity,TKey> repository) {
        return false;
    }

    public static <TEntity extends IEntity<TKey>,TKey> boolean any(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository,
            @NonNull Predicate<TEntity> predicate
    ) {
        return false;
    }

    public static <TEntity extends IEntity<TKey>,TKey> boolean all(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository,
            @NonNull Predicate<TEntity> predicate
    ) {
        return false;
    }

    public static <TEntity extends IEntity<TKey>,TKey> int count(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository
    ) {
        return 0;
    }

    public static <TEntity extends IEntity<TKey>,TKey> int count(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository,
            @NonNull Predicate<TEntity> predicate
    ) {
        return 0;
    }

    public static <TEntity extends IEntity<TKey>,TKey> long longCount(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository
    ) {
        return 0;
    }

    public static <TEntity extends IEntity<TKey>,TKey> long longCount(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository,
            @NonNull Predicate<TEntity> predicate
    ) {
        return 0;
    }

    public static <TEntity extends IEntity<TKey>,TKey> TEntity first(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository
    ) {
        return null;
    }

    public static <TEntity extends IEntity<TKey>,TKey> TEntity first(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository,
            @NonNull Predicate<TEntity> predicate
    ) {
        return null;
    }

    public static <TEntity extends IEntity<TKey>,TKey> TEntity firstOrDefault(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository
    ) {
        return null;
    }

    public static <TEntity extends IEntity<TKey>,TKey> TEntity firstOrDefault(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository,
            @NonNull Predicate<TEntity> predicate
    ) {
        return null;
    }

    public static <TEntity extends IEntity<TKey>,TKey> TEntity last(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository
    ) {
        return null;
    }

    public static <TEntity extends IEntity<TKey>,TKey> TEntity last(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository,
            @NonNull Predicate<TEntity> predicate
    ) {
        return null;
    }

    public static <TEntity extends IEntity<TKey>,TKey> TEntity lastOrDefault(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository
    ) {
        return null;
    }

    public static <TEntity extends IEntity<TKey>,TKey> TEntity lastOrDefault(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository,
            @NonNull Predicate<TEntity> predicate
    ) {
        return null;
    }

    public static <TEntity extends IEntity<TKey>,TKey> TEntity single(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository
    ) {
        return null;
    }

    public static <TEntity extends IEntity<TKey>,TKey> TEntity single(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository,
            @NonNull Predicate<TEntity> predicate
    ) {
        return null;
    }

    public static <TEntity extends IEntity<TKey>,TKey> TEntity singleOrDefault(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository
    ) {
        return null;
    }

    public static <TEntity extends IEntity<TKey>,TKey> TEntity singleOrDefault(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository,
            @NonNull Predicate<TEntity> predicate
    ) {
        return null;
    }

    public static <TEntity extends IEntity<TKey>,TKey> TEntity min(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository
    ) {
        return null;
    }

    public static <TEntity extends IEntity<TKey>,TKey> TEntity max(
            @NonNull IReadOnlyRepository<TEntity,TKey> repository
    ) {
        return null;
    }

}
