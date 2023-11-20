package com.youquiz.Exceptions.Handlers;

import com.youquiz.Utils.ResponseHandler;
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
}
