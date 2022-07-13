package com.ueueo.exceptionhandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * @author Lee
 * @date 2022-05-29 12:52
 */
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExceptionNotifier implements IExceptionNotifier {

    private IExceptionSubscriber exceptionSubscriber;

    public ExceptionNotifier(IExceptionSubscriber exceptionSubscriber) {
        this.exceptionSubscriber = exceptionSubscriber;
    }

    @Override
    public void notify(@NonNull ExceptionNotificationContext context) {
        Objects.requireNonNull(context);
        try {
            exceptionSubscriber.handle(context);
        } catch (Exception e) {
            log.warn(String.format("Exception subscriber of type %s has thrown an exception!", exceptionSubscriber.getClass().getName()), e);
        }
    }
}
