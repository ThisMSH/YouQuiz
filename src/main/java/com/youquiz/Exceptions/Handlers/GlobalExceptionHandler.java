package com.youquiz.Exceptions.Handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityBody {
    @ExceptionHandler(value = {NumberFormatException.class})
    public ResponseEntity<Object> handleNumberFormatException() {
        NumberFormatException e = new NumberFormatException("Invalid ID format. Please provide a numeric ID.");
        return responseEntityBody(e, HttpStatus.BAD_REQUEST);
    }
}
