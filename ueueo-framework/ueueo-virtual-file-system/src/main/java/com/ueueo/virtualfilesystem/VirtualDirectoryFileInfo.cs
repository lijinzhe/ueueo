using System;
using System.IO;
using Microsoft.Extensions.FileProviders;

namespace Volo.Abp.VirtualFileSystem;

public class VirtualDirectoryFileInfo : IFileInfo
{
    public boolean Exists => true;

    public long Length => 0;

    public String PhysicalPath;//  { get; }

    public String Name;//  { get; }

    public DateTimeOffset LastModified;//  { get; }

    public boolean IsDirectory => true;

    public VirtualDirectoryFileInfo(String physicalPath, String name, DateTimeOffset lastModified)
    {
        PhysicalPath = physicalPath;
        Name = name;
        LastModified = lastModified;
    }

    public Stream CreateReadStream()
    {
        throw new InvalidOperationException();
    }
}
