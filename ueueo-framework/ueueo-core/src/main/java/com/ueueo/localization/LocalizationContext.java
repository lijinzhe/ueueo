package com.ueueo.localization;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-29 12:34
 */
@Getter
public class LocalizationContext implements BeanFactoryAware {

    private BeanFactory beanFactory;

    private IStringLocalizerFactory localizerFactory;

    public LocalizationContext(BeanFactory beanFactory, IStringLocalizerFactory localizerFactory) {
        this.beanFactory = beanFactory;
        this.localizerFactory = localizerFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
