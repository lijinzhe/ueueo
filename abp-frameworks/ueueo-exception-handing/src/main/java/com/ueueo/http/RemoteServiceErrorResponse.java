package com.ueueo.http;

import lombok.Data;

@Data
public class RemoteServiceErrorResponse {
    private RemoteServiceErrorInfo error;

    public RemoteServiceErrorResponse(RemoteServiceErrorInfo error) {
        this.error = error;
    }
}
