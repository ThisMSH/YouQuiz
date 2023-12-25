package com.youquiz.exceptions;

public class ResourceBadRequestException extends RuntimeException {
    public ResourceBadRequestException(String message) {
        super(message);
    }

    public ResourceBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
