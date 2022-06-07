using System.Threading.Tasks;
using JetBrains.Annotations;

namespace Volo.Abp.TextTemplating;

public interface ITemplateContentProvider
{
    String> GetContentOrNullAsync(
        @NonNull String templateName,
        @Nullable String cultureName = null,
        boolean tryDefaults = true,
        boolean useCurrentCultureIfCultureNameIsNull = true
    );

    String> GetContentOrNullAsync(
        @NonNull TemplateDefinition templateDefinition,
        @Nullable String cultureName = null,
        boolean tryDefaults = true,
        boolean useCurrentCultureIfCultureNameIsNull = true
    );
}
