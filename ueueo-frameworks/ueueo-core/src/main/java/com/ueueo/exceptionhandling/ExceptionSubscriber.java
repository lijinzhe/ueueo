package com.ueueo.exceptionhandling;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * @author Lee
 * @date 2022-05-29 12:48
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class ExceptionSubscriber implements IExceptionSubscriber {

}
