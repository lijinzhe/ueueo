package com.ueueo.authorization.permissions;

import com.ueueo.simplestatechecking.IHasSimpleStateCheckers;
import com.ueueo.simplestatechecking.SimpleBatchStateCheckerBase;
import com.ueueo.simplestatechecking.SimpleBatchStateCheckerContext;
import com.ueueo.simplestatechecking.SimpleStateCheckerResult;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RequirePermissionsSimpleBatchStateChecker<TState extends IHasSimpleStateCheckers<TState>> extends SimpleBatchStateCheckerBase<TState> {
    //TODO 使用线程缓存，不知道为什么
//    public static RequirePermissionsSimpleBatchStateChecker getCurrent() {
//        return _current.get();
//    }
//
//    private static ThreadLocal<RequirePermissionsSimpleBatchStateChecker> _current = new ThreadLocal<>();

    private IPermissionChecker permissionChecker;

    private List<RequirePermissionsSimpleBatchStateCheckerModel<TState>> models;

//    static {
//        _current.set(new RequirePermissionsSimpleBatchStateChecker());
//    }

    public RequirePermissionsSimpleBatchStateChecker(IPermissionChecker permissionChecker) {
        this.permissionChecker = permissionChecker;
        this.models = new ArrayList<>();
    }

    public RequirePermissionsSimpleBatchStateChecker<TState> addCheckModels(RequirePermissionsSimpleBatchStateCheckerModel<TState>... models) {
        Assert.notEmpty(models, "Models must not empty!");
        this.models.addAll(Arrays.asList(models));
        return this;
    }

//    public static IDisposable use(RequirePermissionsSimpleBatchStateChecker checker) {
//        RequirePermissionsSimpleBatchStateChecker previousValue = getCurrent();
//        _current.set(checker);
//        return new DisposeAction(() -> _current.set(previousValue));
//    }

    @Override
    public SimpleStateCheckerResult<TState> isEnabled(SimpleBatchStateCheckerContext<TState> context) {
        SimpleStateCheckerResult<TState> result = new SimpleStateCheckerResult<>(context.getStates());

        List<String> permissions = models.stream().filter(x -> context.getStates().stream().anyMatch(s -> s.equals(x.getState())))
                .flatMap(x -> x.getPermissions().stream()).distinct().collect(Collectors.toList());
        MultiplePermissionGrantResult grantResult = permissionChecker.isGranted(permissions);

        for (TState state : context.getStates()) {
            RequirePermissionsSimpleBatchStateCheckerModel<TState> model = models.stream().filter(x -> x.getState().equals(state)).findFirst().orElse(null);
            if (model != null) {
                if (model.requiresAll) {
                    result.put(model.getState(),
                            model.getPermissions().stream()
                                    .allMatch(x -> grantResult.getResult().entrySet().stream().anyMatch(y -> y.getKey().equals(x) && y.getValue().equals(PermissionGrantResult.Granted))));
                } else {
                    result.put(model.getState(),
                            grantResult.getResult().entrySet().stream().anyMatch(x -> model.getPermissions().contains(x.getKey()) && x.getValue().equals(PermissionGrantResult.Granted)));

                }
            }
        }

        return result;
    }
}
