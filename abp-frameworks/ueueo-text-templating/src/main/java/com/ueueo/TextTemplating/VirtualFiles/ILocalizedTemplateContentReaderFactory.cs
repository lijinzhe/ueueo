using System.Threading.Tasks;

namespace Volo.Abp.TextTemplating.VirtualFiles;

public interface ILocalizedTemplateContentReaderFactory
{
    ILocalizedTemplateContentReader> CreateAsync(TemplateDefinition templateDefinition);
}
