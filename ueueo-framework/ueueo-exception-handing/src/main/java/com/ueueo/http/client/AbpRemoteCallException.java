package com.ueueo.http.client;

import com.ueueo.http.RemoteServiceErrorInfo;
import com.ueueo.AbpException;
import com.ueueo.exceptionhandling.IHasErrorCode;
import com.ueueo.exceptionhandling.IHasErrorDetails;
import com.ueueo.exceptionhandling.IHasHttpStatusCode;
import lombok.Data;

@Data
public class AbpRemoteCallException extends AbpException implements IHasErrorCode, IHasErrorDetails, IHasHttpStatusCode {

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

    public AbpRemoteCallException() {

    }

    public AbpRemoteCallException(String message, Exception innerException) {
        super(message, innerException);
    }

    public AbpRemoteCallException(RemoteServiceErrorInfo error, Exception innerException) {
        super(error.getMessage(), innerException);
        this.error = error;
        if (error.getData() != null) {
            this.data.putAll(error.getData());
        }
    }
}
