package com.youquiz.Exceptions;

public class StorageExpectationFailed extends StorageException {
    public StorageExpectationFailed(String message) {
        super(message);
    }

    public StorageExpectationFailed(String message, Throwable cause) {
        super(message, cause);
    }
}
