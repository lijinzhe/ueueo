using Microsoft.Extensions.FileProviders;

namespace Volo.Abp.VirtualFileSystem;

public interface IDynamicFileProvider : IFileProvider
{
    void AddOrUpdate(IFileInfo fileInfo);

    boolean Delete(String filePath);
}
