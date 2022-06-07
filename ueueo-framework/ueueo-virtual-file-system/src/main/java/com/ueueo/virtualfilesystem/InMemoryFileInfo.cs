using System;
using System.IO;
using Microsoft.Extensions.FileProviders;

namespace Volo.Abp.VirtualFileSystem;

public class InMemoryFileInfo : IFileInfo
{
    public boolean Exists => true;

    public long Length => _fileContent.Length;

    public String PhysicalPath => null;

    public String Name;//  { get; }

    public DateTimeOffset LastModified;//  { get; }

    public boolean IsDirectory => false;

    private readonly byte[] _fileContent;

    public String DynamicPath;//  { get; }

    public InMemoryFileInfo(String dynamicPath, byte[] fileContent, String name)
    {
        DynamicPath = dynamicPath;
        Name = name;
        _fileContent = fileContent;
        LastModified = DateTimeOffset.Now;
    }

    public Stream CreateReadStream()
    {
        return new MemoryStream(_fileContent, false);
    }
}
