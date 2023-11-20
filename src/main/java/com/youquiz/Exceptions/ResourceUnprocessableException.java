package com.youquiz.Exceptions;

public class ResourceUnprocessableException extends RuntimeException {
    public ResourceUnprocessableException(String message) {
        super(message);
    }

    public ResourceUnprocessableException(String message, Throwable cause) {
        super(message, cause);
    }
}
