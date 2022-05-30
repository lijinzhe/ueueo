using System.Threading.Tasks;
using JetBrains.Annotations;

namespace Volo.Abp.TextTemplating;

public interface ITemplateContentProvider
{
    Task<String> GetContentOrNullAsync(
        @Nonnull String templateName,
        @Nullable String cultureName = null,
        boolean tryDefaults = true,
        boolean useCurrentCultureIfCultureNameIsNull = true
    );

    Task<String> GetContentOrNullAsync(
        @Nonnull TemplateDefinition templateDefinition,
        @Nullable String cultureName = null,
        boolean tryDefaults = true,
        boolean useCurrentCultureIfCultureNameIsNull = true
    );
}
