package com.isp.project.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoSpecialCharacterValidator implements ConstraintValidator<NoSpecialCharacter, String> {

    private static final String SPECIAL_CHARACTERS = ".*[^a-zA-Z0-9 ].*";

    @Override
    public void initialize(NoSpecialCharacter constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false; // Bạn có thể thay đổi logic này để không chấp nhận null hoặc chuỗi rỗng
        }
        return !value.matches(SPECIAL_CHARACTERS);
    }
}
