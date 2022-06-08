package com.ueueo.backgroundworkers;

import org.springframework.beans.factory.BeanFactory;

public class PeriodicBackgroundWorkerContext {
    private BeanFactory beanFactory;

    public PeriodicBackgroundWorkerContext(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
