package com.ueueo.backgroundjobs;

import com.ueueo.exception.SystemException;
import lombok.Data;

@Data
public class BackgroundJobExecutionException extends SystemException {

    private String jobType;

    private Object jobArgs;

    public BackgroundJobExecutionException() {

    }

    /**
     * Creates a new <see cref="BackgroundJobExecutionException"/> object.
     *
     * <param name="message">Exception message</param>
     * <param name="innerException">Inner exception</param>
     */
    public BackgroundJobExecutionException(String message, Exception innerException) {
        super(message, innerException);
    }
}
