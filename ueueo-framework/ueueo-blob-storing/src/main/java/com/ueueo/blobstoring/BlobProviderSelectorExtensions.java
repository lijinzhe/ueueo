package com.ueueo.blobstoring;

import org.springframework.lang.NonNull;

import java.util.Objects;

public class BlobProviderSelectorExtensions {

    public static IBlobProvider get(@NonNull IBlobProviderSelector selector, Class<?> containerType) {
        Objects.requireNonNull(selector);

        BlobContainerNameAttribute attribute = containerType.getAnnotation(BlobContainerNameAttribute.class);
        return selector.get(attribute.name());
    }
}
