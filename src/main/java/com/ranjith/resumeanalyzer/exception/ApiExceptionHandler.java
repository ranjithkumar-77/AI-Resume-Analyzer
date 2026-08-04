package com.ranjith.resumeanalyzer.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        String message = ex.getMessage() == null
                ? "Unexpected error"
                : ex.getMessage();

        String lower = message.toLowerCase();

        if (lower.contains("invalid")
                || lower.contains("password")
                || lower.contains("not found")) {

            status = HttpStatus.UNAUTHORIZED;
        }

        Map<String, String> body = new HashMap<>();
        body.put("message", message);

        return ResponseEntity.status(status).body(body);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> body = new HashMap<>();
        body.put("message", "Invalid request format.");

        return ResponseEntity.badRequest().body(body);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> body = new HashMap<>();
        body.put("message", "Validation failed.");

        return ResponseEntity.badRequest().body(body);
    }
}