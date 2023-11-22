package com.youquiz.Exceptions.Handlers;

import com.youquiz.Utils.ResponseHandler;
import org.modelmapper.MappingException;
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
}
