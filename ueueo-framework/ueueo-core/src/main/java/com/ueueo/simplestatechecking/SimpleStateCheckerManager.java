package com.ueueo.simplestatechecking;

import com.ueueo.dependencyinjection.ICachedServiceProvider;
import com.ueueo.dependencyinjection.system.IServiceProvider;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2021-08-26 15:39
 */
@Getter
public class SimpleStateCheckerManager<TState extends IHasSimpleStateCheckers<TState>> implements ISimpleStateCheckerManager<TState> {

    protected IServiceProvider serviceProvider;
    protected AbpSimpleStateCheckerOptions<TState> Options;

    public SimpleStateCheckerManager(IServiceProvider serviceProvider, AbpSimpleStateCheckerOptions<TState> options) {
        this.serviceProvider = serviceProvider;
        this.Options = options;
    }

    @Override
    public Boolean isEnabled(TState state) {
        return internalIsEnabled(state, true);
    }

    @Override
    public SimpleStateCheckerResult<TState> isEnabled(List<TState> states) {
        SimpleStateCheckerResult<TState> result = new SimpleStateCheckerResult<>(states);

        List<ISimpleBatchStateChecker<TState>> batchStateCheckers = states.stream()
                .flatMap(x -> x.getStateCheckers().stream())
                .filter(x -> x instanceof ISimpleBatchStateChecker)
                .map(x -> (ISimpleBatchStateChecker<TState>) x)
                .collect(Collectors.toList());

        for (ISimpleBatchStateChecker<TState> stateChecker : batchStateCheckers) {
            SimpleBatchStateCheckerContext<TState> context = new SimpleBatchStateCheckerContext<>(
                    serviceProvider.getRequiredService(ICachedServiceProvider.class),
                    states.stream().filter(x -> x.getStateCheckers().contains(stateChecker)).collect(Collectors.toList()));
            result.putAll(stateChecker.isEnabled(context));
            if (result.values().stream().noneMatch(x -> x)) {
                return result;
            }
        }

        for (ISimpleBatchStateChecker<TState> globalStateChecker : Options.getGlobalStateCheckers()
                .stream().filter(ISimpleBatchStateChecker.class::isAssignableFrom)
                .map(x -> (ISimpleBatchStateChecker<TState>) serviceProvider.getRequiredService(x)).collect(Collectors.toList())) {
            SimpleBatchStateCheckerContext<TState> context = new SimpleBatchStateCheckerContext<TState>(
                    serviceProvider.getRequiredService(ICachedServiceProvider.class),
                    states.stream()
                            .filter(x -> {
                                Boolean v = result.get(x);
                                return v != null && v;
                            }).collect(Collectors.toList()));

            result.putAll(globalStateChecker.isEnabled(context));
        }

        for (TState state : states) {
            if (result.get(state)) {
                result.put(state, internalIsEnabled(state, false));
            }
        }

        return result;
    }

    protected boolean internalIsEnabled(TState state, boolean useBatchChecker) {
        SimpleStateCheckerContext<TState> context = new SimpleStateCheckerContext<>(serviceProvider.getRequiredService(ICachedServiceProvider.class), state);
        if (!useBatchChecker) {
            for (ISimpleStateChecker<TState> provider : state.getStateCheckers()) {
                if (!provider.isEnabled(context)) {
                    return false;
                }
            }
            for (ISimpleStateChecker<TState> provider : Options.getGlobalStateCheckers()
                    .stream().map(x -> serviceProvider.getRequiredService(x)).collect(Collectors.toList())) {
                if (!provider.isEnabled(context)) {
                    return false;
                }
            }
        } else {
            for (ISimpleStateChecker<TState> provider : state.getStateCheckers()
                    .stream().filter(x -> !(x instanceof ISimpleBatchStateChecker))
                    .collect(Collectors.toList())) {
                if (!provider.isEnabled(context)) {
                    return false;
                }
            }
            for (ISimpleStateChecker<TState> provider : Options.getGlobalStateCheckers()
                    .stream()
                    .filter(x -> !ISimpleBatchStateChecker.class.isAssignableFrom(x))
                    .map(x -> serviceProvider.getRequiredService(x)).collect(Collectors.toList())) {
                if (!provider.isEnabled(context)) {
                    return false;
                }
            }
        }
        return true;
    }
}
