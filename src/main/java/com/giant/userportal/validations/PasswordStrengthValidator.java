package com.giant.userportal.validations;

import com.giant.userportal.annotation.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordValidator, String> {

    @Override
    public boolean isValid(String passwordField, ConstraintValidatorContext context) {

        if (passwordField == null) {
            return false;
        }

        boolean hasUppercase = passwordField.matches(".*[A-Z].*");
        boolean hasLowercase = passwordField.matches(".*[a-z].*");
        boolean hasDigit = passwordField.matches(".*\\d.*");
        boolean hasSpecialChar = passwordField.matches(".*[!@#$%^&*()-+].*");
        boolean isAtLeast8 = passwordField.length() >= 8;

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar && isAtLeast8;
    }
}