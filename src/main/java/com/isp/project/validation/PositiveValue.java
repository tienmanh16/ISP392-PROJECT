package com.isp.project.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PositiveValueValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveValue {
    String message() default "Value must be positive";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
