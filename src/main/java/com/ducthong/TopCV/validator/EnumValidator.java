package com.ducthong.TopCV.validator;

import java.util.List;
import java.util.stream.Stream;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.ducthong.TopCV.annotation.ValidEnum;

// public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
//    private Class<? extends Enum<?>> enumClass;
//    @Override
//    public void initialize(ValidEnum constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
//        this.enumClass = constraintAnnotation.enumClass();
//    }
//
//    @Override
//    public boolean isValid(String value, ConstraintValidatorContext context) {
//        Object[] enumValues = this.enumClass.getEnumConstants();
//        for(Object item : enumValues)
//            if(value.equals(item.toString())) return true;
//        return false;
//    }
// }
public class EnumValidator implements ConstraintValidator<ValidEnum, CharSequence> {
    private List acceptedValues;

    @Override
    public void initialize(ValidEnum enumValue) {
        acceptedValues = Stream.of(enumValue.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return acceptedValues.contains(value.toString().toUpperCase());
    }
}
