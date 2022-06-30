package com.ueueo.ddd.domain.entities.events.distributed;

import com.ueueo.SystemException;
import com.ueueo.NamedTypeSelector;
import com.ueueo.ddd.domain.entities.IEntity;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

public class AutoEntityDistributedEventSelectorList extends ArrayList<NamedTypeSelector> implements IAutoEntityDistributedEventSelectorList {

    public final String AllEntitiesSelectorName = "All";

    public void addNamespace(@NonNull String namespaceName) {
        Objects.requireNonNull(namespaceName);

        String selectorName = "Namespace:" + namespaceName;

        if (stream().anyMatch(s -> s.getName().equals(selectorName))) {
            return;
        }

        add(new NamedTypeSelector(selectorName, t -> t.getName().startsWith(namespaceName)));
    }

    /**
     * Adds a specific entity type and the types derived from that entity type.
     *
     * <typeparam name="TEntity">Type of the entity</typeparam>
     */
    public <TEntity extends IEntity> void add(Class<TEntity> entityType) {

        String selectorName = "Entity:" + entityType.getName();
        if (stream().anyMatch(s -> s.getName().equals(selectorName))) {
            return;
        }

        add(new NamedTypeSelector(selectorName, entityType::isAssignableFrom));
    }

    /**
     * Remove a specific entity type and the types derived from that entity type.
     *
     * <typeparam name="TEntity">Type of the entity</typeparam>
     */
    public <TEntity extends IEntity> void remove(Class<TEntity> entityType) {

        String selectorName = "Entity:" + entityType.getName();
        removeIf(s -> s.getName().equals(selectorName));
    }

    /**
     * Adds all entity types.
     */
    public void addAll() {

        if (stream().anyMatch(s -> s.getName().equals(AllEntitiesSelectorName))) {
            return;
        }
        add(new NamedTypeSelector(AllEntitiesSelectorName, IEntity.class::isAssignableFrom));

    }

    public void add(String selectorName, Function<Class<?>, Boolean> predicate) {
        if (stream().anyMatch(s -> s.getName().equals(selectorName))) {
            throw new SystemException(String.format("There is already a selector added before with the same name: %s", selectorName));
        }
        add(new NamedTypeSelector(selectorName, predicate));
    }

    public void add(Function<Class<?>, Boolean> predicate) {

        add(UUID.randomUUID().toString(), predicate);
    }

    public boolean removeByName(@NonNull String name) {
        Objects.requireNonNull(name);
        return removeIf(s -> name.equals(s.getName()));
    }

    public boolean isMatch(Class<?> entityType) {
        return stream().anyMatch(s -> s.getPredicate().apply(entityType));
    }
}
