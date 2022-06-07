using Volo.Abp.DependencyInjection;

namespace Volo.Abp.TextTemplating;

public abstract class TemplateDefinitionProvider : ITemplateDefinitionProvider, ITransientDependency
{
    public   void PreDefine(ITemplateDefinitionContext context)
    {

    }

    public abstract void Define(ITemplateDefinitionContext context);

    public   void PostDefine(ITemplateDefinitionContext context)
    {

    }
}
