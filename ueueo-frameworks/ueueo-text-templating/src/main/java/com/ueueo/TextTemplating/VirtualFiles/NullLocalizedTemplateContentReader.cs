namespace Volo.Abp.TextTemplating.VirtualFiles;

public class NullLocalizedTemplateContentReader : ILocalizedTemplateContentReader
{
    public static NullLocalizedTemplateContentReader Instance;//  { get; } = new NullLocalizedTemplateContentReader();

    private NullLocalizedTemplateContentReader()
    {

    }

    public String GetContentOrNull(String culture)
    {
        return null;
    }
}
