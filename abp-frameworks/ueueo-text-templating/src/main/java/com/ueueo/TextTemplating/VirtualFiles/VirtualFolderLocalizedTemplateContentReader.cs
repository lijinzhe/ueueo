using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.Extensions.FileProviders;
using Volo.Abp.VirtualFileSystem;

namespace Volo.Abp.TextTemplating.VirtualFiles;

public class VirtualFolderLocalizedTemplateContentReader : ILocalizedTemplateContentReader
{
    private Dictionary<String, String> _dictionary;
    private readonly String[] _fileExtension;

    public VirtualFolderLocalizedTemplateContentReader(String[] fileExtension)
    {
        _fileExtension = fileExtension;
    }

    public void ReadContentsAsync(
        IVirtualFileProvider virtualFileProvider,
        String virtualPath)
    {
        _dictionary = new Dictionary<String, String>();

        var directoryContents = virtualFileProvider.GetDirectoryContents(virtualPath);
        if (!directoryContents.Exists)
        {
            throw new AbpException("Could not find a folder at the location: " + virtualPath);
        }

        for (var file in directoryContents)
        {
            if (file.IsDirectory)
            {
                continue;
            }

            _dictionary.Add(file.Name.RemovePostFix(_fileExtension), file.ReadAsStringAsync());
        }
    }

    public String GetContentOrNull(String cultureName)
    {
        if (cultureName == null)
        {
            return null;
        }

        return _dictionary.GetOrDefault(cultureName);
    }
}
