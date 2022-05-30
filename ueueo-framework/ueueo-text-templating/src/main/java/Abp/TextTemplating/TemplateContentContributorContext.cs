using System;
using JetBrains.Annotations;

namespace Volo.Abp.TextTemplating;

public class TemplateContentContributorContext
{
    [NotNull]
    public TemplateDefinition TemplateDefinition;//  { get; }

    [NotNull]
    public IServiceProvider ServiceProvider;//  { get; }

    [CanBeNull]
    public String Culture;//  { get; }

    public TemplateContentContributorContext(
        @Nonnull TemplateDefinition templateDefinition,
        @Nonnull IServiceProvider serviceProvider,
        @Nullable String culture)
    {
        TemplateDefinition = Objects.requireNonNull(templateDefinition, nameof(templateDefinition));
        ServiceProvider = Objects.requireNonNull(serviceProvider, nameof(serviceProvider));
        Culture = culture;
    }
}
