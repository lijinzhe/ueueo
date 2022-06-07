using JetBrains.Annotations;
using Microsoft.Extensions.FileProviders;

namespace Volo.Abp.VirtualFileSystem;

public class VirtualFileSetInfo
{
    public IFileProvider FileProvider;//  { get; }

    public VirtualFileSetInfo(@NonNull IFileProvider fileProvider)
    {
        FileProvider = Objects.requireNonNull(fileProvider, nameof(fileProvider));
    }
}
