package com.ducthong.TopCV.exceptions;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.utility.MessageSourceUtil;

import lombok.RequiredArgsConstructor;

import java.util.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSourceUtil messageUtil;

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<Response<String>> handlingAppException(AppException ex) {
        return ResponseEntity.badRequest()
                .body(Response.failedResponse(
                        // HttpStatus.BAD_REQUEST.value(), messageUtil.getMessage(ex.getMessage(), ex.getArgs())));
                        HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<Response<String>> handlingRuntimeException(RuntimeException ex) {
        return ResponseEntity.badRequest()
                // .body(Response.failedResponse(HttpStatus.BAD_REQUEST.value(),
                // messageUtil.getMessage(ex.getMessage())));
                .body(Response.failedResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<Response<String>> handlingMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();

        ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

        Map<String, String> result = new HashMap<>();
        result.put("error", errors.getFirst());
        return ResponseEntity.badRequest()
                .body(Response.failedResponse(HttpStatus.BAD_REQUEST.value(), result.get("error")));
    }

    @ExceptionHandler(value = ValidationException.class)
    ResponseEntity<Response<String>> handlingValidationException(ValidationException ex) {
        return ResponseEntity.badRequest()
                .body(Response.failedResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    ResponseEntity<Response<String>> handlingConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.badRequest()
                .body(Response.failedResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }
}
