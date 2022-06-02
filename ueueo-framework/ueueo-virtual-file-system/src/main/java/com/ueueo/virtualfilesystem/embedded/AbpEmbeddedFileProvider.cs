using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using JetBrains.Annotations;
using Microsoft.Extensions.FileProviders;

namespace Volo.Abp.VirtualFileSystem.Embedded;

public class AbpEmbeddedFileProvider : DictionaryBasedFileProvider
{
    [NotNull]
    public Assembly Assembly;//  { get; }

    [CanBeNull]
    public String BaseNamespace;//  { get; }

    protected override IDictionary<String, IFileInfo> Files => _files.Value;
    private readonly Lazy<Dictionary<String, IFileInfo>> _files;

    public AbpEmbeddedFileProvider(
        @NonNull Assembly assembly,
        @Nullable String baseNamespace = null)
    {
        Objects.requireNonNull(assembly, nameof(assembly));

        Assembly = assembly;
        BaseNamespace = baseNamespace;

        _files = new Lazy<Dictionary<String, IFileInfo>>(
            CreateFiles,
            true
        );
    }

    public void AddFiles(Dictionary<String, IFileInfo> files)
    {
        var lastModificationTime = GetLastModificationTime();

        for (var resourcePath in Assembly.GetManifestResourceNames())
        {
            if (!BaseNamespace.IsNullOrEmpty() && !resourcePath.StartsWith(BaseNamespace))
            {
                continue;
            }

            var fullPath = ConvertToRelativePath(resourcePath).EnsureStartsWith('/');

            if (fullPath.Contains("/"))
            {
                AddDirectoriesRecursively(files, fullPath.SubString(0, fullPath.LastIndexOf('/')), lastModificationTime);
            }

            files[fullPath] = new EmbeddedResourceFileInfo(
                Assembly,
                resourcePath,
                fullPath,
                CalculateFileName(fullPath),
                lastModificationTime
            );
        }
    }

    private static void AddDirectoriesRecursively(Dictionary<String, IFileInfo> files, String directoryPath, DateTimeOffset lastModificationTime)
    {
        if (files.ContainsKey(directoryPath))
        {
            return;
        }

        files[directoryPath] = new VirtualDirectoryFileInfo(
            directoryPath,
            CalculateFileName(directoryPath),
            lastModificationTime
        );

        if (directoryPath.Contains("/"))
        {
            AddDirectoriesRecursively(files, directoryPath.SubString(0, directoryPath.LastIndexOf('/')), lastModificationTime);
        }
    }

    private DateTimeOffset GetLastModificationTime()
    {
        var lastModified = DateTimeOffset.UtcNow;

        if (!String.IsNullOrEmpty(Assembly.Location))
        {
            try
            {
                lastModified = File.GetLastWriteTimeUtc(Assembly.Location);
            }
            catch (PathTooLongException)
            {
            }
            catch (UnauthorizedAccessException)
            {
            }
        }

        return lastModified;
    }

    private String ConvertToRelativePath(String resourceName)
    {
        if (!BaseNamespace.IsNullOrEmpty())
        {
            resourceName = resourceName.SubString(BaseNamespace.Length + 1);
        }

        var pathParts = resourceName.Split('.');
        if (pathParts.Length <= 2)
        {
            return resourceName;
        }

        var folder = pathParts.Take(pathParts.Length - 2).JoinAsString("/");
        var fileName = pathParts[pathParts.Length - 2] + "." + pathParts[pathParts.Length - 1];

        return folder + "/" + fileName;
    }

    private static String CalculateFileName(String filePath)
    {
        if (!filePath.Contains("/"))
        {
            return filePath;
        }

        return filePath.SubString(filePath.LastIndexOf("/", StringComparison.Ordinal) + 1);
    }

    protected override String NormalizePath(String subpath)
    {
        return VirtualFilePathHelper.NormalizePath(subpath);
    }

    private Dictionary<String, IFileInfo> CreateFiles()
    {
        var files = new Dictionary<String, IFileInfo>(StringComparer.OrdinalIgnoreCase);
        AddFiles(files);
        return files;
    }
}
