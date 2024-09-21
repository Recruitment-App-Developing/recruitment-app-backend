package com.ducthong.TopCV.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.ducthong.TopCV.annotation.EnumValid;

public class EnumValidator implements ConstraintValidator<EnumValid, String> {
    private String validatorName;
    private String enumClassName;
    private final String MESSAGE = " must be any of enum ";
    private Enum<?>[] enumValues;

    @Override
    public void initialize(EnumValid validEnum) {
        this.validatorName = validEnum.name();
        this.enumClassName = validEnum.enumClass().getSimpleName();
        this.enumValues = validEnum.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        for (Enum<?> enumValue : enumValues) {
            if (enumValue.name().equals(value)) {
                return true;
            }
        }
        context.buildConstraintViolationWithTemplate(this.validatorName + MESSAGE + this.enumClassName)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
