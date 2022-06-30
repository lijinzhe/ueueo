package com.ueueo.blobstoring;

import com.ueueo.exception.SystemException;

public class BlobAlreadyExistsException extends SystemException {
    public BlobAlreadyExistsException() {

    }

    public BlobAlreadyExistsException(String message) {
        super(message);
    }

    public BlobAlreadyExistsException(String message, Exception innerException) {
        super(message, innerException);
    }

}
