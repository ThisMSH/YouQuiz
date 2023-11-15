package com.youquiz.Exceptions.Handlers;

import com.youquiz.Exceptions.ResourceAlreadyExists;
import com.youquiz.Exceptions.ResourceNotFoundException;
import com.youquiz.Utils.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseHandler.exception(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ResourceAlreadyExists.class})
    public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExists e) {
        return ResponseHandler.exception(e, HttpStatus.CONFLICT);
    }
}
