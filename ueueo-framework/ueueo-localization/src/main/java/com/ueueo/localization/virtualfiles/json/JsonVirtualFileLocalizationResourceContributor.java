package com.ueueo.localization.virtualfiles.json;

//TODO: Use composition over inheritance..?

import com.ueueo.localization.ILocalizationDictionary;
import com.ueueo.localization.json.JsonLocalizationDictionaryBuilder;
import com.ueueo.localization.virtualfiles.VirtualFileLocalizationResourceContributorBase;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class JsonVirtualFileLocalizationResourceContributor extends VirtualFileLocalizationResourceContributorBase {
    public JsonVirtualFileLocalizationResourceContributor(String virtualPath) {
        super(virtualPath);
    }

    @Override
    protected boolean canParseFile(File file) {
        return StringUtils.endsWithIgnoreCase(file.getName(), ".json");
    }

    @Override
    protected ILocalizationDictionary createDictionaryFromFileContent(String jsonString) {
        return JsonLocalizationDictionaryBuilder.buildFromJsonString(jsonString);
    }
}
