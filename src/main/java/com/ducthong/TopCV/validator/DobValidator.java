package com.ducthong.TopCV.validator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.ducthong.TopCV.annotation.DobConstraint;

public class DobValidator implements ConstraintValidator<DobConstraint, Date> {
    private int min;

    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) return true;
        LocalDate dateValue = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long years = ChronoUnit.YEARS.between(dateValue, LocalDate.now());
        if (years >= min) return true;
        return false;
    }
}
