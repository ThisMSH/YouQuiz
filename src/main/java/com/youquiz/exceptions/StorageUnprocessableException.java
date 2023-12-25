package com.youquiz.exceptions;

public class StorageUnprocessableException extends StorageException {
    public StorageUnprocessableException(String message) {
        super(message);
    }

    public StorageUnprocessableException(String message, Throwable cause) {
        super(message, cause);
    }
}
