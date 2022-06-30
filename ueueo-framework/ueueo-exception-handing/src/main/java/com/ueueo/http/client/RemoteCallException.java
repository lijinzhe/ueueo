package com.ueueo.http.client;

import com.ueueo.exception.SystemException;
import com.ueueo.exceptionhandling.IHasErrorCode;
import com.ueueo.exceptionhandling.IHasErrorDetails;
import com.ueueo.exceptionhandling.IHasHttpStatusCode;
import com.ueueo.http.RemoteServiceErrorInfo;
import lombok.Data;

@Data
public class RemoteCallException extends SystemException implements IHasErrorCode, IHasErrorDetails, IHasHttpStatusCode {

    private int httpStatusCode;

    private RemoteServiceErrorInfo error;

    @Override
    public String getCode() {
        return error != null ? error.getCode() : null;
    }

    @Override
    public String getDetails() {
        return error != null ? error.getDetails() : null;
    }

    public RemoteCallException() {

    }

    public RemoteCallException(String message, Exception innerException) {
        super(message, innerException);
    }

    public RemoteCallException(RemoteServiceErrorInfo error, Exception innerException) {
        super(error.getMessage(), innerException);
        this.error = error;
        if (error.getData() != null) {
            this.data.putAll(error.getData());
        }
    }
}
