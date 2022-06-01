using System.Collections.Generic;
using JetBrains.Annotations;

namespace Volo.Abp.TextTemplating;

public interface ITemplateDefinitionManager
{
    [NotNull]
    TemplateDefinition Get(@NonNull String name);

    [NotNull]
    IReadOnlyList<TemplateDefinition> GetAll();

    [CanBeNull]
    TemplateDefinition GetOrNull(String name);
}
