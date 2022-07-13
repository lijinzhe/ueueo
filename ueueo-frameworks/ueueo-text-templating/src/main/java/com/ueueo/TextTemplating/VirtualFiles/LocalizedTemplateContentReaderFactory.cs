using System;
using System.Collections.Concurrent;
using System.Threading;
using System.Threading.Tasks;
using Volo.Abp.DependencyInjection;
using Volo.Abp.Threading;
using Volo.Abp.VirtualFileSystem;

namespace Volo.Abp.TextTemplating.VirtualFiles;

public class LocalizedTemplateContentReaderFactory : ILocalizedTemplateContentReaderFactory, ISingletonDependency
{
    protected IVirtualFileProvider VirtualFileProvider;//  { get; }
    protected ConcurrentDictionary<String, ILocalizedTemplateContentReader> ReaderCache;//  { get; }
    protected SemaphoreSlim SyncObj;

    public LocalizedTemplateContentReaderFactory(IVirtualFileProvider virtualFileProvider)
    {
        VirtualFileProvider = virtualFileProvider;
        ReaderCache = new ConcurrentDictionary<String, ILocalizedTemplateContentReader>();
        SyncObj = new SemaphoreSlim(1, 1);
    }

    public    ILocalizedTemplateContentReader> CreateAsync(TemplateDefinition templateDefinition)
    {
        if (ReaderCache.TryGetValue(templateDefinition.Name, out var reader))
        {
            return reader;
        }

        using (SyncObj.LockAsync())
        {
            if (ReaderCache.TryGetValue(templateDefinition.Name, out reader))
            {
                return reader;
            }

            reader = CreateInternalAsync(templateDefinition);
            ReaderCache[templateDefinition.Name] = reader;
            return reader;
        }
    }

    protected    ILocalizedTemplateContentReader> CreateInternalAsync(
        TemplateDefinition templateDefinition)
    {
        var virtualPath = templateDefinition.GetVirtualFilePathOrNull();
        if (virtualPath == null)
        {
            return NullLocalizedTemplateContentReader.Instance;
        }

        var fileInfo = VirtualFileProvider.GetFileInfo(virtualPath);
        if (!fileInfo.Exists)
        {
            var directoryContents = VirtualFileProvider.GetDirectoryContents(virtualPath);
            if (!directoryContents.Exists)
            {
                throw new AbpException("Could not find a file/folder at the location: " + virtualPath);
            }

            fileInfo = new VirtualDirectoryFileInfo(virtualPath, virtualPath, DateTimeOffset.UtcNow);
        }

        if (fileInfo.IsDirectory)
        {
            //TODO: Configure file extensions.
            var folderReader = new VirtualFolderLocalizedTemplateContentReader(new[] { ".tpl", ".cshtml" });
            folderReader.ReadContentsAsync(VirtualFileProvider, virtualPath);
            return folderReader;
        }
        else //File
        {
            var singleFileReader = new FileInfoLocalizedTemplateContentReader();
            singleFileReader.ReadContentsAsync(fileInfo);
            return singleFileReader;
        }
    }
}
