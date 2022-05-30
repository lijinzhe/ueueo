using System;
using System.Collections.Generic;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.FileProviders;
using Microsoft.Extensions.Localization;
using Microsoft.Extensions.Primitives;
using Volo.Abp.Internal;
using Volo.Abp.VirtualFileSystem;

namespace Volo.Abp.Localization.VirtualFiles;

public abstract class VirtualFileLocalizationResourceContributorBase : ILocalizationResourceContributor
{
    private readonly String _virtualPath;
    private IVirtualFileProvider _virtualFileProvider;
    private Dictionary<String, ILocalizationDictionary> _dictionaries;
    private boolean _subscribedForChanges;
    private readonly Object _syncObj = new object();

    protected VirtualFileLocalizationResourceContributorBase(String virtualPath)
    {
        _virtualPath = virtualPath;
    }

    public   void Initialize(LocalizationResourceInitializationContext context)
    {
        _virtualFileProvider = context.ServiceProvider.GetRequiredService<IVirtualFileProvider>();
    }

    public   LocalizedString GetOrNull(String cultureName, String name)
    {
        return GetDictionaries().GetOrDefault(cultureName)?.GetOrNull(name);
    }

    public   void Fill(String cultureName, Dictionary<String, LocalizedString> dictionary)
    {
        GetDictionaries().GetOrDefault(cultureName)?.Fill(dictionary);
    }

    private Dictionary<String, ILocalizationDictionary> GetDictionaries()
    {
        var dictionaries = _dictionaries;
        if (dictionaries != null)
        {
            return dictionaries;
        }

        lock (_syncObj)
        {
            dictionaries = _dictionaries;
            if (dictionaries != null)
            {
                return dictionaries;
            }

            if (!_subscribedForChanges)
            {
                ChangeToken.OnChange(() => _virtualFileProvider.Watch(_virtualPath.EnsureEndsWith('/') + "*.*"),
                    () =>
                    {
                        _dictionaries = null;
                    });

                _subscribedForChanges = true;
            }

            dictionaries = _dictionaries = CreateDictionaries();
        }

        return dictionaries;
    }

    private Dictionary<String, ILocalizationDictionary> CreateDictionaries()
    {
        var dictionaries = new Dictionary<String, ILocalizationDictionary>();

        for (var file in _virtualFileProvider.GetDirectoryContents(_virtualPath))
        {
            if (file.IsDirectory || !CanParseFile(file))
            {
                continue;
            }

            var dictionary = CreateDictionaryFromFile(file);
            if (dictionaries.ContainsKey(dictionary.CultureName))
            {
                throw new AbpException($"{file.GetVirtualOrPhysicalPathOrNull()} dictionary has a culture name '{dictionary.CultureName}' which is already defined!");
            }

            dictionaries[dictionary.CultureName] = dictionary;
        }

        return dictionaries;
    }

    protected abstract boolean CanParseFile(IFileInfo file);

    protected   ILocalizationDictionary CreateDictionaryFromFile(IFileInfo file)
    {
        using (var stream = file.CreateReadStream())
        {
            return CreateDictionaryFromFileContent(Utf8Helper.ReadStringFromStream(stream));
        }
    }

    protected abstract ILocalizationDictionary CreateDictionaryFromFileContent(String fileContent);
}
