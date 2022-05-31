using System.Collections.Generic;
using System.Threading.Tasks;

namespace Volo.Abp.Localization;

public interface ILanguageProvider
{
    IReadOnlyList<LanguageInfo>> GetLanguagesAsync();
}
