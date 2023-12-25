package com.youquiz.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> success(String message, HttpStatus status, Object data) {
        Map<String, Object> body = new HashMap<>();

        body.put("message", message);
        body.put("status", status.value());
        body.put("data", data);

        return new ResponseEntity<>(body, status);
    }

    public static ResponseEntity<Object> error(String message, HttpStatus status, Object data) {
        Map<String, Object> body = new HashMap<>();

        body.put("message", message);
        body.put("status", status.value());
        body.put("errors", data);

        return new ResponseEntity<>(body, status);
    }

    public static ResponseEntity<Object> exception(RuntimeException e, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();

        body.put("message", e.getMessage());
        body.put("status", status.value());
        body.put("cause", e.getCause());

        return new ResponseEntity<>(body, status);
    }
}
