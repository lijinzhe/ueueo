package com.ueueo.data.objectextending.modularity;

import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class ExtensionPropertyApiConfiguration {
    @NonNull
    public ExtensionPropertyApiGetConfiguration onGet;

    @NonNull
    public ExtensionPropertyApiCreateConfiguration onCreate;

    @NonNull
    public ExtensionPropertyApiUpdateConfiguration onUpdate;

    public ExtensionPropertyApiConfiguration() {
        onGet = new ExtensionPropertyApiGetConfiguration();
        onCreate = new ExtensionPropertyApiCreateConfiguration();
        onUpdate = new ExtensionPropertyApiUpdateConfiguration();
    }
}
