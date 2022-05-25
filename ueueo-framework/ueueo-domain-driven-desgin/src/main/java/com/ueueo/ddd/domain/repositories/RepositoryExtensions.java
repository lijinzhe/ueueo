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
    public static <TEntity extends IEntity> TEntity get(IReadOnlyRepository<TEntity> repository,
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
    public static <TEntity extends IEntity> List<TEntity> getList(IReadOnlyRepository<TEntity> repository,
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
    public static <TEntity extends IEntity> void deleteMany(IBasicRepository<TEntity> repository, Predicate<TEntity> predicate, Boolean autoSave) {
        repository.getList(false).stream().filter(predicate)
                .collect(Collectors.toList())
                .forEach(entity -> repository.delete(entity, autoSave));
    }

    public static <T extends IEntity> boolean Contains(@NonNull IReadOnlyRepository<T> repository, @NonNull T item) {
        return false;
    }

    public static <T extends IEntity> boolean Any(@NonNull IReadOnlyRepository<T> repository) {
        return false;
    }

    public static <T extends IEntity> boolean Any(
            @NonNull IReadOnlyRepository<T> repository,
            @NonNull Predicate<T> predicate
    ) {
        return false;
    }

    public static <T extends IEntity> boolean All(
            @NonNull IReadOnlyRepository<T> repository,
            @NonNull Predicate<T> predicate
    ) {
        return false;
    }

    public static <T extends IEntity> int Count(
            @NonNull IReadOnlyRepository<T> repository
    ) {
        return 0;
    }

    public static <T extends IEntity> int Count(
            @NonNull IReadOnlyRepository<T> repository,
            @NonNull Predicate<T> predicate
    ) {
        return 0;
    }

    public static <T extends IEntity> long LongCount(
            @NonNull IReadOnlyRepository<T> repository
    ) {
        return 0;
    }

    public static <T extends IEntity> long LongCount(
            @NonNull IReadOnlyRepository<T> repository,
            @NonNull Predicate<T> predicate
    ) {
        return 0;
    }

    public static <T extends IEntity> T First(
            @NonNull IReadOnlyRepository<T> repository
    ) {
        return null;
    }

    public static <T extends IEntity> T First(
            @NonNull IReadOnlyRepository<T> repository,
            @NonNull Predicate<T> predicate
    ) {
        return null;
    }

    public static <T extends IEntity> T FirstOrDefault(
            @NonNull IReadOnlyRepository<T> repository
    ) {
        return null;
    }

    public static <T extends IEntity> T FirstOrDefault(
            @NonNull IReadOnlyRepository<T> repository,
            @NonNull Predicate<T> predicate
    ) {
        return null;
    }

    public static <T extends IEntity> T Last(
            @NonNull IReadOnlyRepository<T> repository
    ) {
        return null;
    }

    public static <T extends IEntity> T Last(
            @NonNull IReadOnlyRepository<T> repository,
            @NonNull Predicate<T> predicate
    ) {
        return null;
    }

    public static <T extends IEntity> T LastOrDefault(
            @NonNull IReadOnlyRepository<T> repository
    ) {
        return null;
    }

    public static <T extends IEntity> T LastOrDefault(
            @NonNull IReadOnlyRepository<T> repository,
            @NonNull Predicate<T> predicate
    ) {
        return null;
    }

    public static <T extends IEntity> T Single(
            @NonNull IReadOnlyRepository<T> repository
    ) {
        return null;
    }

    public static <T extends IEntity> T Single(
            @NonNull IReadOnlyRepository<T> repository,
            @NonNull Predicate<T> predicate
    ) {
        return null;
    }

    public static <T extends IEntity> T SingleOrDefault(
            @NonNull IReadOnlyRepository<T> repository
    ) {
        return null;
    }

    public static <T extends IEntity> T SingleOrDefault(
            @NonNull IReadOnlyRepository<T> repository,
            @NonNull Predicate<T> predicate
    ) {
        return null;
    }

    public static <T extends IEntity> T Min(
            @NonNull IReadOnlyRepository<T> repository
    ) {
        return null;
    }

    public static <T extends IEntity> T Max(
            @NonNull IReadOnlyRepository<T> repository
    ) {
        return null;
    }

}
