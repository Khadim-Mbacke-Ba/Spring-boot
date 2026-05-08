package sn.ucad.restou.entity;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumeroCarteValidator implements ConstraintValidator<ValidNumeroCarte, String> {

    private static final String PATTERN = "ETU-\\d{4}-\\d{3}";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null)
            return false;

        return value.matches(PATTERN);
    }
}