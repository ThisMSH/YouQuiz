package com.youquiz.exceptions.handlers;

import com.youquiz.exceptions.StorageException;
import com.youquiz.exceptions.StorageExpectationFailed;
import com.youquiz.exceptions.StorageUnprocessableException;
import com.youquiz.utils.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StorageExceptionHandler {
    @ExceptionHandler(value = {StorageException.class})
    public ResponseEntity<Object> handleStorageException(StorageException e) {
        return ResponseHandler.exception(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {StorageUnprocessableException.class})
    public ResponseEntity<Object> handleStorageUnprocessableException(StorageUnprocessableException e) {
        return ResponseHandler.exception(e, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {StorageExpectationFailed.class})
    public ResponseEntity<Object> handleStorageExpectationFailed(StorageExpectationFailed e) {
        return ResponseHandler.exception(e, HttpStatus.EXPECTATION_FAILED);
    }
}
