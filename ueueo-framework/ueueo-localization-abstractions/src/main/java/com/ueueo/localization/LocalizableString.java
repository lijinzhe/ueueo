package com.ueueo.localization;

import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.lang.reflect.Type;

/**
 * @author Lee
 * @date 2022-05-17 14:46
 */
public class LocalizableString implements ILocalizableString {
    @Getter
    private Type resourceType;
    @Getter
    private String name;

    public LocalizableString(Type resourceType, @NonNull String name) {
        Assert.notNull(name, "name must not null.");
        this.name = name;
        this.resourceType = resourceType;
    }

    @Override
    public LocalizedString localize(IStringLocalizerFactory stringLocalizerFactory) {
        return stringLocalizerFactory.create(resourceType).get(this.name);
    }

    public static LocalizableString create(Type resourceType, @NonNull String name) {
        return new LocalizableString(resourceType, name);
    }
}
