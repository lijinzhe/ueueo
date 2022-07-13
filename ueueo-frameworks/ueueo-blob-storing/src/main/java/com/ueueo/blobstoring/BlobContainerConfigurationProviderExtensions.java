package com.ueueo.blobstoring;

public class BlobContainerConfigurationProviderExtensions {
    public static BlobContainerConfiguration get(
            IBlobContainerConfigurationProvider configurationProvider, Class<?> containerType) {
        BlobContainerNameAttribute attribute = containerType.getAnnotation(BlobContainerNameAttribute.class);
        return configurationProvider.get(attribute.name());
    }
}
