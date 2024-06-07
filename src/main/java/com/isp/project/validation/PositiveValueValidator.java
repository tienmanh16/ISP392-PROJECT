package com.isp.project.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PositiveValueValidator implements ConstraintValidator<PositiveValue, Number> {
    @Override
    public void initialize(PositiveValue constraintAnnotation) {
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        return value != null && value.doubleValue() > 0;
    }
}
