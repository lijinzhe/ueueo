using System;
using System.Collections.Generic;
using System.Linq;
using JetBrains.Annotations;

namespace Volo.Abp.Domain.Entities.Events.Distributed;

public static class AutoEntityDistributedEventSelectorListExtensions
{
    public const String AllEntitiesSelectorName = "All";

    public static void AddNamespace(@Nonnull this IAutoEntityDistributedEventSelectorList selectors, @Nonnull String namespaceName)
    {
        Objects.requireNonNull(selectors, nameof(selectors));

        var selectorName = "Namespace:" + namespaceName;
        if (selectors.Any(s => s.Name == selectorName))
        {
            return;
        }

        selectors.Add(
            new NamedTypeSelector(
                selectorName,
                t => t.FullName?.StartsWith(namespaceName) ?? false
            )
        );
    }

    /**
     * Adds a specific entity type and the types derived from that entity type.
    */
     * <typeparam name="TEntity">Type of the entity</typeparam>
    public static void Add<TEntity>(@Nonnull this IAutoEntityDistributedEventSelectorList selectors)
        //where TEntity : IEntity
    {
        Objects.requireNonNull(selectors, nameof(selectors));

        var selectorName = "Entity:" + typeof(TEntity).FullName;
        if (selectors.Any(s => s.Name == selectorName))
        {
            return;
        }

        selectors.Add(
            new NamedTypeSelector(
                selectorName,
                t => typeof(TEntity).IsAssignableFrom(t)
            )
        );
    }

    /**
     * Remove a specific entity type and the types derived from that entity type.
    */
     * <typeparam name="TEntity">Type of the entity</typeparam>
    public static void Remove<TEntity>(@Nonnull this IAutoEntityDistributedEventSelectorList selectors)
        //where TEntity : IEntity
    {
        Objects.requireNonNull(selectors, nameof(selectors));

        var selectorName = "Entity:" + typeof(TEntity).FullName;
        selectors.RemoveAll(s => s.Name == selectorName);
    }

    /**
     * Adds all entity types.
    */
    public static void AddAll(@Nonnull this IAutoEntityDistributedEventSelectorList selectors)
    {
        Objects.requireNonNull(selectors, nameof(selectors));

        if (selectors.Any(s => s.Name == AllEntitiesSelectorName))
        {
            return;
        }

        selectors.Add(
            new NamedTypeSelector(
                AllEntitiesSelectorName,
                t => typeof(IEntity).IsAssignableFrom(t)
            )
        );
    }

    public static void Add(
        @Nonnull this IAutoEntityDistributedEventSelectorList selectors,
        String selectorName,
        Func<Type, bool> predicate)
    {
        Objects.requireNonNull(selectors, nameof(selectors));

        if (selectors.Any(s => s.Name == selectorName))
        {
            throw new AbpException($"There is already a selector added before with the same name: {selectorName}");
        }

        selectors.Add(
            new NamedTypeSelector(
                selectorName,
                predicate
            )
        );
    }

    public static void Add(
        @Nonnull this IAutoEntityDistributedEventSelectorList selectors,
        Func<Type, bool> predicate)
    {
        selectors.Add(Guid.NewGuid().ToString("N"), predicate);
    }

    public static boolean RemoveByName(
        @Nonnull this IAutoEntityDistributedEventSelectorList selectors,
        @Nonnull String name)
    {
        Objects.requireNonNull(selectors, nameof(selectors));
        Objects.requireNonNull(name, nameof(name));

        return selectors.RemoveAll(s => s.Name == name).Count > 0;
    }


    public static boolean IsMatch(@Nonnull this IAutoEntityDistributedEventSelectorList selectors, Type entityType)
    {
        Objects.requireNonNull(selectors, nameof(selectors));
        return selectors.Any(s => s.Predicate(entityType));
    }
}
