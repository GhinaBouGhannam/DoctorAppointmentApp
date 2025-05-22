package com.example.springboot.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<ValidDate, String> {
    @Override
    public void initialize(ValidDate constraintAnnotation) {}

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }

    }}