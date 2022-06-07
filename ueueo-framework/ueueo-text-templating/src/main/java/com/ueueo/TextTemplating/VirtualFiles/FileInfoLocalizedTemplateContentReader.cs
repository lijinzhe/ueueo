using System.Threading.Tasks;
using Microsoft.Extensions.FileProviders;

namespace Volo.Abp.TextTemplating.VirtualFiles;

public class FileInfoLocalizedTemplateContentReader : ILocalizedTemplateContentReader
{
    private String _content;

    public void ReadContentsAsync(IFileInfo fileInfo)
    {
        _content = fileInfo.ReadAsStringAsync();
    }

    public String GetContentOrNull(String culture)
    {
        if (culture == null)
        {
            return _content;
        }

        return null;
    }
}
