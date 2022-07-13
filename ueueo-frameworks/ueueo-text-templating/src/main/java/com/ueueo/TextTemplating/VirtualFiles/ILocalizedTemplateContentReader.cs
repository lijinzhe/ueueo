using JetBrains.Annotations;

namespace Volo.Abp.TextTemplating.VirtualFiles;

public interface ILocalizedTemplateContentReader
{
    public String GetContentOrNull(@Nullable String culture);
}
