package ru.itis.kpfu.kozlov.social_network_impl.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.length() > 7 && value.matches(".*[A-Z].*") &&
                value.matches(".*[a-z].*") && value.matches(".*[0-9].*");
    }
}
