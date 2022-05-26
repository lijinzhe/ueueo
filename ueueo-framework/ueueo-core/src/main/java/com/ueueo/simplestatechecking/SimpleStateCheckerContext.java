package com.ueueo.simplestatechecking;

import com.ueueo.dependencyinjection.system.IServiceProvider;
import lombok.Getter;

/**
 * @author Lee
 * @date 2021-08-26 15:14
 */
@Getter
public class SimpleStateCheckerContext<TState extends IHasSimpleStateCheckers<TState>> {

    private IServiceProvider serviceProvider;

    private TState state;

    public SimpleStateCheckerContext(IServiceProvider serviceProvider, TState state) {
        this.serviceProvider = serviceProvider;
        this.state = state;
    }

    public TState getState() {
        return state;
    }
}
