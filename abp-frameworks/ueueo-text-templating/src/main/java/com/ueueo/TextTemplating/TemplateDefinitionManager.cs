using System;
using System.Collections.Generic;
using System.Collections.Immutable;
using System.Linq;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.TextTemplating;

public class TemplateDefinitionManager : ITemplateDefinitionManager, ISingletonDependency
{
    protected Lazy<IDictionary<String, TemplateDefinition>> TemplateDefinitions;//  { get; }

    protected AbpTextTemplatingOptions Options;//  { get; }

    protected IServiceProvider ServiceProvider;//  { get; }

    public TemplateDefinitionManager(
        IOptions<AbpTextTemplatingOptions> options,
        IServiceProvider serviceProvider)
    {
        ServiceProvider = serviceProvider;
        Options = options.Value;

        TemplateDefinitions =
            new Lazy<IDictionary<String, TemplateDefinition>>(CreateTextTemplateDefinitions, true);
    }

    public   TemplateDefinition Get(String name)
    {
        Objects.requireNonNull(name, nameof(name));

        var template = GetOrNull(name);

        if (template == null)
        {
            throw new AbpException("Undefined template: " + name);
        }

        return template;
    }

    public   IReadOnlyList<TemplateDefinition> GetAll()
    {
        return TemplateDefinitions.Value.Values.ToImmutableList();
    }

    public   TemplateDefinition GetOrNull(String name)
    {
        return TemplateDefinitions.Value.GetOrDefault(name);
    }

    protected   IDictionary<String, TemplateDefinition> CreateTextTemplateDefinitions()
    {
        var templates = new Dictionary<String, TemplateDefinition>();

        using (var scope = ServiceProvider.CreateScope())
        {
            var providers = Options
                .DefinitionProviders
                .Select(p => scope.ServiceProvider.GetRequiredService(p) as ITemplateDefinitionProvider)
                .ToList();

            var context = new TemplateDefinitionContext(templates);

            for (var provider in providers)
            {
                provider.PreDefine(context);
            }

            for (var provider in providers)
            {
                provider.Define(context);
            }

            for (var provider in providers)
            {
                provider.PostDefine(context);
            }
        }

        return templates;
    }
}
