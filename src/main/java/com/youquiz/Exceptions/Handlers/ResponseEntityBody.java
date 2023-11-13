package com.youquiz.Exceptions.Handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseEntityBody {
    protected ResponseEntity<Object> responseEntityBody(RuntimeException e, HttpStatus httpStatus) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("message", e.getMessage());
        body.put("cause", e.getCause());
        body.put("status", httpStatus.value());

        return new ResponseEntity<>(body, httpStatus);
    }
}
