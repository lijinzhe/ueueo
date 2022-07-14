package com.ueueo.features;

import com.ueueo.aspects.CrossCuttingConcerns;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Lee
 * @date 2022-05-17 16:54
 */
public class FeatureInterceptor implements MethodInterceptor {

    public static final String FEATURE_CHECKING = "FeatureChecking";

    private final IMethodInvocationFeatureCheckerService checkerService;

    public FeatureInterceptor(IMethodInvocationFeatureCheckerService checkerService) {
        this.checkerService = checkerService;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object targetObject = invocation.getThis();
        if (targetObject != null && CrossCuttingConcerns.isApplied(targetObject, FEATURE_CHECKING)) {
            return invocation.proceed();
        }
        checkFeatures(invocation);
        return invocation.proceed();
    }

    protected void checkFeatures(MethodInvocation invocation) {
        checkerService.check(new MethodInvocationFeatureCheckerContext(invocation.getMethod()));
    }
}
