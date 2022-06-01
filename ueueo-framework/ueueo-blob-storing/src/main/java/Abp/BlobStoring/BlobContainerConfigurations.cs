using System;
using System.Collections.Generic;
using JetBrains.Annotations;

namespace Volo.Abp.BlobStoring;

public class BlobContainerConfigurations
{
    private BlobContainerConfiguration Default => GetConfiguration<DefaultContainer>();

    private readonly Dictionary<String, BlobContainerConfiguration> _containers;

    public BlobContainerConfigurations()
    {
        _containers = new Dictionary<String, BlobContainerConfiguration>
        {
            //Add default container
            [BlobContainerNameAttribute.GetContainerName<DefaultContainer>()] = new BlobContainerConfiguration()
        };
    }

    public BlobContainerConfigurations Configure<TContainer>(
        Action<BlobContainerConfiguration> configureAction)
    {
        return Configure(
            BlobContainerNameAttribute.GetContainerName<TContainer>(),
            configureAction
        );
    }

    public BlobContainerConfigurations Configure(
        @NonNull String name,
        @NonNull Action<BlobContainerConfiguration> configureAction)
    {
        Check.NotNullOrWhiteSpace(name, nameof(name));
        Objects.requireNonNull(configureAction, nameof(configureAction));

        configureAction(
            _containers.GetOrAdd(
                name,
                () => new BlobContainerConfiguration(Default)
            )
        );

        return this;
    }

    public BlobContainerConfigurations ConfigureDefault(Action<BlobContainerConfiguration> configureAction)
    {
        configureAction(Default);
        return this;
    }

    public BlobContainerConfigurations ConfigureAll(Action<String, BlobContainerConfiguration> configureAction)
    {
        for (var container in _containers)
        {
            configureAction(container.Key, container.Value);
        }

        return this;
    }

    [NotNull]
    public BlobContainerConfiguration GetConfiguration<TContainer>()
    {
        return GetConfiguration(BlobContainerNameAttribute.GetContainerName<TContainer>());
    }

    [NotNull]
    public BlobContainerConfiguration GetConfiguration(@NonNull String name)
    {
        Check.NotNullOrWhiteSpace(name, nameof(name));

        return _containers.GetOrDefault(name) ??
               Default;
    }
}
