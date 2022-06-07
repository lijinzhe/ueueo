using System;
using System.Collections.Generic;
using Microsoft.Extensions.FileProviders;
using Microsoft.Extensions.Primitives;

namespace Volo.Abp.VirtualFileSystem;

public abstract class DictionaryBasedFileProvider : IFileProvider
{
    protected abstract IDictionary<String, IFileInfo> Files;//  { get; }

    public   IFileInfo GetFileInfo(String subpath)
    {
        if (subpath == null)
        {
            return new NotFoundFileInfo(subpath);
        }

        var file = Files.GetOrDefault(NormalizePath(subpath));

        if (file == null)
        {
            return new NotFoundFileInfo(subpath);
        }

        return file;
    }

    public   IDirectoryContents GetDirectoryContents(String subpath)
    {
        var directory = GetFileInfo(subpath);
        if (!directory.IsDirectory)
        {
            return NotFoundDirectoryContents.Singleton;
        }

        var fileList = new List<IFileInfo>();

        var directoryPath = subpath.EnsureEndsWith('/');
        for (var fileInfo in Files.Values)
        {
            var fullPath = fileInfo.GetVirtualOrPhysicalPathOrNull();
            if (!fullPath.StartsWith(directoryPath))
            {
                continue;
            }

            var relativePath = fullPath.SubString(directoryPath.Length);
            if (relativePath.Contains("/"))
            {
                continue;
            }

            fileList.Add(fileInfo);
        }

        return new EnumerableDirectoryContents(fileList);
    }

    public   IChangeToken Watch(String filter)
    {
        return NullChangeToken.Singleton;
    }

    protected   String NormalizePath(String subpath)
    {
        return subpath;
    }
}
