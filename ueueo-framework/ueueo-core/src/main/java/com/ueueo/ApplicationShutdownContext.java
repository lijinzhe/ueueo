package com.ueueo;

import lombok.Getter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * @author Lee
 * @date 2021-08-20 10:42
 */
public class ApplicationShutdownContext {
    @Getter
    private BeanFactory beanFactory;

    public ApplicationShutdownContext(@NonNull BeanFactory beanFactory) {
        Objects.requireNonNull(beanFactory);
        this.beanFactory = beanFactory;
    }
}
