package com.ueueo.localization;

import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * @author Lee
 * @date 2022-05-17 14:46
 */
@Getter
public class LocalizableString implements ILocalizableString {
    private final Class<?> resourceType;
    private final String name;
    private final Object[] arguments;
    private final String defaultMessage;

    public LocalizableString(Class<?> resourceType, @NonNull String name) {
        this(resourceType, name, null, null);
    }

    public LocalizableString(Class<?> resourceType, @NonNull String name, Object[] arguments) {
        this(resourceType, name, arguments, null);
    }

    public LocalizableString(Class<?> resourceType, @NonNull String name, Object[] arguments, String defaultMessage) {
        Assert.notNull(name, "name must not null.");
        this.resourceType = resourceType;
        this.name = name;
        this.arguments = arguments;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public LocalizedString localize(IStringLocalizerFactory stringLocalizerFactory) {
        return stringLocalizerFactory.create(resourceType).get(name, arguments, defaultMessage);
    }

    public static LocalizableString create(Class<?> resourceType, @NonNull String name) {
        return new LocalizableString(resourceType, name);
    }
}
