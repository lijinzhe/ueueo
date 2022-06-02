package com.ueueo.localization;

import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class LocalizationResource {
    @NonNull
    private Class<?> resourceType;

    @Nullable
    private String defaultCultureName;

    @NonNull
    private LocalizationResourceContributorList contributors;

    @NonNull
    private Set<Class<?>> baseResourceTypes;

    public String getResourceName() {
        LocalizationResourceNameAttribute attribute = resourceType.getAnnotation(LocalizationResourceNameAttribute.class);
        if (attribute != null) {
            return attribute.name();
        } else {
            return null;
        }
    }

    public LocalizationResource(
            @NonNull Class<?> resourceType,
            @Nullable String defaultCultureName,
            @Nullable ILocalizationResourceContributor initialContributor) {
        this.resourceType = Objects.requireNonNull(resourceType);
        this.defaultCultureName = defaultCultureName;

        baseResourceTypes = new HashSet<>();
        contributors = new LocalizationResourceContributorList();

        if (initialContributor != null) {
            contributors.add(initialContributor);
        }
        addBaseResourceTypes();
    }

    protected void addBaseResourceTypes() {
        InheritResourceAttribute attribute = resourceType.getAnnotation(InheritResourceAttribute.class);
        if (attribute != null && ArrayUtils.isNotEmpty(attribute.resourceTypes())) {
            baseResourceTypes.addAll(Arrays.asList(attribute.resourceTypes()));
        }
    }
}
