package com.ueueo.localization;

import lombok.Getter;
import org.springframework.beans.factory.BeanFactory;

@Getter
public class LocalizationResourceInitializationContext {
    private final LocalizationResource Resource;

    private final BeanFactory beanFactory;

    public LocalizationResourceInitializationContext(LocalizationResource resource, BeanFactory beanFactory) {
        this.Resource = resource;
        this.beanFactory = beanFactory;
    }
}
