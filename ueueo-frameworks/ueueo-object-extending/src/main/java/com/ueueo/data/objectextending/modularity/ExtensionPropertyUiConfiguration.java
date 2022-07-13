package com.ueueo.data.objectextending.modularity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
@Getter
public class ExtensionPropertyUiConfiguration
{
    @NonNull
    private ExtensionPropertyUiTableConfiguration onTable;

    @NonNull
    private ExtensionPropertyUiFormConfiguration onCreateForm;

    @NonNull
    private ExtensionPropertyUiFormConfiguration onEditForm;

    @NonNull
    @Setter
    private ExtensionPropertyLookupConfiguration lookup;

    public ExtensionPropertyUiConfiguration()
    {
        onTable = new ExtensionPropertyUiTableConfiguration();
        onCreateForm = new ExtensionPropertyUiFormConfiguration();
        onEditForm = new ExtensionPropertyUiFormConfiguration();
        lookup = new ExtensionPropertyLookupConfiguration();
    }
}
