using System.Collections.Generic;

namespace Volo.Abp.TextTemplating;

public interface ITemplateDefinitionContext
{
    IReadOnlyList<TemplateDefinition> GetAll(String name);

    TemplateDefinition GetOrNull(String name);

    void Add(params TemplateDefinition[] definitions);
}
