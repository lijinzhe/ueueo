using JetBrains.Annotations;
using Microsoft.Extensions.FileProviders;

namespace Volo.Abp.VirtualFileSystem.Physical;

public class PhysicalVirtualFileSetInfo : VirtualFileSetInfo
{
    public String Root;//  { get; }

    public PhysicalVirtualFileSetInfo(
        @Nonnull IFileProvider fileProvider,
        @Nonnull String root
        )
        : base(fileProvider)
    {
        Root = Check.NotNullOrWhiteSpace(root, nameof(root));
    }
}
