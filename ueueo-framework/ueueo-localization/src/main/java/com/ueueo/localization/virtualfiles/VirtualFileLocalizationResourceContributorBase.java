package com.ueueo.localization.virtualfiles;

import com.ueueo.exception.SystemException;
import com.ueueo.localization.ILocalizationDictionary;
import com.ueueo.localization.ILocalizationResourceContributor;
import com.ueueo.localization.LocalizationResourceInitializationContext;
import com.ueueo.localization.LocalizedString;
import com.ueueo.virtualfilesystem.IVirtualFileProvider;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class VirtualFileLocalizationResourceContributorBase implements ILocalizationResourceContributor {
    private String virtualPath;
    private IVirtualFileProvider virtualFileProvider;
    private Map<String, ILocalizationDictionary> dictionaries;
    private boolean subscribedForChanges;
    private final Object Lock = new Object();

    protected VirtualFileLocalizationResourceContributorBase(String virtualPath) {
        this.virtualPath = virtualPath.endsWith("/") ? virtualPath : virtualPath + "/";
    }

    @Override
    public void initialize(LocalizationResourceInitializationContext context) {
        virtualFileProvider = context.getBeanFactory().getBean(IVirtualFileProvider.class);
    }

    @Override
    public LocalizedString getOrNull(String cultureName, String name) {
        return Optional.ofNullable(getDictionaries().get(cultureName))
                .map(x -> x.getOrNull(name))
                .orElse(null);
    }

    @Override
    public void fill(String cultureName, Map<String, LocalizedString> dictionary) {
        Optional.ofNullable(getDictionaries().get(cultureName))
                .ifPresent(x -> x.fill(dictionary));
    }

    private Map<String, ILocalizationDictionary> getDictionaries() {
        Map<String, ILocalizationDictionary> dictionaries = this.dictionaries;
        if (dictionaries != null) {
            return dictionaries;
        }

        synchronized (Lock) {
            dictionaries = this.dictionaries;
            if (dictionaries != null) {
                return dictionaries;
            }

            if (!subscribedForChanges) {
                FileAlterationObserver observer = new FileAlterationObserver(virtualPath);
                observer.addListener(new FileAlterationListenerAdaptor() {
                    @Override
                    public void onDirectoryChange(File directory) {
                        VirtualFileLocalizationResourceContributorBase.this.dictionaries = null;
                    }
                });
                virtualFileProvider.watch(observer);
                subscribedForChanges = true;
            }

            dictionaries = this.dictionaries = createDictionaries();
        }

        return dictionaries;
    }

    private Map<String, ILocalizationDictionary> createDictionaries() {
        Map<String, ILocalizationDictionary> dictionaries = new HashMap<>();

        File directory = virtualFileProvider.getDirectoryInfo(virtualPath);
        if (directory != null && directory.isDirectory() && directory.listFiles() != null) {
            for (File file : directory.listFiles()) {
                ILocalizationDictionary dictionary = createDictionaryFromFile(file);
                if (dictionaries.containsKey(dictionary.getCultureName())) {
                    throw new SystemException(String.format("file.GetVirtualOrPhysicalPathOrNull() dictionary has a culture name '%s' which is already defined!", dictionary.getCultureName()));
                }
                dictionaries.put(dictionary.getCultureName(), dictionary);
            }
        }

        return dictionaries;
    }

    protected abstract boolean canParseFile(File file);

    protected ILocalizationDictionary createDictionaryFromFile(File file) {
        try {
            return createDictionaryFromFileContent(FileUtils.readFileToString(file, StandardCharsets.UTF_8));
        } catch (IOException e) {
            return null;
        }
    }

    protected abstract ILocalizationDictionary createDictionaryFromFileContent(String fileContent);
}
