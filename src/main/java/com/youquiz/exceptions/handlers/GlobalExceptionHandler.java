package com.youquiz.exceptions.handlers;

import com.youquiz.utils.ResponseHandler;
import jakarta.validation.ConstraintViolationException;
import org.modelmapper.MappingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {NumberFormatException.class})
    public ResponseEntity<Object> handleNumberFormatException() {
        NumberFormatException e = new NumberFormatException("Invalid ID format. Please provide a numeric ID.");
        return ResponseHandler.exception(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {PropertyReferenceException.class})
    public ResponseEntity<Object> handlePropertyReferenceException(PropertyReferenceException e) {
        return ResponseHandler.exception(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MappingException.class})
    public ResponseEntity<Object> handleMappingException(MappingException e) {
        return ResponseHandler.exception(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseHandler.exception(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseHandler.exception(e, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        RuntimeException ex = new DataIntegrityViolationException("The object that you're trying to delete is assigned to another object.", e);
        return ResponseHandler.exception(ex, HttpStatus.CONFLICT);
    }
}
