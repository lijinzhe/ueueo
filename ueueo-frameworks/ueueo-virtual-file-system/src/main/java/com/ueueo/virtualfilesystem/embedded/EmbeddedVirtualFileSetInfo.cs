using System.Reflection;
using Microsoft.Extensions.FileProviders;

namespace Volo.Abp.VirtualFileSystem.Embedded;

public class EmbeddedVirtualFileSetInfo : VirtualFileSetInfo
{
    public Assembly Assembly;//  { get; }

    public String BaseFolder;//  { get; }

    public EmbeddedVirtualFileSetInfo(
        IFileProvider fileProvider,
        Assembly assembly,
        String baseFolder = null)
        : base(fileProvider)
    {
        Assembly = assembly;
        BaseFolder = baseFolder;
    }
}
