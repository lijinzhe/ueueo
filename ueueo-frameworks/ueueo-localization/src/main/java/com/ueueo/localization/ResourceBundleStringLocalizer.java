package com.ueueo.localization;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.Nullable;

public class ResourceBundleStringLocalizer extends ResourceBundleMessageSource implements IStringLocalizer {

    public ResourceBundleStringLocalizer(String... basenames) {
        setBasenames(basenames);
    }

    @Override
    public LocalizedString get(String name) {
        return get(name, null);
    }

    @Override
    public LocalizedString get(String name, @Nullable Object[] arguments) {
        String message = getMessage(name, arguments, CurrentLocale.getCurrentLocale());
        boolean resourceNotFound = false;
        if (StringUtils.isBlank(message)) {
            resourceNotFound = true;
            message = name;
        }
        return new LocalizedString(name, message, resourceNotFound);
    }

    @Override
    public LocalizedString get(String name, @Nullable Object[] arguments, @Nullable String defaultMessage) {
        String message = getMessage(name, arguments, CurrentLocale.getCurrentLocale());
        boolean resourceNotFound = false;
        if (StringUtils.isBlank(message)) {
            resourceNotFound = true;
            message = defaultMessage;
        }
        return new LocalizedString(name, message, resourceNotFound);
    }

}
