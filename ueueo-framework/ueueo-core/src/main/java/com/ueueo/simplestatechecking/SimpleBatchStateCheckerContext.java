package com.ueueo.simplestatechecking;

import com.ueueo.dependencyinjection.system.IServiceProvider;
import lombok.Getter;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-26 15:24
 */
@Getter
public class SimpleBatchStateCheckerContext<TState extends IHasSimpleStateCheckers<TState>> {
    private IServiceProvider serviceProvider;
    private List<TState> states;

    public SimpleBatchStateCheckerContext(IServiceProvider serviceProvider, List<TState> states) {
        this.serviceProvider = serviceProvider;
        this.states = states;
    }

    public List<TState> getStates() {
        return states;
    }
}
