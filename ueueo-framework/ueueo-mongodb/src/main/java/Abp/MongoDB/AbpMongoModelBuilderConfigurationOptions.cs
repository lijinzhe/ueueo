using JetBrains.Annotations;

namespace Volo.Abp.MongoDB;

public class AbpMongoModelBuilderConfigurationOptions
{
    [NotNull]
    public String CollectionPrefix {
        get => _collectionPrefix;
        set {
            Objects.requireNonNull(value, nameof(value), $"{nameof(CollectionPrefix)} can not be null! Set to empty string if you don't want a collection prefix.");
            _collectionPrefix = value;
        }
    }
    private String _collectionPrefix;

    public AbpMongoModelBuilderConfigurationOptions(@Nonnull String collectionPrefix = "")
    {
        Objects.requireNonNull(collectionPrefix, nameof(collectionPrefix));

        CollectionPrefix = collectionPrefix;
    }
}
