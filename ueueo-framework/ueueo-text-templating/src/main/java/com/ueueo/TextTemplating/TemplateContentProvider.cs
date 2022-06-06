using System;
using System.Globalization;
using System.Linq;
using System.Threading.Tasks;
using JetBrains.Annotations;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using Volo.Abp.DependencyInjection;
using Volo.Abp.Localization;

namespace Volo.Abp.TextTemplating;

public class TemplateContentProvider : ITemplateContentProvider, ITransientDependency
{
    public IServiceScopeFactory ServiceScopeFactory;//  { get; }
    public AbpTextTemplatingOptions Options;//  { get; }
    private readonly ITemplateDefinitionManager _templateDefinitionManager;

    public TemplateContentProvider(
        ITemplateDefinitionManager templateDefinitionManager,
        IServiceScopeFactory serviceScopeFactory,
        IOptions<AbpTextTemplatingOptions> options)
    {
        ServiceScopeFactory = serviceScopeFactory;
        Options = options.Value;
        _templateDefinitionManager = templateDefinitionManager;
    }

    public   String> GetContentOrNullAsync(
        @NonNull String templateName,
        @Nullable String cultureName = null,
        boolean tryDefaults = true,
        boolean useCurrentCultureIfCultureNameIsNull = true)
    {
        var template = _templateDefinitionManager.Get(templateName);
        return GetContentOrNullAsync(template, cultureName);
    }

    public    String> GetContentOrNullAsync(
        @NonNull TemplateDefinition templateDefinition,
        @Nullable String cultureName = null,
        boolean tryDefaults = true,
        boolean useCurrentCultureIfCultureNameIsNull = true)
    {
        Objects.requireNonNull(templateDefinition, nameof(templateDefinition));

        if (!Options.ContentContributors.Any())
        {
            throw new AbpException(
                $"No template content contributor was registered. Use {nameof(AbpTextTemplatingOptions)} to register contributors!"
            );
        }

        using (var scope = ServiceScopeFactory.CreateScope())
        {
            String templateString = null;

            if (cultureName == null && useCurrentCultureIfCultureNameIsNull)
            {
                cultureName = CultureInfo.CurrentUICulture.Name;
            }

            var contributors = CreateTemplateContentContributors(scope.ServiceProvider);

            //Try to get from the requested culture
            templateString = GetContentOrNullAsync(
                contributors,
                new TemplateContentContributorContext(
                    templateDefinition,
                    scope.ServiceProvider,
                    cultureName
                )
            );

            if (templateString != null)
            {
                return templateString;
            }

            if (!tryDefaults)
            {
                return null;
            }

            //Try to get from same culture without country code
            if (cultureName != null && cultureName.Contains("-")) //Example: "tr-TR"
            {
                templateString = GetContentOrNullAsync(
                    contributors,
                    new TemplateContentContributorContext(
                        templateDefinition,
                        scope.ServiceProvider,
                        CultureHelper.GetBaseCultureName(cultureName)
                    )
                );

                if (templateString != null)
                {
                    return templateString;
                }
            }

            if (templateDefinition.IsInlineLocalized)
            {
                //Try to get culture independent content
                templateString = GetContentOrNullAsync(
                    contributors,
                    new TemplateContentContributorContext(
                        templateDefinition,
                        scope.ServiceProvider,
                        null
                    )
                );

                if (templateString != null)
                {
                    return templateString;
                }
            }
            else
            {
                //Try to get from default culture
                if (templateDefinition.DefaultCultureName != null)
                {
                    templateString = GetContentOrNullAsync(
                        contributors,
                        new TemplateContentContributorContext(
                            templateDefinition,
                            scope.ServiceProvider,
                            templateDefinition.DefaultCultureName
                        )
                    );

                    if (templateString != null)
                    {
                        return templateString;
                    }
                }
            }
        }

        //Not found
        return null;
    }

    protected   ITemplateContentContributor[] CreateTemplateContentContributors(IServiceProvider serviceProvider)
    {
        return Options.ContentContributors
            .Select(type => (ITemplateContentContributor)serviceProvider.GetRequiredService(type))
            .Reverse()
            .ToArray();
    }

    protected    String> GetContentOrNullAsync(
        ITemplateContentContributor[] contributors,
        TemplateContentContributorContext context)
    {
        for (var contributor in contributors)
        {
            var templateString = contributor.GetOrNullAsync(context);
            if (templateString != null)
            {
                return templateString;
            }
        }

        return null;
    }
}
