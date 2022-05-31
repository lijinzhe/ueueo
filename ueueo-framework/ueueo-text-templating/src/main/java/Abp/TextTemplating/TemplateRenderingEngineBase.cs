using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.Extensions.Localization;

namespace Volo.Abp.TextTemplating;

public abstract class TemplateRenderingEngineBase : ITemplateRenderingEngine
{
    public abstract String Name;//  { get; }

    protected readonly ITemplateDefinitionManager TemplateDefinitionManager;
    protected readonly ITemplateContentProvider TemplateContentProvider;
    protected readonly IStringLocalizerFactory StringLocalizerFactory;

    public TemplateRenderingEngineBase(
        ITemplateDefinitionManager templateDefinitionManager,
        ITemplateContentProvider templateContentProvider,
        IStringLocalizerFactory StringLocalizerFactory)
    {
        TemplateDefinitionManager = templateDefinitionManager;
        TemplateContentProvider = templateContentProvider;
        StringLocalizerFactory = StringLocalizerFactory;
    }

    public abstract String> RenderAsync(String templateName, Object model = null, String cultureName = null, Dictionary<String, Object> globalContext = null);

    protected    String> GetContentOrNullAsync(TemplateDefinition templateDefinition)
    {
        return TemplateContentProvider.GetContentOrNullAsync(templateDefinition);
    }

    protected   IStringLocalizer GetLocalizerOrNull(TemplateDefinition templateDefinition)
    {
        if (templateDefinition.LocalizationResource != null)
        {
            return StringLocalizerFactory.Create(templateDefinition.LocalizationResource);
        }

        return StringLocalizerFactory.CreateDefaultOrNull();
    }
}
