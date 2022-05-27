package com.ueueo.boot.autoconfigure.uow;

import com.ueueo.uow.IUnitOfWork;
import com.ueueo.uow.IUnitOfWorkManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-18 14:42
 */
@Configuration
@ConditionalOnClass(IUnitOfWork.class)
public class UowAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(IUnitOfWorkManager.class)
    public IUnitOfWorkManager unitOfWorkManager(){
        return null;
    }
}
