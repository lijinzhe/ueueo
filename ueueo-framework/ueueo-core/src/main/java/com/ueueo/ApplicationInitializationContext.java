package com.ueueo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-20 10:30
 */
public class ApplicationInitializationContext implements BeanFactoryAware {

    public BeanFactory beanFactory;

    public ApplicationInitializationContext(@NonNull BeanFactory beanFactory) {
        Objects.requireNonNull(beanFactory);
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
