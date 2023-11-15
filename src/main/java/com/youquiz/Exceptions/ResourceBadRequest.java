package com.youquiz.Exceptions;

public class ResourceBadRequest extends RuntimeException {
    public ResourceBadRequest(String message) {
        super(message);
    }

    public ResourceBadRequest(String message, Throwable cause) {
        super(message, cause);
    }
}
