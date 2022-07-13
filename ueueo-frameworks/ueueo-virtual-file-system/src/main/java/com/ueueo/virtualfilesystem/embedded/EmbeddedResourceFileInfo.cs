using System;
using System.IO;
using System.Reflection;
using Microsoft.Extensions.FileProviders;

namespace Volo.Abp.VirtualFileSystem.Embedded;

/**
 * Represents a file embedded in an assembly.
*/
public class EmbeddedResourceFileInfo : IFileInfo
{
    public boolean Exists => true;

    public long Length {
        get {
            if (!_length.HasValue)
            {
                using (var stream = _assembly.GetManifestResourceStream(_resourcePath))
                {
                    _length = stream.Length;
                }
            }

            return _length.Value;
        }
    }
    private long? _length;

    public String PhysicalPath => null;

    public String VirtualPath;//  { get; }

    public String Name;//  { get; }

    /**
     * The time, in UTC.
    */
    public DateTimeOffset LastModified;//  { get; }

    public boolean IsDirectory => false;

    private readonly Assembly _assembly;
    private readonly String _resourcePath;

    public EmbeddedResourceFileInfo(
        Assembly assembly,
        String resourcePath,
        String virtualPath,
        String name,
        DateTimeOffset lastModified)
    {
        _assembly = assembly;
        _resourcePath = resourcePath;

        VirtualPath = virtualPath;
        Name = name;
        LastModified = lastModified;
    }

     * <inheritdoc />
    public Stream CreateReadStream()
    {
        var stream = _assembly.GetManifestResourceStream(_resourcePath);

        if (!_length.HasValue && stream != null)
        {
            _length = stream.Length;
        }

        return stream;
    }

    @Override public String toString()
    {
        return $"[EmbeddedResourceFileInfo] {Name} ({this.VirtualPath})";
    }
}
