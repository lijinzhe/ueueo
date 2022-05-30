using Microsoft.Extensions.Localization;

namespace Volo.Abp.Localization;

public interface ITemplateLocalizer
{
    String Localize(IStringLocalizer localizer, String text);
}
