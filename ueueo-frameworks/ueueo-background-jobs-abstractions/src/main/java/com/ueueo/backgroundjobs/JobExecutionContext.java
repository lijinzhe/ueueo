package com.ueueo.backgroundjobs;

import lombok.Data;
import org.springframework.beans.factory.BeanFactory;

/**
 * @author Lee
 * @date 2022-05-29 18:15
 */
@Data
public class JobExecutionContext {
    private BeanFactory beanFactory;

    private Class<?> jobType;

    private Object jobArgs;

    public JobExecutionContext(BeanFactory beanFactory, Class<?> jobType, Object jobArgs) {
        this.beanFactory = beanFactory;
        this.jobType = jobType;
        this.jobArgs = jobArgs;
    }
}
