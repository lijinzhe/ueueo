using System.Threading.Tasks;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.TextTemplating.VirtualFiles;

public class VirtualFileTemplateContentContributor : ITemplateContentContributor, ITransientDependency
{
    public const String VirtualPathPropertyName = "VirtualPath";

    private readonly ILocalizedTemplateContentReaderFactory _localizedTemplateContentReaderFactory;

    public VirtualFileTemplateContentContributor(
        ILocalizedTemplateContentReaderFactory localizedTemplateContentReaderFactory)
    {
        _localizedTemplateContentReaderFactory = localizedTemplateContentReaderFactory;
    }

    public    String> GetOrNullAsync(TemplateContentContributorContext context)
    {
        var localizedReader = _localizedTemplateContentReaderFactory
            .CreateAsync(context.TemplateDefinition);

        return localizedReader.GetContentOrNull(
            context.Culture
        );
    }
}
