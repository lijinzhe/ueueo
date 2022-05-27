package com.ueueo.auditing;

import com.ueueo.uow.IUnitOfWorkManager;
import com.ueueo.users.ICurrentUser;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

/**
 * @author Lee
 * @date 2022-05-26 17:56
 */
public class AuditingInterceptorRegistrar extends DefaultPointcutAdvisor {

    public AuditingInterceptorRegistrar(IAuditingHelper auditingHelper,
                                        IAuditingManager auditingManager,
                                        AbpAuditingOptions auditingOptions,
                                        IUnitOfWorkManager unitOfWorkManager,
                                        ICurrentUser currentUser) {
        super(new AnnotationMatchingPointcut(Audited.class, Audited.class),
                new AuditingInterceptor(auditingHelper, auditingManager, auditingOptions, unitOfWorkManager,currentUser));
    }

    //    public static void registerIfNeeded(IOnServiceRegistredContext context) {
    //        if (shouldIntercept(context.getImplementationType())) {
    //            context.getInterceptors().add(AuditingInterceptor.class);
    //        }
    //    }
    //
    //    private static boolean shouldIntercept(Class<?> type) {
    //        if (DynamicProxyIgnoreTypes.contains(type, true)) {
    //            return false;
    //        }
    //        if (Boolean.TRUE.equals(shouldAuditTypeByDefaultOrNull(type))) {
    //            return true;
    //        }
    //        if (Arrays.stream(type.getMethods()).anyMatch(m -> m.isAnnotationPresent(Audited.class))) {
    //            return true;
    //        }
    //        return false;
    //    }

}
