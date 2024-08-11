package com.ducthong.TopCV.annotation;

// import com.ducthong.TopCV.validator.EnumValidator;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.ducthong.TopCV.validator.EnumValidator;

// @Target({FIELD, PARAMETER})
// @Retention(RUNTIME)
// @Constraint(validatedBy = EnumValidator.class)
// public @interface ValidEnum {
//    String message() default "{validator.valid-enum}";
//    Class<? extends Enum<?>> enumClass();
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
// }
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnumValidator.class)
public @interface ValidEnum {
    String name();

    String message() default "Hello";

    Class<? extends Enum<?>> enumClass();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
