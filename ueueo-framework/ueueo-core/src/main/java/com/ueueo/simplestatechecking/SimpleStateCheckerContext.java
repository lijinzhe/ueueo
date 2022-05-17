package com.ueueo.simplestatechecking;

import lombok.Getter;
import org.springframework.beans.factory.BeanFactory;

/**
 * @author Lee
 * @date 2021-08-26 15:14
 */
@Getter
public class SimpleStateCheckerContext<TState extends IHasSimpleStateCheckers<TState>> {

    private BeanFactory beanFactory;

    private TState state;

    public SimpleStateCheckerContext(BeanFactory beanFactory, TState state) {
        this.beanFactory = beanFactory;
        this.state = state;
    }

    public TState getState() {
        return state;
    }
}
