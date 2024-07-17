package com.ducthong.TopCV.annotation;

import java.lang.annotation.*;

import org.springframework.web.bind.annotation.RestController;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
public @interface RestApiV1 {}
