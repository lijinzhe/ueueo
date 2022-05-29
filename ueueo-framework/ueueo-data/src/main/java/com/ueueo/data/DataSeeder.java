package com.ueueo.data;

import com.ueueo.uow.UnitOfWorkAttribute;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author Lee
 * @date 2022-05-29 15:06
 */
public class DataSeeder implements IDataSeeder, BeanFactoryAware {

    private BeanFactory beanFactory;

    protected AbpDataSeedOptions options;

    public DataSeeder(BeanFactory beanFactory, AbpDataSeedOptions options) {
        this.beanFactory = beanFactory;
        this.options = options;
    }

    @Override
    @UnitOfWorkAttribute
    public void seed(DataSeedContext context) {
        for (Class<? extends IDataSeedContributor> contributorType : options.getContributors()) {
            IDataSeedContributor dataSeedContributor = beanFactory.getBean(contributorType);
            dataSeedContributor.seed(context);
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
