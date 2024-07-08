package com.ducthong.TopCV.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ducthong.TopCV.responses.Response;
import com.ducthong.TopCV.utility.MessageSourceUtil;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSourceUtil messageSourceUtil;

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<Response<String>> handlingRuntimeException(RuntimeException ex) {
        return ResponseEntity.badRequest()
                .body(Response.failedResponse(
                        HttpStatus.BAD_REQUEST.value(), messageSourceUtil.getMessage(ex.getMessage())));
    }
}
