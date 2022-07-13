package com.ueueo.blobstoring;

import com.ueueo.exception.BaseException;

public class BlobAlreadyExistsException extends BaseException {
    public BlobAlreadyExistsException() {

    }

    public BlobAlreadyExistsException(String message) {
        super(message);
    }

    public BlobAlreadyExistsException(String message, Exception innerException) {
        super(message, innerException);
    }

}
