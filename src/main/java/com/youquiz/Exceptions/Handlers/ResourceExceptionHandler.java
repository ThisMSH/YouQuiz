package com.youquiz.Exceptions.Handlers;

import com.youquiz.Exceptions.ResourceAlreadyExistsException;
import com.youquiz.Exceptions.ResourceBadRequestException;
import com.youquiz.Exceptions.ResourceNotFoundException;
import com.youquiz.Exceptions.ResourceUnprocessableException;
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

    @ExceptionHandler(value = {ResourceAlreadyExistsException.class})
    public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        return ResponseHandler.exception(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {ResourceBadRequestException.class})
    public ResponseEntity<Object> handleResourceBadRequestException(ResourceBadRequestException e) {
        return ResponseHandler.exception(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ResourceUnprocessableException.class})
    public ResponseEntity<Object> handleResourceUnprocessableException(ResourceUnprocessableException e) {
        return ResponseHandler.exception(e, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
