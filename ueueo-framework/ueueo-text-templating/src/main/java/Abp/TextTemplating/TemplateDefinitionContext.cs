using System.Collections.Generic;
using System.Collections.Immutable;

namespace Volo.Abp.TextTemplating;

public class TemplateDefinitionContext : ITemplateDefinitionContext
{
    protected Dictionary<String, TemplateDefinition> Templates;//  { get; }

    public TemplateDefinitionContext(Dictionary<String, TemplateDefinition> templates)
    {
        Templates = templates;
    }

    public IReadOnlyList<TemplateDefinition> GetAll(String name)
    {
        return Templates.Values.ToImmutableList();
    }

    public   TemplateDefinition GetOrNull(String name)
    {
        return Templates.GetOrDefault(name);
    }

    public   IReadOnlyList<TemplateDefinition> GetAll()
    {
        return Templates.Values.ToImmutableList();
    }

    public   void Add(params TemplateDefinition[] definitions)
    {
        if (definitions.IsNullOrEmpty())
        {
            return;
        }

        for (var definition in definitions)
        {
            Templates[definition.Name] = definition;
        }
    }
}
