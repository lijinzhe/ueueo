using System.Collections.Generic;
using System.Globalization;
using System.Threading.Tasks;
using JetBrains.Annotations;

namespace Volo.Abp.TextTemplating;

public interface ITemplateRenderingEngine
{
    String Name;//  { get; }

    /**
     * Renders a text template.
    *
     * <param name="templateName">The template name</param>
     * <param name="model">An optional model object that is used in the template</param>
     * <param name="cultureName">Culture name. Uses the <see cref="CultureInfo.CurrentUICulture"/> if not specified</param>
     * <param name="globalContext">A dictionary which can be used to import global objects to the template</param>
     * <returns></returns>
     */
    String> RenderAsync(
        @Nonnull String templateName,
        @Nullable Object model = null,
        @Nullable String cultureName = null,
        @Nullable Dictionary<String, Object> globalContext = null
    );
}
