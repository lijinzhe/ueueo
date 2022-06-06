package com.ueueo.exceptionhandling;

import com.ueueo.http.RemoteServiceErrorInfo;

import java.util.function.Consumer;

/**
 * This interface can be implemented to convert an <see cref="Exception"/> object to an <see cref="RemoteServiceErrorInfo"/> object.
 * Implements Chain Of Responsibility pattern.
 */
public interface IExceptionToErrorInfoConverter {

    /**
     * Converter method.
     *
     * <param name="exception">The exception.</param>
     * <param name="options">Additional options.</param>
     * <returns>Error info or null</returns>
     */
    RemoteServiceErrorInfo convert(Exception exception, Consumer<AbpExceptionHandlingOptions> options);
}
