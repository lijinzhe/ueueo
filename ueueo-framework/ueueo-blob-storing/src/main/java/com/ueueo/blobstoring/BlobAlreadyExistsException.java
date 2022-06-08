package com.ueueo.blobstoring;

import com.ueueo.AbpException;

public class BlobAlreadyExistsException extends AbpException {
    public BlobAlreadyExistsException() {

    }

    public BlobAlreadyExistsException(String message) {
        super(message);
    }

    public BlobAlreadyExistsException(String message, Exception innerException) {
        super(message, innerException);
    }

}
